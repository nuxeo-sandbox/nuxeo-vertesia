package org.nuxeo.labs.vertesia;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.labs.vertesia.automation.automation.VertesiaRunOp;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@RunWith(FeaturesRunner.class)
@Features(TestVertesiaFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
public class TestVertesiaRunOp {

    @Inject
    protected CoreSession session;

    @Inject
    protected AutomationService automationService;

    @Test
    public void testSuccess() throws OperationException {
        OperationContext ctx = new OperationContext(session);
        Map<String, Object> params = new HashMap<>();
        params.put("interactionId",System.getProperty("VertesiaInteractionId"));
        params.put("environmentId",System.getProperty("VertesiaEnvironmentId"));
        params.put("interactionInput","{\"text\":\"Hello\"}");
        Blob json = (Blob) automationService.run(ctx, VertesiaRunOp.ID, params);
        Assert.assertNotNull(json);
    }

    @Test(expected = NuxeoException.class)
    public void testFailure() throws OperationException {
        OperationContext ctx = new OperationContext(session);
        Map<String, Object> params = new HashMap<>();
        params.put("interactionId",System.getProperty("VertesiaInteractionId"));
        params.put("environmentId",System.getProperty("VertesiaEnvironmentId"));
        params.put("modelId","gpt2000");
        params.put("interactionInput","{\"text\":\"Hello\"}");
        Blob json = (Blob) automationService.run(ctx, VertesiaRunOp.ID, params);
        Assert.assertNotNull(json);
    }

    @Test
    public void testAutomationWithOptionalParams() throws OperationException {
        OperationContext ctx = new OperationContext(session);
        Map<String, Object> params = new HashMap<>();
        params.put("interactionId",System.getProperty("VertesiaInteractionId"));
        params.put("environmentId",System.getProperty("VertesiaEnvironmentId"));
        params.put("interactionInput","{\"text\":\"Hello\"}");
        params.put("max_tokens", "1000");
        params.put("temperature", "0.8");
        Blob json = (Blob) automationService.run(ctx, VertesiaRunOp.ID, params);
        Assert.assertNotNull(json);
    }

    @Test
    @Deploy("nuxeo-vertesia-core:test-automation-js-contrib.xml")
    public void testAutomationScriptWithOptionalParams() throws OperationException {
        OperationContext ctx = new OperationContext(session);
        Map<String, Object> params = new HashMap<>();
        params.put("interactionId",System.getProperty("VertesiaInteractionId"));
        params.put("environmentId",System.getProperty("VertesiaEnvironmentId"));
        Blob json = (Blob) automationService.run(ctx, "javascript.test_cp_automation_js", params);
        Assert.assertNotNull(json);
    }

}
