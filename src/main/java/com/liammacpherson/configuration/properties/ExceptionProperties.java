package com.liammacpherson.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "exception")
public class ExceptionProperties {
    private StacktraceLevel enableStacktrace = StacktraceLevel.ALWAYS;

    public StacktraceLevel getEnableStacktrace() {
        return enableStacktrace;
    }

    public void setEnableStacktrace(StacktraceLevel enableStacktrace) {
        this.enableStacktrace = enableStacktrace;
    }

    public enum StacktraceLevel {
        ALWAYS,
        TRACE,
        OFF
    }
}
