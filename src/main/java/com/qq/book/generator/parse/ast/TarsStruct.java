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

import com.qq.book.generator.gensrc.NameFormatter;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class TarsStruct extends CommonTree implements Annotation{

	private final String structName;
	private final List<TarsStructMember> memberList = new ArrayList<TarsStructMember>();

	private String headAnnotation;
	private String tailAnnotation;

	public TarsStruct(int tokenType, String structName) {
		super(new CommonToken(tokenType));
		this.structName = structName;
	}

	@Override
	public void addChild(Tree child) {
		super.addChild(child);
		if (child instanceof TarsStructMember) {
			memberList.add((TarsStructMember) child);
		}
	}

	public String getStructName() {
		return structName;
	}

	public String getJavaName() {
		return NameFormatter.toJava(structName);
	}

	public String getDbName() {
		return NameFormatter.toDB(structName);
	}

	public List<TarsStructMember> getMemberList() {
		return memberList;
	}

	@Override
	public String getHeadAnnotation() {
		return headAnnotation;
	}

	@Override
	public void setHeadAnnotation(String headAnnotation) {
		this.headAnnotation = headAnnotation;
	}

	@Override
	public String getTailAnnotation() {
		return tailAnnotation;
	}

	@Override
	public void setTailAnnotation(String tailAnnotation) {
		this.tailAnnotation = tailAnnotation;
	}
}
