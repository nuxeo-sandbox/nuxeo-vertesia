package org.nuxeo.labs.vertesia.automation.execution;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunError {

    public String status;

    public String error;

    public String message;

    public RunError() {
    };

    public RunError(String status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

}
