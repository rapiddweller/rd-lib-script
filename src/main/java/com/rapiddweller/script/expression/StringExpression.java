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
import com.rapiddweller.commons.converter.ToStringConverter;
import com.rapiddweller.script.Expression;

/**
 * Wrapper for an {@link Expression} that converts its result to a {@link String} 
 * using the {@link ToStringConverter}.<br/><br/>
 * Created: 27.10.2009 13:35:29
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class StringExpression extends UnaryExpression<String> {
	
	private final ToStringConverter converter;

	public StringExpression(Expression<?> term) {
	    super("(string) ", term);
	    this.converter = new ToStringConverter(null);
    }

    @Override
	public String evaluate(Context context) {
		Object tmp = term.evaluate(context);
		return (tmp instanceof String ? (String) tmp : converter.convert(tmp));
	}
	
}
