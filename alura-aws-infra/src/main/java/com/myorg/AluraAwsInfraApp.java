package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class AluraAwsInfraApp {
    public static void main(final String[] args) {
        App app = new App();

        AluraVpcStack vpcStack = new AluraVpcStack(app, "vpc");

        AluraClusterStack clusterStack = new AluraClusterStack(app, "cluster", vpcStack.getVpc());
        clusterStack.addDependency(vpcStack);

        AluraRdsStack rdsStack = new AluraRdsStack(app, "rds", vpcStack.getVpc());
        rdsStack.addDependency(vpcStack);

        AluraServiceStack serviceStack = new AluraServiceStack(app, "service", clusterStack.getCluster());
        serviceStack.addDependency(clusterStack);
        serviceStack.addDependency(rdsStack);
        app.synth();
    }
}

