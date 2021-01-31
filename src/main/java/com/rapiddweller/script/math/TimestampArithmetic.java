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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.TimeUtil;

import java.sql.Timestamp;
import java.util.Date;

/**
 * {@link TypeArithmetic} implementation for the {@link Date} type.<br/>
 * <br/>
 * Created at 06.10.2009 10:31:14
 * @since 0.6.0
 * @author Volker Bergmann
 */

public class TimestampArithmetic extends TypeArithmetic<Timestamp> {

    public TimestampArithmetic() {
	    super(Timestamp.class);
    }
    
    // Arithmetic interface implementation -----------------------------------------------------------------------------

    @Override
    public Timestamp add(Object summand1, Object summand2) {
    	if (summand1 instanceof Timestamp)
    		return addToTimestamp((Timestamp) summand1, summand2);
    	else if (summand2 instanceof Timestamp)
    		return addToTimestamp((Timestamp) summand2, summand1);
    	else
    		throw new IllegalArgumentException("No argument is of type " + baseType + ": " + 
    				summand1 + ", " + summand2);
    }

    @Override
    public Object subtract(Object minuend, Object subtrahend) {
    	if (minuend instanceof Timestamp)
    		return subtractFromTimestamp((Timestamp) minuend, subtrahend);
    	else
    		throw new IllegalArgumentException("No argument is of type " + baseType + ": " + 
    				minuend + ", " + subtrahend);
    }

    @Override
    public Object multiply(Object factor1, Object factor2) {
	    throw new UnsupportedOperationException("Cannot multiply timestamps");
    }

	@Override
	public Object divide(Object quotient, Object divisor) {
	    throw new UnsupportedOperationException("Cannot divide timestamps");
	}
	
    // private methods -------------------------------------------------------------------------------------------------
    
	private Timestamp addToTimestamp(Timestamp summand1, Object summand2) {
    	if (summand2 instanceof Number)
    		return new Timestamp(summand1.getTime() + ((Number) summand2).longValue());
    	else if (summand2 instanceof Timestamp)
    		return addTimestamps(summand1, (Timestamp) summand2);
    	else if (summand2 instanceof Date)
    		return addTimestamps(summand1, new Timestamp(((Date) summand2).getTime()));
    	else
    		throw new IllegalArgumentException("Cannot add " +
    				BeanUtil.simpleClassName(summand2) + " to " + baseType.getName());
    }

	private static Timestamp addTimestamps(Timestamp summand1, Timestamp summand2) {
	    int nanoSum = summand1.getNanos() + summand2.getNanos();
	    Timestamp result = new Timestamp(summand1.getTime() + TimeUtil.millisSinceOwnEpoch(summand2) + nanoSum / 1000000000L);
	    result.setNanos(nanoSum % 1000000000);
	    return result;
    }

    private static Timestamp subtractFromTimestamp(Timestamp minuend, Object subtrahend) {
    	if (subtrahend instanceof Number)
    		return new Timestamp(minuend.getTime() - ((Number) subtrahend).longValue());
    	else if (subtrahend instanceof Timestamp)
    		return subtractTimestamps(minuend, (Timestamp) subtrahend);
    	else if (subtrahend instanceof Date)
    		return subtractTimestamps(minuend, new Timestamp(((Date) subtrahend).getTime()));
    	else
    		throw new IllegalArgumentException("Cannot subtract " +
    				BeanUtil.simpleClassName(subtrahend) + " from " + minuend.getClass().getName());
    }

	private static Timestamp subtractTimestamps(Timestamp minuend, Timestamp subtrahend) {
	    int nanoDiff = minuend.getNanos() - subtrahend.getNanos();
	    if (nanoDiff < 0)
	    	nanoDiff += 1000000000;
	    Timestamp result = new Timestamp(minuend.getTime() - TimeUtil.millisSinceOwnEpoch(subtrahend) - nanoDiff / 1000000);
	    result.setNanos(nanoDiff % 1000000000);
	    return result;
    }

}
