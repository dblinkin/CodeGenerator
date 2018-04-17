package com.qq.book.generator.gensrc;

import com.qq.book.generator.parse.ast.TarsStruct;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorFactory {

    public static List<Generator> getGenerators(TarsStruct struct) {
        List<Generator> generators = new ArrayList<>();
        generators.add(getBeanGenerator(struct));
        generators.add(getServiceGenerator(struct));
        generators.add(getActionGenerator(struct));
        return generators;
    }

    public static Generator getBeanGenerator(TarsStruct struct) {
        Generator generator = new Generator();
        generator.setOutFilePath(getFilePath(struct, ""));
        generator.setTemplate(Velocity.INSTANCE.getTemplate("javabean"));

        generator.setContext("struct", struct);
        generator.setContext("packageName", "com.qq.book");
        return generator;
    }

    public static Generator getServiceGenerator(TarsStruct struct) {
        Generator generator = new Generator();
        generator.setOutFilePath(getFilePath(struct, "Service"));
        generator.setTemplate(Velocity.INSTANCE.getTemplate("service"));

        generator.setContext("struct", struct);
        generator.setContext("packageName", "com.qq.book");
        return generator;
    }

    public static Generator getActionGenerator(TarsStruct struct) {
        Generator generator = new Generator();
        generator.setOutFilePath(getFilePath(struct, "Action"));
        generator.setTemplate(Velocity.INSTANCE.getTemplate("action"));

        generator.setContext("struct", struct);
        generator.setContext("packageName", "com.qq.book");
        return generator;
    }

    private static String getFilePath(TarsStruct struct, String ext) {
        return String.format("%s/%s%s.java", new File(".").getAbsolutePath(),
                struct.getStructName(), ext);
    }

}
