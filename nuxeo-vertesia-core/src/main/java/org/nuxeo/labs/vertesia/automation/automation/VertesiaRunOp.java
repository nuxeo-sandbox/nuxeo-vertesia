package org.nuxeo.labs.vertesia.automation.automation;

import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.impl.blob.StringBlob;
import org.nuxeo.labs.vertesia.automation.execution.RunRequest;
import org.nuxeo.labs.vertesia.automation.service.VertesiaService;

import java.util.List;

@Operation(id = VertesiaRunOp.ID, category = "Vertesia", label = "Execute a Vertesia Interaction",
        description = "Call the Vertesia platform to run an interaction")
public class VertesiaRunOp {

    public static final String ID = "Vertesia.ExecInteraction";

    @Param(name = "interactionId", required = true)
    protected String interactionId;

    @Param(name = "environmentId", required = true)
    protected String environmentId;

    @Param(name = "modelId", required = false)
    protected String modelId;

    @Param(name = "interactionInput", required = true)
    protected String input;

    @Param(name = "temperature", required = false)
    protected String temperatureStr;

    @Param(name = "max_tokens", required = false)
    protected String max_tokensStr;

    @Param(name = "tags", required = false)
    protected List<String> tags;

    @Context
    VertesiaService cp;

    @OperationMethod
    public Blob run() {

        double temperature = temperatureStr != null ? Double.parseDouble(temperatureStr) : 0.0;
        long max_tokens = max_tokensStr != null ? Long.parseLong(max_tokensStr) : 0;

        RunRequest request = new RunRequest(interactionId, input,
                new RunRequest.Configuration(environmentId, modelId, temperature, max_tokens), tags);
        String result = cp.runExecution(request);
        return new StringBlob(result, "application/json");
    }
}
