package org.nuxeo.labs.vertesia;

import org.apache.commons.lang3.StringUtils;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.RunnerFeature;

import static org.nuxeo.labs.vertesia.automation.service.VertesiaComponent.API_KEY;
import static org.nuxeo.labs.vertesia.automation.service.VertesiaComponent.BASE_URL;

@Features({AutomationFeature.class})
@Deploy("nuxeo-vertesia-core")
public class TestVertesiaFeature implements RunnerFeature {

    @Override
    public void beforeRun(FeaturesRunner runner) {
        Framework.getProperties().setProperty(BASE_URL, System.getProperty("VertesiaBaseUrl"));
        Framework.getProperties().setProperty(API_KEY, System.getProperty("VertesiaApiKey"));
    }

    public boolean isKeySet() {
        return StringUtils.isNotBlank(Framework.getProperty(API_KEY));
    }
}