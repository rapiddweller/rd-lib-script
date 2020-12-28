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
package com.rapiddweller.script;

import com.rapiddweller.commons.Context;
import com.rapiddweller.commons.Filter;
import com.rapiddweller.commons.context.ContextHolder;

/**
 * {@link Filter} implementation based on a boolean filter {@link Expression}.<br/><br/>
 * Created: 08.03.2011 12:00:26
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class ExpressionBasedFilter<E> extends ContextHolder implements Filter<E> {

	protected final Expression<Boolean> expression;
	
	public ExpressionBasedFilter(Expression<Boolean> expression, Context context) {
		super(context);
		this.expression = expression;
	}

	@Override
	public boolean accept(E candidate) {
		context.set("_candidate", candidate);
		return expression.evaluate(context);
	}

}
