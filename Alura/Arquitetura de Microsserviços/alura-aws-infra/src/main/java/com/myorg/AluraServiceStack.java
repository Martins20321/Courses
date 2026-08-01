package com.myorg;

import software.amazon.awscdk.Fn;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecr.IRepository;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.constructs.Construct;

import java.util.Map;

public class AluraServiceStack extends Stack {
    public AluraServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public AluraServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        IRepository iRepository = Repository.fromRepositoryName(this, "repository", "josemartins07/alura-pedidos-ms");

        // Create a load-balanced Fargate service and make it public
        ApplicationLoadBalancedFargateService.Builder.create(this, "AluraService")
                .serviceName("alura-service-estudos")
                .cluster(cluster)           // Required
                //Task Definition
                .cpu(512)                   // Default is 256
                .desiredCount(1)      // Quantidade de instâncias desejadas
                .listenerPort(8080)     //Porta que o LB escuta
                .assignPublicIp(true)   //Atribui IP público ao container
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .image(ContainerImage.fromEcrRepository(iRepository))
                                .containerPort(8080)
                                .containerName("app_pedidos_db")
                                .environment(Map.of(
                                        "SPRING_DATASOURCE_URL", "jdbc:mysql://" +
                                                Fn.importValue("pedidos-db-endpoint")
                                                + ":3306/alurafood-pedidos?createDatabaseIfNotExist=true",
                                        "SPRING_DATASOURCE_USERNAME","admin",
                                        "SPRING_DATASOURCE_PASSWORD", Fn.importValue("pedidos-db-senha")))
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // LB acessível pela internet
                .build();
    }
}
