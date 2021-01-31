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

import java.util.Collection;

/**
 * Boolean {@link Expression} which tells if a value is contained in a given collection.<br/><br/>
 * Created: 08.06.2011 10:15:02
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class ValueCollectionContainsExpression extends BinaryExpression<Boolean> {

	public ValueCollectionContainsExpression(String symbol, Expression<?> term1, Expression<? extends Collection<?>> term2) {
		super(symbol, term1, term2);
	}

	@Override
	public Boolean evaluate(Context context) {
		Collection<?> collection = (Collection<?>) term2.evaluate(context);
		return collection.contains(term1.evaluate(context));
	}
	
}
