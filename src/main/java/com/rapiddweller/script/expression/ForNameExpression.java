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
import com.rapiddweller.common.bean.DefaultClassProvider;
import com.rapiddweller.script.Expression;

/**
 * Resolves a class by its class name.<br/><br/>
 * Created: 07.10.2010 11:41:59
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class ForNameExpression implements Expression<Class<?>>{
	
	protected final Expression<String> className;

	public ForNameExpression(Expression<String> className) {
	    this.className = className;
    }

	@Override
	public boolean isConstant() {
	    return className.isConstant();
    }

	@Override
	public Class<?> evaluate(Context context) {
		return DefaultClassProvider.resolveByObjectOrDefaultInstance(className.evaluate(context), context);
    }

}
