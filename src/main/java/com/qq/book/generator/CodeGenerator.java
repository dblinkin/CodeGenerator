package com.qq.book.generator;

import com.qq.book.generator.gensrc.Generator;
import com.qq.book.generator.gensrc.GeneratorFactory;
import com.qq.book.generator.gensrc.Tars2JavaMojo;
import com.qq.book.generator.parse.TarsLexer;
import com.qq.book.generator.parse.TarsParser;
import com.qq.book.generator.parse.ast.Annotation;
import com.qq.book.generator.parse.ast.TarsAnnotation;
import com.qq.book.generator.parse.ast.TarsNamespace;
import com.qq.book.generator.parse.ast.TarsRoot;
import com.qq.book.generator.parse.ast.TarsStruct;
import com.qq.book.generator.parse.ast.TarsStructMember;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qq.book.generator.parse.TarsLexer.COMMENT;

public class CodeGenerator {

    public static void main(String[] args) throws Exception{
        System.out.println(args);
        String fileName = "test.tars";
        ClassLoader classLoader = Tars2JavaMojo.class.getClassLoader();
        URL url = classLoader.getResource(fileName);

        System.out.println(url.getFile());

        TarsLexer tarsLexer = new TarsLexer(new ANTLRFileStream(url.getFile(), "UTF-8"));
        CommonTokenStream tokens = new CommonTokenStream(tarsLexer);
        TarsParser tarsParser = new TarsParser(tokens);
        TarsRoot root = (TarsRoot) tarsParser.start().getTree();
        root.setTokenStream(tokens);
        Map<String, List<TarsNamespace>> nsMap = new HashMap<String, List<TarsNamespace>>();
        for (TarsNamespace ns : root.namespaceList()) {
            List<TarsNamespace> list = nsMap.get(ns.namespace());
            if (list == null) {
                list = new ArrayList<TarsNamespace>();
                nsMap.put(ns.namespace(), list);
            }
            list.add(ns);
        }

        setAnnotation(tokens, root);

        for (TarsNamespace ns : root.namespaceList()) {
            for (TarsStruct struct : ns.structList()) {
                for (Generator generator : GeneratorFactory.getGenerators(ns, struct)) {
                    generator.gen();
                }
            }
        }
        System.out.println("end");
    }

    private static void setAnnotation(CommonTokenStream tokens, TarsRoot root) {
        for (TarsNamespace ns : root.namespaceList()) {
            for (TarsStruct struct : ns.structList()) {
                setAnnotation(struct, tokens);
                for (TarsStructMember member : struct.getMemberList()) {
                    setAnnotation(member, tokens);
                }
            }
        }
    }

    private static void setAnnotation(CommonTree node, CommonTokenStream tokens) {
        int headIndex = node.getTokenStartIndex() - 1;
        if (headIndex >= 0) {
            Token token = tokens.get(headIndex);
            if (token.getType() == COMMENT && TarsAnnotation.isHeadAnnotation(token.getText())) {
                if (node instanceof Annotation) {
                    ((Annotation)node).setHeadAnnotation(token.getText());
                }
            }
        }

        // +2 需要略过一个;号
        int tailIndex = node.getTokenStopIndex() + 2;
        if (tailIndex < tokens.size()) {
            Token token = tokens.get(tailIndex);
            if (token.getType() == COMMENT && TarsAnnotation.isTailAnnotation(token.getText())) {
                if (node instanceof Annotation) {
                    ((Annotation)node).setTailAnnotation(token.getText());
                }
            }
        }
    }





}
