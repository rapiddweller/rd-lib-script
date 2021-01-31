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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.converter.AnyConverter;
import com.rapiddweller.script.Expression;

import java.util.List;
import java.util.Map;

/**
 * {@link Expression} that evaluates an index argument.<br/><br/>
 * Created: 24.11.2010 14:12:48
 * @since 0.6.4
 * @author Volker Bergmann
 */
public class IndexExpression extends BinaryExpression<Object> {
	
	public IndexExpression(Expression<?> term1, Expression<?> term2) {
		super(term1, term2);
	}

	@Override
	public Object evaluate(Context context) {
	    Object container = term1.evaluate(context);
	    Object indexObject = term2.evaluate(context);
	    if (container instanceof List) {
			int index = AnyConverter.convert(indexObject, Integer.class);
	    	return ((List<?>) container).get(index);
	    } else if (container.getClass().isArray()) {
			int index = AnyConverter.convert(indexObject, Integer.class);
	    	return ((Object[]) container)[index];
	    } else if (container instanceof String) {
			int index = AnyConverter.convert(indexObject, Integer.class);
	    	return ((String) container).charAt(index);
	    } else if (container instanceof Map) {
	    	return ((Map<?,?>) container).get(indexObject);
	    } else
	    	throw new IllegalArgumentException("Cannot do index-based access on " 
	    			+ BeanUtil.simpleClassName(container));
	}
	
	@Override
	public String toString() {
		return term1 + "[" + term2 + "]";
	}
	
}