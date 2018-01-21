package com.iggy.zap.adder;

import com.iggy.zap.adder.health.BasicHealthCheck;
import com.iggy.zap.adder.resources.AddResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AdderApplication extends Application<AdderConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AdderApplication().run(args);
    }

    @Override
    public String getName() {
        return "Adder Service";
    }

    @Override
    public void initialize(final Bootstrap<AdderConfiguration> bootstrap) {
    }

    @Override
    public void run(final AdderConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new AddResource());
        environment.healthChecks().register("simple", new BasicHealthCheck());
    }

}
