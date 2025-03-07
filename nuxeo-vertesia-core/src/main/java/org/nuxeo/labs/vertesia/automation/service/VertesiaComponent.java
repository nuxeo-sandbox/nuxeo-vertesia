package org.nuxeo.labs.vertesia.automation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.labs.vertesia.automation.execution.RunRequest;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.model.DefaultComponent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VertesiaComponent extends DefaultComponent implements VertesiaService {

    private static final Logger log = LogManager.getLogger(VertesiaComponent.class);

    public static final MediaType JSON = MediaType.get("application/json");

    public static final String BASE_URL = "vertesia.api.url";

    public static final String API_KEY = "vertesia.api.key";

    public static final String TIMEOUT = "vertesia.http.timeout";

    protected OkHttpClient client;

    protected static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String runExecution(RunRequest runRequest) {

        RequestBody body;

        try {
            String json = objectMapper.writeValueAsString(runRequest);
            log.debug(json);
            body = RequestBody.create(json, JSON);
        } catch (JsonProcessingException e) {
            throw new NuxeoException(e);
        }

        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Authorization", String.format("Bearer %s", Framework.getProperty(API_KEY)))
                .url(String.format("%s/runs", Framework.getProperty(BASE_URL)))
                .post(body)
                .build();

        try (Response response = getClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new NuxeoException(response.body().string());
            }
        } catch (IOException e) {
            throw new NuxeoException(e);
        }
    }

    public OkHttpClient getClient() {
        // thread safe lazy initialization of the client
        // see https://en.wikipedia.org/wiki/Double-checked_locking
        OkHttpClient result = client;
        if (result == null) {
            synchronized (this) {
                result = client;
                if (result == null) {
                    long timeout = Long.parseLong(Framework.getProperty(TIMEOUT,"60"));
                    client = result = new OkHttpClient.Builder()
                            .readTimeout(timeout, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return result;
    }

}
