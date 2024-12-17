package org.nuxeo.labs.vertesia;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.labs.vertesia.automation.execution.RunRequest;
import org.nuxeo.labs.vertesia.automation.service.VertesiaService;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import jakarta.inject.Inject;

@RunWith(FeaturesRunner.class)
@Features(TestVertesiaFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
public class TestVertesiaService {

    @Inject
    protected VertesiaService vertesiaService;

    @Inject
    protected TestVertesiaFeature vertesiaFeature;

    @Test
    public void testRunExecution() {
        Assume.assumeTrue(vertesiaFeature.isKeySet());
        String response = vertesiaService.runExecution(
                new RunRequest(System.getProperty("VertesiaInteractionId"), "{\"text\":\"Hello\"}",
                        new RunRequest.Configuration(System.getProperty("VertesiaEnvironmentId"), "")));
        System.out.println(response);
    }

}
