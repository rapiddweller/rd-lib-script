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
import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link TimestampArithmetic}.<br/><br/>
 * Created: 14.10.2009 11:24:55
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class TimestampArithmeticTest {

  private static final Timestamp TS_BASE = TimeUtil.timestamp(2009, 9, 14, 1, 2, 3, 123456789);
  private static final Timestamp TS_OFFSET = TimeUtil.timestamp(1970, 0, 1, 6, 5, 4, 876543210);
  private static final Timestamp TS_SUM = TimeUtil.timestamp(2009, 9, 14, 7, 7, 7, 999999999);
  private static final Timestamp TS_OFFSET2 = TimeUtil.timestamp(1970, 0, 1, 0, 0, 0, 999999999);
  private static final Timestamp TS_SUM2 = TimeUtil.timestamp(2009, 9, 14, 1, 2, 4, 123456788);
  private static final long ONE_OUR_MILLIS = 3600L * 1000;

  final TimestampArithmetic arithmetic = new TimestampArithmetic();

  @Test
  public void testGetBaseType() {
    assertEquals(Timestamp.class, arithmetic.getBaseType());
  }

  @Test
  public void testAdd_Timestamp() {
    // simple test
    assertEquals(TS_SUM, arithmetic.add(TS_BASE, TS_OFFSET));
    // testing nano overrun
    assertEquals(TS_SUM2, arithmetic.add(TS_BASE, TS_OFFSET2));
  }

  @Test
  public void testAdd_Millis() {
    assertEquals(TimeUtil.add(TS_BASE, Calendar.HOUR, 1), arithmetic.add(TS_BASE, ONE_OUR_MILLIS));
  }

  @Test(expected = IllegalArgumentError.class)
  public void testAdd_IllegalType() {
    arithmetic.add(TS_BASE, new File("test"));
  }

  @Test
  public void testSubtract() {
    // normal test
    assertEquals(TS_BASE, arithmetic.subtract(TS_SUM, TS_OFFSET));
    assertEquals(TS_OFFSET, arithmetic.subtract(TS_SUM, TS_BASE));
    // testing nano underrrun
    assertEquals(TS_BASE, arithmetic.subtract(TS_SUM2, TS_OFFSET2));
    assertEquals(TS_OFFSET2, arithmetic.subtract(TS_SUM2, TS_BASE));
  }

  @Test
  public void testSubtract_Millis() {
    assertEquals(TimeUtil.add(TS_BASE, Calendar.HOUR, -1), arithmetic.subtract(TS_BASE, ONE_OUR_MILLIS));
  }

  @Test(expected = IllegalArgumentError.class)
  public void testSubtract_IllegalType() {
    arithmetic.subtract(TS_BASE, new File("test"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testMultiply() {
    arithmetic.multiply(TS_BASE, TS_OFFSET);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testDivide() {
    arithmetic.divide(TS_BASE, TS_OFFSET);
  }

}
