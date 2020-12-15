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

import com.rapiddweller.script.Expression;

/**
 * Abstract parent class for expression that evaluate two terms.<br/>
 * <br/>
 * Created at 06.10.2009 14:26:04
 * @since 0.5.0
 * @author Volker Bergmann
 */

public abstract class BinaryExpression<E> implements WrapperExpression<E> {

	protected String symbol;
	protected Expression<?> term1;
	protected Expression<?> term2;

	public BinaryExpression(Expression<?> term1, Expression<?> term2) {
		this(null, term1, term2);
    }
	
	public BinaryExpression(String symbol, Expression<?> term1, Expression<?> term2) {
		this.symbol = symbol;
	    this.term1 = term1;
	    this.term2 = term2;
    }
	
	@Override
	public Expression<?>[] getSourceExpressions() {
		return new Expression[] { term1, term2 };
	}
	
	@Override
	public boolean isConstant() {
	    return term1.isConstant() && term2.isConstant();
	}
	
	@Override
	public String toString() {
		return "(" + term1 + " " + symbol + " " + term2 + ")";
	}
	
}
