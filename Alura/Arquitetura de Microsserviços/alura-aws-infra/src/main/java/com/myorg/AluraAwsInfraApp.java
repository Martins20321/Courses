package com.myorg;

import software.amazon.awscdk.App;

//Ponto de entrada
public class AluraAwsInfraApp {
    public static void main(final String[] args) {
        App app = new App();

        //id = Identificação da Stack(Aglomerado de recursos, separados por responsabilidade) no CloudFormation
        AluraVpcStack vpcStack = new AluraVpcStack(app, "Vpc"); //Sobe uma stack VPC, aonde possui todos os recursos relacionados a rede
        AluraClusterStack clusterStack = new AluraClusterStack(app, "Cluster", vpcStack.getVpc());
        clusterStack.addStackDependency(vpcStack);
        AluraRdsStack rdsStack = new AluraRdsStack(app, "Rds", vpcStack.getVpc());
        rdsStack.addStackDependency(vpcStack);
        AluraServiceStack serviceStack = new AluraServiceStack(app, "Service",clusterStack.getCluster());
        serviceStack.addStackDependency(clusterStack);
        serviceStack.addStackDependency(rdsStack);
        app.synth();
    }
}

