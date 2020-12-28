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

import com.rapiddweller.commons.Context;
import com.rapiddweller.commons.converter.AnyConverter;
import com.rapiddweller.script.Expression;

/**
 * {@link Expression} implementation that evaluates a boolean (condition) expression 
 * and, depending if the result was true or false, evaluates the first or second expression delegate.
 * The semantic is like the Java expression <code>(condition ? alt1 : alt2)</code>.<br/>
 * <br/>
 * Created at 06.10.2009 16:04:15
 * @since 0.6.0
 * @author Volker Bergmann
 */

public class ConditionalExpression<E> extends DynamicExpression<E> {
	
	private final Expression<Boolean> condition;
	private final Expression<? extends E> alt1;
	private final Expression<? extends E> alt2;
	
    public ConditionalExpression(Expression<Boolean> condition, 
    		Expression<? extends E> alt1, Expression<? extends E> alt2) {
	    this.condition = condition;
	    this.alt1 = alt1;
	    this.alt2 = alt2;
    }

	@Override
	public E evaluate(Context context) {
        boolean cond = AnyConverter.convert(condition.evaluate(context), Boolean.class);
        return (cond ? alt1 : alt2).evaluate(context);
    }

	@Override
	public String toString() {
	    return "(" + condition + " ? " + alt1 + " : " + alt2 + ")";
	}
	
}
