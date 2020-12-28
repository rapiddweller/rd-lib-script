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

import java.util.ArrayList;
import java.util.List;

import com.rapiddweller.commons.Context;
import com.rapiddweller.script.Expression;

/**
 * Provides {@link Expression}-related utility methods.<br/>
 * <br/>
 * Created at 07.10.2009 22:33:14
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class ExpressionUtil {

    public static Object[] evaluateAll(Expression<?>[] expressions, Context context) {
	    Object[] result = new Object[expressions.length];
		for (int i = 0; i < expressions.length; i++)
			result[i] = expressions[i].evaluate(context);
	    return result;
    }

    public static boolean isNull(Expression<?> ex) {
    	if (ex == null)
    		return true;
    	return (ex instanceof ConstantExpression && ((ConstantExpression<?>) ex).getValue() == null);   
    }

	public static List<Object> evaluateAll(List<Expression<?>> expressions, Context context) {
	    List<Object> result = new ArrayList<>(expressions.size());
		for (Expression<?> expression : expressions)
			result.add(expression.evaluate(context));
	    return result;
    }

	public static <T> T evaluate(Expression<T> expression, Context context) {
	    return (expression != null ? expression.evaluate(context) : null);
    }

	public static <T> Expression<T> constant(T value) {
	    return new ConstantExpression<>(value);
    }
	
	public Expression<String> unescape(Expression<String> source) {
		return new UnescapeExpression(source);
	}
	
	public static <T> Expression<T> simplify(Expression<T> expression, Context context) {
		if (expression.isConstant() && !(expression instanceof ConstantExpression)) 
			return new ConstantExpression<>(evaluate(expression, context));
		else
			return expression;
	}
	
}
