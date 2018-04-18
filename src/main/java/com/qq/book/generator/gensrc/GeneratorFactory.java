package com.qq.book.generator.gensrc;

import com.qq.book.generator.html.Struct;
import com.qq.book.generator.parse.ast.TarsNamespace;
import com.qq.book.generator.parse.ast.TarsStruct;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorFactory {

    public static List<Generator> getGenerators(TarsNamespace namespace, TarsStruct struct) {
        List<Generator> generators = new ArrayList<>();
        generators.add(getBeanGenerator(namespace, struct));
        generators.add(getServiceGenerator(namespace, struct));
        generators.add(getActionGenerator(namespace, struct));
        generators.add(getHtmlGenerator(namespace, struct));
        generators.add(getHtmlInputGenerator(namespace, struct));
        generators.add(getJsGenerator(namespace, struct));
        return generators;
    }

    public static Generator getBeanGenerator(TarsNamespace namespace, TarsStruct struct) {
        Generator generator = new Generator();
        generator.setOutFilePath(getFilePath(struct, ""));
        generator.setTemplate(Velocity.INSTANCE.getTemplate("javabean"));

        generator.setContext("struct", struct);
        generator.setContext("packageName", "com.qq.book");
        return generator;
    }

    public static Generator getServiceGenerator(TarsNamespace namespace, TarsStruct struct) {
        Generator generator = new Generator();
        generator.setOutFilePath(getFilePath(struct, "Service"));
        generator.setTemplate(Velocity.INSTANCE.getTemplate("service"));

        generator.setContext("struct", struct);
        generator.setContext("packageName", "com.qq.book");
        return generator;
    }

    public static Generator getActionGenerator(TarsNamespace namespace, TarsStruct struct) {
        Generator generator = new Generator();
        generator.setOutFilePath(getFilePath(struct, "Action"));
        generator.setTemplate(Velocity.INSTANCE.getTemplate("action"));

        generator.setContext("struct", struct);
        generator.setContext("packageName", "com.qq.book");
        return generator;
    }

    public static Generator getHtmlGenerator(TarsNamespace namespace, TarsStruct struct) {
        Generator generator = new Generator();
        generator.setOutFilePath(getHtmlFilePath(struct, "_list"));
        generator.setTemplate(Velocity.INSTANCE.getTemplate("html"));

        generator.setContext("moduleName", namespace.name());
        generator.setContext("struct", new Struct(struct));
        return generator;
    }

    public static Generator getHtmlInputGenerator(TarsNamespace namespace, TarsStruct struct) {
        Generator generator = new Generator();
        generator.setOutFilePath(getHtmlFilePath(struct, "_input"));
        generator.setTemplate(Velocity.INSTANCE.getTemplate("html_input"));

        generator.setContext("moduleName", namespace.name());
        generator.setContext("struct", new Struct(struct));
        return generator;
    }

    public static Generator getJsGenerator(TarsNamespace namespace, TarsStruct struct) {
        Generator generator = new Generator();
        generator.setOutFilePath(getJsFilePath(struct, "_ops"));
        generator.setTemplate(Velocity.INSTANCE.getTemplate("js"));

        generator.setContext("moduleName", namespace.name());
        generator.setContext("struct", new Struct(struct));
        return generator;
    }

    private static String getFilePath(TarsStruct struct, String ext) {
        return String.format("%s/%s%s.java", new File(".").getAbsolutePath(),
                struct.getStructName(), ext);
    }

    private static String getHtmlFilePath(TarsStruct struct, String ext) {
        return String.format("%s/%s%s.html", new File(".").getAbsolutePath(),
                struct.getDbName(), ext);
    }

    private static String getJsFilePath(TarsStruct struct, String ext) {
        return String.format("%s/%s%s.js", new File(".").getAbsolutePath(),
                struct.getDbName(), ext);
    }

}
