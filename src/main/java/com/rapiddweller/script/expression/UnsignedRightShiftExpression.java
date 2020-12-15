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
import com.rapiddweller.script.Expression;
import com.rapiddweller.script.math.ArithmeticEngine;

/**
 * {@link Expression} that performs an unsigned right shift (&gt;&gt;&gt;).<br/><br/>
 * Created: 24.11.2010 14:21:52
 * @since 0.6.4
 * @author Volker Bergmann
 */
public class UnsignedRightShiftExpression extends BinaryExpression<Object> {
	
	public UnsignedRightShiftExpression(Expression<?> term1, Expression<?> term2) {
		super(">>>", term1, term2);
	}

	@Override
	public Object evaluate(Context context) {
		return ArithmeticEngine.defaultInstance().shiftRightUnsigned(term1.evaluate(context), term2.evaluate(context));
	}
	
}