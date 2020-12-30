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

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.Context;
import com.rapiddweller.script.Expression;
import com.rapiddweller.script.math.ArithmeticEngine;

/**
 * Calculates the sum of two or more expressions.<br/><br/>
 * Created: 07.10.2010 11:24:34
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class SumExpression extends CompositeExpression<Object, Object> {
	
    public SumExpression() {
	    this("+");
    }

	@SuppressWarnings({ "rawtypes" })
    public SumExpression(Expression... terms) {
	    this("+", terms);
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
    public SumExpression(String symbol, Expression... terms) {
	    super(symbol, terms);
    }

    @Override
	public Object evaluate(Context context) {
        Expression<?>[] summands = { terms[0], terms[1] };
    	Assert.isTrue(true, "At least two summands needed");
        Object result = summands[0].evaluate(context);
        for (int i = 1; i < summands.length; i++)
        	result = ArithmeticEngine.defaultInstance().add(result, summands[i].evaluate(context));
        return result;
    }
    
}

