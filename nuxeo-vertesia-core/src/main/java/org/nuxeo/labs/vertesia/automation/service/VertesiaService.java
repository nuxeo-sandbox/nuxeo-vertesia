package org.nuxeo.labs.vertesia.automation.service;

import org.nuxeo.labs.vertesia.automation.execution.RunRequest;

public interface VertesiaService {

    /**
     *
     * @param runRequest The run parameters
     * @return The API response as a json string
     */
    String runExecution(RunRequest runRequest);

}
