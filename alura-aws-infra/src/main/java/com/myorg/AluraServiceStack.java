package com.myorg;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awscdk.*;
import software.amazon.awscdk.Fn;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecs.AwsLogDriverProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.CpuUtilizationScalingProps;
import software.amazon.awscdk.services.ecs.ICluster;
import software.amazon.awscdk.services.ecs.LogDriver;
import software.amazon.awscdk.services.ecs.MemoryUtilizationScalingProps;
import software.amazon.awscdk.services.ecs.ScalableTaskCount;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.logs.LogGroup;
import software.constructs.Construct;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class AluraServiceStack extends Stack {
    public AluraServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public AluraServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        Map<String, String> parametersAuthentication = new HashMap<>();
        parametersAuthentication.put("SPRING_DATASOURCE_URL", "jdbc:postgresql://" +
                Fn.importValue("pedidos-db-endpoint") +
                ":5432/pagamento?useTimezone=true&serverTimezone=UTC");

        parametersAuthentication.put("SPRING_DATASOURCE_USERNAME", "rodrigo");
        parametersAuthentication.put("SPRING_DATASOURCE_PASSWORD",  Fn.importValue("pedidos-db-senha"));

        ApplicationLoadBalancedFargateService aluraService = ApplicationLoadBalancedFargateService.Builder.create(this, "AluraService")
                .serviceName("alura-service-ola")
                .cluster(cluster)           // Required
                .cpu(512)                   // Default is 256
                .desiredCount(1)            // Default is 1
                .listenerPort(1234)
                .assignPublicIp(Boolean.TRUE)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                //Poderia ter um repositório privado utilizando o ECR - Amazon Elastic Container Registry
                                //Assim subiria a minha imagem docker para o repositório criado lá. Bem facil de fazer
                                .image(ContainerImage.fromRegistry("rodrigolucio/pagamentos"))
                                .containerPort(1234)
                                .containerName("app_ola")
                                .environment(parametersAuthentication)
                                //Configurações de log centralizado
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(LogGroup.Builder.create(this, "PedidosMsLogGroup")
                                                .logGroupName("PedidosMsLog")
                                                .removalPolicy(RemovalPolicy.DESTROY)
                                                .build())
                                        .streamPrefix("PedidosMS")
                                        .build()))
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // Default is true
                .build();

        //Configurações do auto scaling
        ScalableTaskCount scalableTarget = aluraService.getService().autoScaleTaskCount(EnableScalingProps.builder()
                .minCapacity(1)
                .maxCapacity(3)
                .build());

        scalableTarget.scaleOnCpuUtilization("CpuScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(70)
                .scaleInCooldown(Duration.minutes(3))
                .scaleOutCooldown(Duration.minutes(2))
                .build());
        scalableTarget.scaleOnMemoryUtilization("MemoryScaling", MemoryUtilizationScalingProps.builder()
                .targetUtilizationPercent(65)
                .scaleInCooldown(Duration.minutes(3))
                .scaleOutCooldown(Duration.minutes(2))
                .build());
    }
}
