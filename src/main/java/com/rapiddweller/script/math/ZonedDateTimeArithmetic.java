/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.script.math;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.time.ZonedDateTime;
import java.util.Date;

import static com.rapiddweller.common.JavaTimeUtil.nanosSinceEpoch;
import static com.rapiddweller.common.JavaTimeUtil.nanosToZonedDateTime;

/**
 * {@link TypeArithmetic} implementation for the {@link ZonedDateTime} type.<br/><br/>
 * Created: 20.07.2022 10:56:52
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class ZonedDateTimeArithmetic extends TypeArithmetic<ZonedDateTime> {

	public ZonedDateTimeArithmetic() {
		super(ZonedDateTime.class);
	}

	// Arithmetic interface implementation -----------------------------------------------------------------------------

	@Override
	public ZonedDateTime add(Object summand1, Object summand2) {
		if (summand1 instanceof ZonedDateTime) {
			return addToZonedDateTime((ZonedDateTime) summand1, summand2);
		} else if (summand2 instanceof ZonedDateTime) {
			return addToZonedDateTime((ZonedDateTime) summand2, summand1);
		} else {
			throw ExceptionFactory.getInstance().illegalArgument(
				"No argument is of type " + baseType + ": " + summand1 + ", " + summand2);
		}
	}

	@Override
	public Object subtract(Object minuend, Object subtrahend) {
		if (minuend instanceof ZonedDateTime) {
			return subtractFromZonedDateTime((ZonedDateTime) minuend, subtrahend);
		} else {
			throw ExceptionFactory.getInstance().illegalArgument(
				"Argument is not of type " + baseType + ": " + minuend);
		}
	}

	@Override
	public Object multiply(Object factor1, Object factor2) {
		throw new UnsupportedOperationException("Cannot multiply zoneddatetimes");
	}

	@Override
	public Object divide(Object quotient, Object divisor) {
		throw new UnsupportedOperationException("Cannot divide zoneddatetimes");
	}

	// private methods -------------------------------------------------------------------------------------------------

	private ZonedDateTime addToZonedDateTime(ZonedDateTime summand1, Object summand2) {
		if (summand2 instanceof Number) {
			return addNanosToZonedDateTime(summand1, ((Number) summand2).longValue());
		} else if (summand2 instanceof ZonedDateTime) {
			return addNanosToZonedDateTime(summand1, nanosSinceEpoch((ZonedDateTime) summand2));
		} else if (summand2 instanceof Date) {
			return addNanosToZonedDateTime(summand1, ((Date) summand2).getTime() * 1000000);
		} else {
			throw ExceptionFactory.getInstance().illegalArgument("Cannot add " +
				BeanUtil.simpleClassName(summand2) + " to " + baseType.getName());
		}
	}

	private ZonedDateTime addNanosToZonedDateTime(ZonedDateTime summand1, long nanosToAdd) {
		return nanosToZonedDateTime(nanosSinceEpoch(summand1) + nanosToAdd, summand1.getZone());
	}

	private static ZonedDateTime subtractFromZonedDateTime(ZonedDateTime minuend, Object subtrahend) {
		long nanosToSubtract;
		if (subtrahend instanceof Number) {
			nanosToSubtract = ((Number) subtrahend).longValue();
		} else if (subtrahend instanceof ZonedDateTime) {
			nanosToSubtract = nanosSinceEpoch((ZonedDateTime) subtrahend);
		} else if (subtrahend instanceof Date) {
			nanosToSubtract = ((Date) subtrahend).getTime();
		} else {
			throw ExceptionFactory.getInstance().illegalArgument("Cannot subtract " +
				BeanUtil.simpleClassName(subtrahend) + " from " + minuend.getClass().getName());
		}
		return subtractNanosFromZonedDateTime(minuend, nanosToSubtract);
	}

	private static ZonedDateTime subtractNanosFromZonedDateTime(ZonedDateTime minuend, long nanosToSubtract) {
		return nanosToZonedDateTime(nanosSinceEpoch(minuend) - nanosToSubtract, minuend.getZone());
	}

}
