package com.phope.realcalloutbackend;

import org.springframework.boot.SpringApplication;

public class TestRealCalloutBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(RealCalloutBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
