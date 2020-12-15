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
package com.rapiddweller.script.math;

import java.util.Date;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.TimeUtil;

/**
 * {@link TypeArithmetic} implementation for the {@link Date} class.<br/>
 * <br/>
 * Created at 06.10.2009 10:31:14
 * @since 0.6.0
 * @author Volker Bergmann
 */

public class DateArithmetic extends TypeArithmetic<Date> {

    public DateArithmetic() {
	    super(Date.class);
    }
    
    // Arithmetic interface implementation -----------------------------------------------------------------------------

    @Override
    public Date add(Object summand1, Object summand2) {
    	if (summand1 instanceof Date)
    		return addImpl((Date) summand1, summand2);
    	else if (summand2 instanceof Date)
    		return addImpl((Date) summand2, summand1);
    	else
    		throw new IllegalArgumentException("No argument is of type " + baseType + ": " + 
    				summand1 + ", " + summand2);
    }

    @Override
    public Object subtract(Object minuend, Object subtrahend) {
    	return subtractImpl((Date) minuend, subtrahend);
    }

    @Override
    public Object multiply(Object factor1, Object factor2) {
	    throw new UnsupportedOperationException("Cannot multiply dates");
    }
    
    @Override
    public Object divide(Object quotient, Object divisor) {
	    throw new UnsupportedOperationException("Cannot divide dates");
    }
    
    // private methods -------------------------------------------------------------------------------------------------

	private Date addImpl(Date summand1, Object summand2) {
    	if (summand2 instanceof Number)
    		return new Date(summand1.getTime() + ((Number) summand2).longValue());
    	else if (summand2 instanceof Date)
    		return TimeUtil.add(summand1, (Date) summand2);
    	else
    		throw new IllegalArgumentException("Cannot add " +
    				BeanUtil.simpleClassName(summand2) + " to " + baseType.getClass().getName());
    }

    private static Object subtractImpl(Date minuend, Object subtrahend) {
	    if (subtrahend instanceof Number)
    		return new Date(minuend.getTime() - ((Number) subtrahend).longValue());
    	else if (subtrahend instanceof Date) {
    		return TimeUtil.subtract(minuend,  (Date) subtrahend);
    	} else
    		throw new IllegalArgumentException("Cannot subtract " +
    				BeanUtil.simpleClassName(subtrahend) + " from " + minuend.getClass().getName());
    }

}
