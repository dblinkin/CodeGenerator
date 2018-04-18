package com.qq.book.generator.html;

import com.qq.book.generator.gensrc.NameFormatter;
import com.qq.book.generator.parse.ast.TarsStruct;
import com.qq.book.generator.parse.ast.TarsStructMember;

import java.util.ArrayList;
import java.util.List;

public class Struct {

    private String name;

    private String desc;

    private List<Member> members = new ArrayList<>();

    private List<Member> queryMembers = new ArrayList<>();

    private List<Member> hiddenMembers = new ArrayList<>();

    public Struct(TarsStruct struct) {
        this.name = struct.getStructName();

        String headAnnotation = struct.getHeadAnnotation();
        if (headAnnotation != null) {
            parseHead(headAnnotation);
        }

        for (TarsStructMember member : struct.getMemberList()) {
            Member m = new Member((member));
            members.add(m);
            if (m.isType("query")) {
                queryMembers.add(m);
            } else if (m.isType("hidden")) {
                hiddenMembers.add(m);
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getJavaName() {
        return NameFormatter.toJava(name);
    }

    public String getDbName() {
        return NameFormatter.toDB(name);
    }

    public String getDesc() {
        if (desc == null) {
            return name;
        }
        return desc;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Member> getQueryMembers() {
        return queryMembers;
    }

    public List<Member> getHiddenMembers() {
        return hiddenMembers;
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

    private void parseLine(String str) {
        String[] ss = str.split("\\s+");
        switch (ss[0]) {
            case "@name":
                this.desc = ss[1];
                break;
            default:
                System.out.println(str);
                break;
        }
    }

    private String filterHead(String annotation) {
        // /** /* */ * 过滤
        return annotation.trim()
                .replaceAll("\\s*/\\*\\*\\s*", "")
                .replaceAll("\\s*/\\*\\s*", "")
                .replaceAll("\\s+\\*\\s+", "")
                .replaceAll("\\s*\\*/\\s*", "");
    }
}
