package com.qq.book.generator.gensrc;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Generator {

    protected String outFilePath;

    protected Template template;

    protected Map<String, Object> context = new HashMap<>();

    public void setContext(String key, Object obj) {
        this.context.put(key, obj);
    }

    public void gen() throws Exception{

        VelocityContext ctx = new VelocityContext();
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            ctx.put(entry.getKey(), entry.getValue());
        }

        StringWriter writer = new StringWriter();
        template.merge(ctx, writer);

        Files.write(Paths.get(outFilePath), writer.toString().getBytes());
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
