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
import com.rapiddweller.script.Expression;

/**
 * Evaluates an attribute of an object or class.<br/>
 * <br/>
 * Created at 08.10.2009 10:20:10
 * @since 0.6.0
 * @author Volker Bergmann
 */

public class FieldExpression extends DynamicExpression<Object> {
	
	private final Expression<?> targetEx;
	private final String featureName;

    public FieldExpression(Expression<?> targetEx, String featureName) {
    	this.targetEx = targetEx;
    	this.featureName = featureName;
    }

    @Override
	public Object evaluate(Context context) {
	    Object target = targetEx.evaluate(context);
	    return FeatureAccessor.getValue(target, featureName);
    }

    @Override
    public String toString() {
    	return targetEx + "." + featureName;
    }
    
}
