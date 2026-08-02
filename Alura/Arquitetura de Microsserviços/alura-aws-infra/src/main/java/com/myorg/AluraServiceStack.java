package com.myorg;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecr.IRepository;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.logs.LogGroup;
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
        ApplicationLoadBalancedFargateService aluraService = ApplicationLoadBalancedFargateService.Builder.create(this, "AluraService")
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
                                        "SPRING_DATASOURCE_USERNAME", "admin",
                                        "SPRING_DATASOURCE_PASSWORD", Fn.importValue("pedidos-db-senha")))
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(LogGroup.Builder.create(this, "PedidosMsLogGroup")
                                                .logGroupName("PedidosMsLog") //Nome do grupo no CloudWatch
                                                .removalPolicy(RemovalPolicy.DESTROY)
                                                .build())
                                        .streamPrefix("PedidosMs")
                                        .build()))
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // LB acessível pela internet
                .build();

        ScalableTaskCount scalableTarget = aluraService.getService().autoScaleTaskCount(EnableScalingProps.builder()
                .minCapacity(1) //Mínimo de uma instância sempre rodando
                .maxCapacity(3)  //Máximo de instâncias
                .build());
        //escala baseado na CPU
        scalableTarget.scaleOnCpuUtilization("CpuScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(70)  //Se a CPU passar de 50% sobre mais instâncias
                //Configurações de tempo para novas decisões
                .scaleInCooldown(Duration.minutes(3)) //Derrubar uma instância
                .scaleOutCooldown(Duration.minutes(2)) //Subir nota instância
                .build());
        //escala baseado na memória
        scalableTarget.scaleOnMemoryUtilization("MemoryScaling", MemoryUtilizationScalingProps.builder()
                .targetUtilizationPercent(65)
                //Configurações de tempo para novas decisões
                .scaleInCooldown(Duration.minutes(3))
                .scaleOutCooldown(Duration.minutes(2))
                .build());
    }
}
