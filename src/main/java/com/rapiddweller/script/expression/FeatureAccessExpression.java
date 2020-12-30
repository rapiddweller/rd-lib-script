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
import com.rapiddweller.common.accessor.FeatureAccessor;

/**
 * Expression implementation that evaluates a feature value of an object.<br/>
 * <br/>
 * Created at 23.07.2009 14:59:41
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class FeatureAccessExpression<E> extends DynamicExpression<E> {

	private final String featureName;
	
	public FeatureAccessExpression(String featureName) {
		this.featureName = featureName;
	}
	
	@Override
	@SuppressWarnings("unchecked")
    public E evaluate(Context context) {
		return (E) FeatureAccessor.getValue(context, featureName);
    }

}
