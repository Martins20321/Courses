package com.myorg;

import software.amazon.awscdk.App;

//Ponto de entrada
public class AluraAwsInfraApp {
    public static void main(final String[] args) {
        App app = new App();

        //id = Identificação da Stack(Aglomerado de recursos, separados por responsabilidade) no CloudFormation
        new AluraVpcStack(app, "Vpc");

        app.synth();
    }
}

