package com.qq.book.generator.gensrc;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.Writer;

public class Velocity {

    private static final String CHARSET = "UTF-8";

    public static final Velocity INSTANCE = new Velocity();

    private VelocityEngine ve = new VelocityEngine();

    private Velocity() {
        init();
    }

    public Template getBeanTemplate() {
        return ve.getTemplate("template/javabean.vm", CHARSET);
    }

    public String getTemplate(String name) {
        return String.format("template/%s.vm", name);
    }

    public VelocityEngine engine() {
        return ve;
    }

    private void init() {
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
    }
}
