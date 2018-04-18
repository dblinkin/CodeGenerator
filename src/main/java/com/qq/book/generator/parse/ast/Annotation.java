package com.qq.book.generator.parse.ast;

public interface Annotation {

    void setHeadAnnotation(String annotation);

    void setTailAnnotation(String annotation);

    String getHeadAnnotation();

    String getTailAnnotation();

}
