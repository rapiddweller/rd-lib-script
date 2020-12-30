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

import com.rapiddweller.common.Context;
import com.rapiddweller.common.mutator.AnyMutator;
import com.rapiddweller.script.Expression;
import com.rapiddweller.script.QNExpression;

/**
 * Evaluates an assignment expression like <code>x.y = f.d + 3</code>.<br/><br/>
 * Created: 23.02.2010 10:55:56
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class AssignmentExpression extends DynamicExpression<Object> {
	
	private final String[] lhs;
	private final Expression<?> rhs;

	public AssignmentExpression(String[] lhs, Expression<?> rhs) {
	    this.lhs = lhs;
	    this.rhs = rhs;
    }

	@Override
	public Object evaluate(Context context) {
		Object value = rhs.evaluate(context);
		if (lhs.length == 1) {
			// if lhs is a simple variable name then put the result into context by this name
			context.set(lhs[0], value);
		} else {
			// get last parent object of QN and set the feature denoted by the last QN part
			String fieldName = lhs[lhs.length - 1];
			Object field = QNExpression.resolveNamePart(lhs, lhs.length - 1, context);
			AnyMutator.setValue(field, fieldName, value, true, true);
		}
	    return value;
    }

}
