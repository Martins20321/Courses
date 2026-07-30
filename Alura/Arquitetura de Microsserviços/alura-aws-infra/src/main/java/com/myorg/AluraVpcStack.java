package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

public class AluraVpcStack extends Stack {
    public AluraVpcStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public AluraVpcStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        //Criando a VPC
        Vpc vpc = Vpc.Builder.create(this, "AluraVpc")
                .maxAzs(3)  // Default is all AZs in region. A VPC vai abranger até 3 AZs
                .build();
    }
}
