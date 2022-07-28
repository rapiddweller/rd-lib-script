/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.script.math;

import com.rapiddweller.common.exception.IllegalArgumentError;
import org.junit.Test;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link ZonedDateTimeArithmetic}.<br/><br/>
 * Created: 20.07.2022 11:33:40
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class ZonedDateTimeArithmeticTest {

	private static final ZonedDateTime BASE    = ZonedDateTime.of(2022, 7, 20, 11, 36, 25, 123456789, ZoneId.of("UTC"));
	private static final ZonedDateTime OFFSET1 = ZonedDateTime.of(1970, 1,  1,  6,  5,  4, 876543210, ZoneId.of("UTC"));
	private static final ZonedDateTime SUM1    = ZonedDateTime.of(2022, 7, 20, 17, 41, 29, 999999999, ZoneId.of("UTC"));
	private static final ZonedDateTime OFFSET2 = ZonedDateTime.of(1970, 1,  3,  0,  0,  0, 999999999, ZoneId.of("UTC"));
	private static final ZonedDateTime SUM2    = ZonedDateTime.of(2022, 7, 22, 11, 36, 26, 123456788, ZoneId.of("UTC"));
	private static final long ONE_HOUR_NANOS = 3600L * 1000000000L;

	final ZonedDateTimeArithmetic arithmetic = new ZonedDateTimeArithmetic();

	@Test
	public void testGetBaseType() {
		assertEquals(ZonedDateTime.class, arithmetic.getBaseType());
	}

	@Test
	public void testAdd_ZonedDateTime_simple() {
		assertEquals(SUM1, arithmetic.add(BASE, OFFSET1));
	}

	@Test
	public void testAdd_ZonedDateTime_nano_overrun() {
		assertEquals(SUM2, arithmetic.add(BASE, OFFSET2));
	}

	@Test
	public void testAdd_Nanos() {
		assertEquals(BASE.plusHours(1), arithmetic.add(BASE, ONE_HOUR_NANOS));
	}

	@Test(expected = IllegalArgumentError.class)
	public void testAdd_IllegalType() {
		arithmetic.add(BASE, new File("test"));
	}

	@Test
	public void testSubtract1() {
		// normal test
		assertEquals(BASE, arithmetic.subtract(SUM1, OFFSET1));
		assertEquals(OFFSET1, arithmetic.subtract(SUM1, BASE));
	}

	@Test
	public void testSubtract2() {
		// testing nano underrrun
		assertEquals(BASE, arithmetic.subtract(SUM2, OFFSET2));
		assertEquals(OFFSET2, arithmetic.subtract(SUM2, BASE));
	}

	@Test
	public void testSubtract_Nanos() {
		assertEquals(BASE.minusHours(1), arithmetic.subtract(BASE, ONE_HOUR_NANOS));
	}

	@Test(expected = IllegalArgumentError.class)
	public void testSubtract_IllegalType() {
		arithmetic.subtract(BASE, new File("test"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testMultiply() {
		arithmetic.multiply(BASE, OFFSET1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDivide() {
		arithmetic.divide(BASE, OFFSET1);
	}


}
