/*
 * Copyright (C) 2011-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapiddweller.script.expression;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Context;
import com.rapiddweller.script.Expression;

/**
 * {@link Expression} implementation that performs a static method call.<br/>
 * <br/>
 * Created at 27.07.2009 08:58:30
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class StaticMethodCallExpression extends DynamicExpression<Object> {
	
	private final Class<?> targetClass;
	private final String methodName;
	private final Expression<?>[] argExpressions;
	
	public StaticMethodCallExpression(Class<?> targetClass, String methodName, Expression<?> ... argExpressions) {
	    this.targetClass = targetClass;
	    this.methodName = methodName;
	    this.argExpressions = argExpressions;
    }

	@Override
	public Object evaluate(Context context) {
    	Object[] args = ExpressionUtil.evaluateAll(argExpressions, context);
		return BeanUtil.invokeStatic(targetClass, methodName, args);
    }

}
