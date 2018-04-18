package com.qq.book.generator.gensrc;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Generator {

    protected String outFilePath;

    protected String template;

    protected Map<String, Object> context = new HashMap<>();

    public void setContext(String key, Object obj) {
        this.context.put(key, obj);
    }

    public void gen() throws Exception{

        VelocityContext ctx = new VelocityContext();
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            ctx.put(entry.getKey(), entry.getValue());
        }

        String templateStr = getTemplateStr();

        StringWriter writer = new StringWriter();

        // 转换输出, 去除velocity代码缩进的空格符
        VelocityEngine ve = Velocity.INSTANCE.engine();
        ve.evaluate(ctx, writer, "",
                templateStr.replaceAll("[ ]*(#if|#else|#elseif|#end|#set|#foreach)", "$1"));
        // template.merge(ctx, writer);

        Files.write(Paths.get(outFilePath), writer.toString().getBytes());
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplateStr() throws Exception{
        String strPath = Generator.class.getClassLoader().getResource(template).getFile();
        System.out.println(strPath);
        byte[] bytes = Files.readAllBytes(Paths.get(strPath.substring(1)));
        return new String(bytes, Charset.forName("UTF-8"));
    }
}
