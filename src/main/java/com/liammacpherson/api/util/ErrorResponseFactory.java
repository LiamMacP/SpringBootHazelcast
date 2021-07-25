package com.liammacpherson.api.util;

import com.liammacpherson.configuration.properties.ExceptionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

@Service
@EnableConfigurationProperties(ExceptionProperties.class)
public final class ErrorResponseFactory {

    private final ExceptionProperties exceptionProperties;

    private ErrorResponseFactory(ExceptionProperties exceptionProperties) {
        this.exceptionProperties = exceptionProperties;
    }

    public ErrorResponse getErrorResponse(Exception ex, HttpStatus status, WebRequest webRequest) {
        if (isStacktraceEnabled(webRequest)) {
            return new ErrorResponse(status, ex, getStacktrace(ex));
        }
        return new ErrorResponse(status, ex);
    }

    private boolean isStacktraceEnabled(WebRequest webRequest) {
        ExceptionProperties.StacktraceLevel level = exceptionProperties.getEnableStacktrace();

        return (level == ExceptionProperties.StacktraceLevel.ALWAYS
                || (level == ExceptionProperties.StacktraceLevel.TRACE && isTraceOn(webRequest)));
    }

    private boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues("TRACE");
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }

    private String getStacktrace(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);

        return sw.toString();
    }
}
