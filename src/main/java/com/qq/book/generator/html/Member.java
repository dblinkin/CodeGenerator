package com.qq.book.generator.html;

import com.qq.book.generator.gensrc.NameFormatter;
import com.qq.book.generator.parse.ast.TarsStructMember;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Member {

    private String name;

    // html输入框前的描述
    private String desc;

    // text hidden select hidden radio
    private List<String> types = new ArrayList<>();

    private String enumName;

    // 额外的name-value信息
    private LinkedHashMap<String, String> selectValues;

    public Member(TarsStructMember member) {
        this.name = member.getMemberName();
        this.types.add("text"); // 默认text

        String headAnnotation = member.getHeadAnnotation();
        if (headAnnotation != null) {
            parseHead(headAnnotation);
        }

        String tailAnnotation = member.getTailAnnotation();
        if (tailAnnotation != null) {
            parseTail(tailAnnotation);
        }
    }

    public boolean isType(String type) {
        if ("time".equals(type)) {
            return this.name.endsWith("Time");
        }
        return types.contains(type);
    }

    public String getDesc() {
        if (desc == null) {
            return name;
        }
        return desc;
    }

    public String getJavaName() {
        return NameFormatter.toJava(name);
    }

    public String getName() {
        return name;
    }

    public String getEnumName() {
        return enumName;
    }

    public LinkedHashMap<String, String> getSelectValues() {
        return selectValues;
    }

    private void parseHead(String annotation) {
        String source = filterHead(annotation);

        int index = source.indexOf("@");
        if (index < 0) {
            this.desc = source;
            return;
        }

        int nextIndex = -1;
        while ((nextIndex = source.indexOf("@", index + 1)) != -1) {
            String str = source.substring(index, nextIndex);
            parseLine(str);
            index = nextIndex;
        }
        parseLine(source.substring(index));
    }

    private String filterHead(String annotation) {
        // /** /* */ * 过滤
        return annotation.trim()
                .replaceAll("\\s*/\\*\\*\\s*", "")
                .replaceAll("\\s*/\\*\\s*", "")
                .replaceAll("\\s+\\*\\s+", "")
                .replaceAll("\\s*\\*/\\s*", "");
    }

    private void parseLine(String str) {
        String[] ss = str.split("\\s+");
        switch (ss[0]) {
            case "@name":
                this.desc = ss[1];
                break;
            case "@enum":
                this.types.add("enum");
                this.enumName = ss[1];
                break;
            case "@values":
                this.types.add("select");
                for (int i = 1; i < ss.length; i++) {
                    if (ss[i] == null || ss[i].trim().length() == 0) {
                        continue;
                    }

                    String[] kvs = ss[i].trim().split("\\-");
                    if (kvs.length == 2) {
                        put(kvs[0], kvs[1]);
                    }
                }
                break;
            default:
                System.out.println(str);
                break;
        }
    }

    protected void put(String key, String value) {
        if (selectValues == null) {
            selectValues = new LinkedHashMap<>();
        }
        selectValues.put(key, value);
    }

    private void parseTail(String annotation) {
        String source = filterTail(annotation);

        String[] ss = source.trim().split("\\s+");
        for (String type : ss) {
            if (type != null && type.trim().length() != 0) {
                this.types.add(type);
            }
        }
    }

    private String filterTail(String annotation) {
        return annotation.trim().replaceAll("//\\s*", "");
    }
}
