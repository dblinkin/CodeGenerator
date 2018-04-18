package com.qq.book.generator.parse.ast;

import java.util.regex.Pattern;

public class TarsAnnotation {

    private static Pattern headPattern = Pattern.compile("/\\*.+\\*/", Pattern.DOTALL);

    private static Pattern tailPattern = Pattern.compile("//.+?\\n", Pattern.DOTALL);

    protected String source;

    public TarsAnnotation(String source) {
        this.source = source.trim();
    }

    public String getSource() {
        return source;
    }

    /**
     * 头部注释 多行注释表示头部注释
     * @param desc
     * @return
     */
    public static boolean isHeadAnnotation(String desc) {
        return headPattern.matcher(desc).find();
    }

    /**
     * 尾部注释 单行注释表示尾部注释，尾部注释定义一些代码生成规则
     * @param desc
     * @return
     */
    public static boolean isTailAnnotation(String desc) {
        return tailPattern.matcher(desc).find();
    }
}
