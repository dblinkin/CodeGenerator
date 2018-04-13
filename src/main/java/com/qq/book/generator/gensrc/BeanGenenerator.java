package com.qq.book.generator.gensrc;

import com.qq.book.generator.parse.ast.TarsStruct;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BeanGenenerator {

    private String packageName;

    private TarsStruct struct;

    private String outFilePath;

    public BeanGenenerator(TarsStruct struct) {
        this.struct = struct;
    }

    public void gen() throws Exception{
        Template template = Velocity.INSTANCE.getBeanTemplate();

        VelocityContext ctx = new VelocityContext();
        ctx.put("packageName", packageName);
        ctx.put("struct", struct);

        StringWriter writer = new StringWriter();
        template.merge(ctx, writer);
        System.out.println(writer.toString());

        Files.write(Paths.get(outFilePath), writer.toString().getBytes());
        // FileWriter fileWriter = new FileWriter(new File(outFilePath));
        // fileWriter.write(writer.toString());
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setStruct(TarsStruct struct) {
        this.struct = struct;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }
}
