package br.com.alurafood.avaliacao.avaliacao.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AvaliacaoAMQPConfiguration {
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return  rabbitTemplate;
    }

//    @Bean
//    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchTenRabbitListenerContainerFactory(ConnectionFactory rabbitConnectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(rabbitConnectionFactory);
//        factory.setPrefetchCount(5);
//        return factory;
//    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange(PagamentoEventsConstants.EXCHANGE_PAGAMENTO_EVENTS)
                .build();
    }

    @Bean
    public Queue queuePagamentoEvents() {
        return QueueBuilder
                .durable(PagamentoEventsConstants.QUEUE_PAGAMENTO_EVENTS)
                .deadLetterExchange(PagamentoEventsConstants.EXCHANGE_PAGAMENTO_EVENTS_DEAD_LETTER) // Direciona automaticamente as mensagem para essa deadletter em caso de exception
                .build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder
                .fanoutExchange(PagamentoEventsConstants.EXCHANGE_PAGAMENTO_EVENTS_DEAD_LETTER)
                .build();
    }

    @Bean
    public Queue queuePagamentoEventsDeadLetter() {
        return QueueBuilder
                .durable(PagamentoEventsConstants.QUEUE_PAGAMENTO_EVENTS_DEAD_LETTER)
                .build();
    }

    @Bean
    public Binding bindPagamentoEvents() {
        return BindingBuilder
                .bind(queuePagamentoEvents())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindPagamentoEventsDeadLetter() {
        return BindingBuilder
                .bind(queuePagamentoEventsDeadLetter())
                .to(deadLetterExchange());
    }

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

}
