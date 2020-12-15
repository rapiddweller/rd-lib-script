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

import com.rapiddweller.commons.ArrayFormat;
import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.Context;
import com.rapiddweller.script.Expression;

/**
 * {@link Expression} implementation that instantiates a Java object by constructor invocation.<br/>
 * <br/>
 * Created at 06.10.2009 11:48:59
 * @since 0.6.0
 * @author Volker Bergmann
 */

public class ParameterizedConstruction<E> extends Construction<E> {
	
	private Expression<?>[] argumentExpressions;

    public ParameterizedConstruction(String className, Expression<?>[] argumentExpressions) {
	    super(className);
	    this.argumentExpressions = argumentExpressions;
    }
    
	@Override
	@SuppressWarnings("unchecked")
    public E evaluate(Context context) {
		Class<?> type = getType(context);
		Object[] arguments = new Object[argumentExpressions.length];
		for (int i = 0; i < argumentExpressions.length; i++)
			arguments[i] = argumentExpressions[i].evaluate(context);
	    return (E) BeanUtil.newInstance(type, false, arguments);
    }

	public boolean classExists(Context context) {
		try {
			getType(context);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
	    return "new " + className + '(' + ArrayFormat.format(", ", argumentExpressions) + ')';
	}
	
}
