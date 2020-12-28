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
 * {@link Expression} implementation that evaluates another expression and converts its results.<br/>
 * <br/>
 * Created at 28.07.2009 06:49:43
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class TypeConvertingExpression<E> extends UnaryExpression<E> {
	
	private final AnyConverter<E> converter;

    @SuppressWarnings("rawtypes")
    public TypeConvertingExpression(Expression source, Class<E> resultType) {
    	super("", source);
	    this.converter = new AnyConverter<>(resultType);
    }

	@Override
	public E evaluate(Context context) {
	    return converter.convert(term.evaluate(context));
    }

	@Override
	public String toString() {
	    return getClass().getSimpleName() + '[' + term + " -> " + converter + ']';
	}
	
}
