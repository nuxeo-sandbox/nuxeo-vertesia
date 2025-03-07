package org.nuxeo.labs.vertesia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.nuxeo.labs.vertesia.automation.execution.RunError;
import org.nuxeo.labs.vertesia.automation.execution.RunRequest;
import org.nuxeo.labs.vertesia.automation.execution.RunResult;

import java.util.List;
import java.util.Map;

public class TestModelJson {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRunRequestRequiredParamsSerialization() throws JsonProcessingException {
        RunRequest request = new RunRequest("123", "\"test\"", new RunRequest.Configuration("abc", "model1"));
        String json = objectMapper.writeValueAsString(request);
        Assert.assertEquals("""        
        {"interactionId":"123","data":"test","config":{"environment":"abc","model":"model1"}}""",json);
    }

    @Test
    public void testRunRequestAllParamsSerialization() throws JsonProcessingException {
        RunRequest request = new RunRequest("123", "\"test\"", new RunRequest.Configuration("abc", "model1",0.5,1000), List.of("tag1","tag2"));
        String json = objectMapper.writeValueAsString(request);
        Assert.assertEquals("""        
        {"interactionId":"123","data":"test","tags":["tag1","tag2"],"config":{"environment":"abc","model":"model1","temperature":0.5,"max_tokens":1000}}""",json);
    }


    @Test
    public void testRunResultDeserialization() throws JsonProcessingException {
        String json = """
                {
                  "result": {
                    "color": "blue"
                  },
                  "status": "completed"
                }
                """;
        RunResult runResult = objectMapper.readValue(json, RunResult.class);
        Map<String,Object> result = runResult.result;
        Assert.assertEquals("blue",result.get("color"));
    }

    @Test
    public void testRunResultErrorDeserialization() throws JsonProcessingException {
        String json = """
                {
                    "error": "Not Found",
                    "status": 404,
                    "message": "Interaction not found: 6554cf617eae1c28ef5f3d41"
                }
                """;
        RunError error = objectMapper.readValue(json, RunError.class);
        Assert.assertEquals("404", error.status);
    }

}
