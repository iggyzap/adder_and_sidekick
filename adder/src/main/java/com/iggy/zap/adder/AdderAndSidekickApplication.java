package com.iggy.zap.adder;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AdderAndSidekickApplication extends Application<AdderAndSidekickConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AdderAndSidekickApplication().run(args);
    }

    @Override
    public String getName() {
        return "Adder And Sidekick";
    }

    @Override
    public void initialize(final Bootstrap<AdderAndSidekickConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final AdderAndSidekickConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
