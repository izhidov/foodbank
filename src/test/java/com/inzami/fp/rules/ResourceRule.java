package com.inzami.fp.rules;

import org.apache.commons.io.IOUtils;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;

public class ResourceRule extends ExternalResource {

    private static Class applyClass;

    @Override
    public Statement apply(final Statement base, final Description description) {
        applyClass = description.getTestClass();
        return super.apply(base, description);
    }

    public String get(String resourceName) throws IOException {
        return IOUtils.toString(
                applyClass.getResourceAsStream(resourceName),
                "UTF-8"
        );
    }
}
