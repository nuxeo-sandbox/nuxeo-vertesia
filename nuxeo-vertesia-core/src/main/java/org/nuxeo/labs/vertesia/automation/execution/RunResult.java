package org.nuxeo.labs.vertesia.automation.execution;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunResult {

    public String status;

     public Map<String, Object> result;

    public RunResult() {
    };

    public RunResult(String status, Map<String, Object> result) {
        this.status = status;
        this.result = result;
    }

    @Override
    public String toString() {
        return "RunResult{" +
                "status='" + status + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

}
