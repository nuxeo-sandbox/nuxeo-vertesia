package org.nuxeo.labs.vertesia.automation.execution;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunRequest {

    public String interactionId;
    @JsonRawValue
    public String data;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<String> tags;
    public Configuration config;

    public RunRequest(String interactionId, String data, Configuration config) {
        this.interactionId = interactionId;
        this.data = data;
        this.config = config;
    }

    public RunRequest(String interactionId, String data, Configuration config, List<String> tags) {
        this(interactionId,data,config);
        this.tags = tags;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Configuration {
        public String environment;
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public String model;
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public double temperature;
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public long max_tokens;

        public Configuration(String environment, String model) {
            this.environment = environment;
            this.model = model;
        }

        public Configuration(String environment, String model, double temperature, long max_tokens) {
            this(environment, model);
            this.temperature = temperature;
            this.max_tokens = max_tokens;
        }

    }

}



