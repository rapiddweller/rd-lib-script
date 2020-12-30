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
import com.rapiddweller.script.Expression;
import com.rapiddweller.script.math.ArithmeticEngine;

/**
 * {@link Expression} which calculates the unary minus operation.<br/><br/>
 * Created: 08.06.2011 09:37:40
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class UnaryMinusExpression<E> extends UnaryExpression<E> {

	public UnaryMinusExpression(Expression<?> term) {
		this("-", term);
	}

	public UnaryMinusExpression(String symbol, Expression<?> term) {
		super(symbol, term);
	}   

	@Override
	@SuppressWarnings("unchecked")
	public E evaluate(Context context) {
		return (E) ArithmeticEngine.defaultInstance().negate(term.evaluate(context));
	}

}
