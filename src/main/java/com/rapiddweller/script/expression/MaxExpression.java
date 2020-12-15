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

import java.util.Comparator;

import com.rapiddweller.commons.ArrayFormat;
import com.rapiddweller.commons.ComparableComparator;
import com.rapiddweller.commons.Context;
import com.rapiddweller.script.Expression;

/**
 * Calculates the maximum value of several arguments. 
 * <code>null</code> values are ignored <br/><br/>
 * Created: 19.10.2009 01:31:42
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class MaxExpression<E> extends CompositeExpression<E,E> {

	private Comparator<E> comparator;

	@SuppressWarnings({ "unchecked", "rawtypes" })
    public MaxExpression(Expression<E>... terms) {
	    this(new ComparableComparator(), terms);
    }

	public MaxExpression(Comparator<E> comparator, Expression<E>... terms) {
	    super("", terms);
	    this.comparator = comparator;
    }

    @Override
	public E evaluate(Context context) {
    	E max = terms[0].evaluate(context);
	    for (int i = 1; i < terms.length; i++) {
	    	E tmp = terms[i].evaluate(context);
	    	if (comparator.compare(tmp, max) > 0)
	    		max = tmp;
	    }
	    return max;
    }

    @Override
    public String toString() {
        return "max(" + ArrayFormat.format(terms) + ')';
    }
    
}
