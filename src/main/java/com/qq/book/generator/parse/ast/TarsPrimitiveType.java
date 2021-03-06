/**
 * Tencent is pleased to support the open source community by making Tars available.
 *
 * Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.qq.book.generator.parse.ast;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;

import static com.qq.book.generator.parse.TarsLexer.TARS_BOOL;
import static com.qq.book.generator.parse.TarsLexer.TARS_BYTE;
import static com.qq.book.generator.parse.TarsLexer.TARS_DOUBLE;
import static com.qq.book.generator.parse.TarsLexer.TARS_FLOAT;
import static com.qq.book.generator.parse.TarsLexer.TARS_INT;
import static com.qq.book.generator.parse.TarsLexer.TARS_LONG;
import static com.qq.book.generator.parse.TarsLexer.TARS_SHORT;
import static com.qq.book.generator.parse.TarsLexer.TARS_STRING;
import static com.qq.book.generator.parse.TarsLexer.TARS_VOID;

public class TarsPrimitiveType extends TarsType {

    public static enum PrimitiveType {
        VOID(0, "void"),
        BOOL(1, "boolean"),
        BYTE(2, "byte"),
        SHORT(3, "short"),
        INT(4, "int"),
        LONG(5, "long"),
        FLOAT(6, "float"),
        DOUBLE(7, "double"),
        STRING(8, "String");

        private int id;
        private String javaStr;

        PrimitiveType(int id, String javaStr) {
            this.id = id;
            this.javaStr = javaStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJavaStr() {
            return javaStr;
        }

        public void setJavaStr(String javaStr) {
            this.javaStr = javaStr;
        }
    }

    private final PrimitiveType primitiveType;

    public TarsPrimitiveType(Token token) {
        super(token, toPrimitiveType(token.getType()).name());
        this.primitiveType = toPrimitiveType(token.getType());
    }

    public TarsPrimitiveType(int type) {
        this(new CommonToken(type));
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public TarsPrimitiveType asPrimitive() {
        return this;
    }

    public PrimitiveType primitiveType() {
        return primitiveType;
    }

    public boolean isVoid() {
        return primitiveType == PrimitiveType.VOID;
    }

    private static PrimitiveType toPrimitiveType(int tokenType) {
        PrimitiveType type = null;
        switch (tokenType) {
            case TARS_VOID:
                type = PrimitiveType.VOID;
                break;
            case TARS_BOOL:
                type = PrimitiveType.BOOL;
                break;
            case TARS_BYTE:
                type = PrimitiveType.BYTE;
                break;
            case TARS_SHORT:
                type = PrimitiveType.SHORT;
                break;
            case TARS_INT:
                type = PrimitiveType.INT;
                break;
            case TARS_LONG:
                type = PrimitiveType.LONG;
                break;
            case TARS_FLOAT:
                type = PrimitiveType.FLOAT;
                break;
            case TARS_DOUBLE:
                type = PrimitiveType.DOUBLE;
                break;
            case TARS_STRING:
                type = PrimitiveType.STRING;
                break;
        }
        return type;
    }

}
