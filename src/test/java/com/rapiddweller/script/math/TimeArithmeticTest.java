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

import com.rapiddweller.common.TimeUtil;
import com.rapiddweller.common.exception.IllegalArgumentError;
import org.junit.Test;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link TimeArithmetic}.<br/><br/>
 * Created: 13.10.2009 18:00:06
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class TimeArithmeticTest {

  private static final Time TIME_BASE = TimeUtil.time(1, 2, 3, 456);
  private static final Time TIME_OFFSET = TimeUtil.time(6, 5, 4, 321);
  private static final Time TIME_SUM = TimeUtil.time(7, 7, 7, 777);
  private static final long ONE_OUR_MILLIS = 3600L * 1000;

  final TimeArithmetic arithmetic = new TimeArithmetic();

  @Test
  public void testGetBaseType() {
    assertEquals(Time.class, arithmetic.getBaseType());
  }

  @Test
  public void testAdd_Time() {
    assertEquals(TIME_SUM, arithmetic.add(TIME_BASE, TIME_OFFSET));
  }

  @Test
  public void testAdd_Millis() {
    assertEquals(TimeUtil.add(TIME_BASE, Calendar.HOUR, 1), arithmetic.add(TIME_BASE, ONE_OUR_MILLIS));
  }

  @Test(expected = IllegalArgumentError.class)
  public void testAdd_IllegalType() {
    arithmetic.add(TIME_BASE, new File("test"));
  }

  @Test
  public void testSubtract() {
    assertEquals(TIME_BASE, arithmetic.subtract(TIME_SUM, TIME_OFFSET));
    assertEquals(TIME_OFFSET, arithmetic.subtract(TIME_SUM, TIME_BASE));
  }

  @Test(expected = IllegalArgumentError.class)
  public void testSubtract_IllegalType() {
    arithmetic.subtract(TIME_BASE, new File("test"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testMultiply() {
    arithmetic.multiply(TIME_BASE, TIME_OFFSET);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testDivide() {
    arithmetic.divide(TIME_BASE, TIME_OFFSET);
  }

}
