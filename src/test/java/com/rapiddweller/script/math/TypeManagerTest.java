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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link TypeManager}.<br/>
 * <br/>
 * Created at 06.10.2009 09:55:20
 *
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class TypeManagerTest {

  /**
   * Test same.
   */
  @Test
  public void testSame() {
    check(Integer.class, Integer.class, Integer.class);
    check(Long.class, Long.class, Long.class);
  }

  /**
   * Test different.
   */
  @Test
  public void testDifferent() {
    check(Long.class, Integer.class, Long.class);        // 1st < 2nd
    check(Long.class, Long.class, Integer.class);        // 2nd < 1st
  }

  private static void check(Class<?> expectedResult, Class<?> type1, Class<?> type2) {
    assertEquals(expectedResult, TypeManager.combinedType(type1, type2));
  }

}
