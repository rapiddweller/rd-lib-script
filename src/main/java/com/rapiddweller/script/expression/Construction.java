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
import com.rapiddweller.commons.bean.DefaultClassProvider;

/**
 * Common parent class for Expressions that instantiate a Java object.<br/><br/>
 * Created: 25.10.2009 08:29:14
 * @since 0.6.0
 * @author Volker Bergmann
 */
public abstract class Construction<E> extends DynamicExpression<E> {

	protected final String className;

	public Construction(String className) {
		this.className = className;
    }

	public String getClassName() {
    	return className;
    }

    @SuppressWarnings("unchecked")
    public Class<E> getType(Context context) {
	    return (Class<E>) DefaultClassProvider.resolveByObjectOrDefaultInstance(className, context);
    }

}
