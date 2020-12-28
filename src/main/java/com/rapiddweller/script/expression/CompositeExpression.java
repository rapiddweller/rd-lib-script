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
import com.rapiddweller.commons.ArrayUtil;
import com.rapiddweller.script.Expression;

/**
 * Expression that evaluates the results of other Expressions.<br/>
 * <br/>
 * Created: 18.06.2007 17:02:17
 * @author Volker Bergmann
 */
public abstract class CompositeExpression<S, R> implements WrapperExpression<R> {

	protected final String symbol;
    protected Expression<S>[] terms;

    @SafeVarargs
    protected CompositeExpression(Expression<S>... terms) {
    	this(null, terms);
    }

    @SafeVarargs
    protected CompositeExpression(String symbol, Expression<S>... terms) {
    	this.symbol = symbol;
        this.terms = terms;
    }

	public Expression<S>[] getTerms() {
		return terms;
	}
	
	@Override
	public Expression<?>[] getSourceExpressions() {
		return getTerms();
	}
	
    public void addTerm(Expression<S> term) {
    	this.terms = ArrayUtil.append(term, this.terms);
    }
    
    @Override
	public boolean isConstant() {
        for (Expression<?> term : terms)
        	if (!term.isConstant())
        		return false;
        return true;
    }
    
	@Override
	public String toString() {
	    return "(" + ArrayFormat.format(" " + symbol + " ", terms) + ")";
	}
	
}
