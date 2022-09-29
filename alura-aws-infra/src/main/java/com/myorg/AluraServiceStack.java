package com.myorg;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awscdk.Fn;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.ICluster;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
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

        ApplicationLoadBalancedFargateService.Builder.create(this, "AluraService")
                .serviceName("alura-service-ola")
                .cluster(cluster)           // Required
                .cpu(512)                   // Default is 256
                .desiredCount(2)            // Default is 1
                .listenerPort(1234)
                .assignPublicIp(Boolean.TRUE)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .image(ContainerImage.fromRegistry("rodrigolucio/pagamentos"))
                                .containerPort(1234)
                                .containerName("app_ola")
                                .environment(parametersAuthentication)
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // Default is true
                .build();
    }
}
