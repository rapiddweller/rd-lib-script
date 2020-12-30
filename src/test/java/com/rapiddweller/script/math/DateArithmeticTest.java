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

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import com.rapiddweller.common.TimeUtil;
import org.junit.Test;

/**
 * Tests the {@link DateArithmetic}.<br/><br/>
 * Created: 13.10.2009 17:34:36
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class DateArithmeticTest {
	
	private static final Date DATE     = TimeUtil.date(2009, 9, 13);
	private static final Time TIME     = TimeUtil.time(17, 36, 37, 389);
	private static final Date DATETIME = TimeUtil.date(2009, 9, 13, 17, 36, 37, 389);
	private static final long ONE_DAY_MILLIS = 24L * 3600 * 1000;
	
	final DateArithmetic arithmetic = new DateArithmetic();
	
	@Test
	public void testGetBaseType() {
		assertEquals(Date.class, arithmetic.getBaseType());
	}
	
	@Test
	public void testAdd() {
		assertEquals(DATETIME, arithmetic.add(DATE, TIME));
		assertEquals(TimeUtil.add(DATE, Calendar.DATE, 1), arithmetic.add(DATE, ONE_DAY_MILLIS));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAdd_IllegalType() {
		arithmetic.add(DATE, new File("test"));
	}

	@Test
	public void testSubtract() {
		assertEquals(DATE, arithmetic.subtract(DATETIME, TIME));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSubtract_IllegalType() {
		arithmetic.subtract(DATE, new File("test"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testMultiply() {
		arithmetic.multiply(DATE, TIME);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDivide() {
		arithmetic.divide(DATE, TIME);
	}

}
