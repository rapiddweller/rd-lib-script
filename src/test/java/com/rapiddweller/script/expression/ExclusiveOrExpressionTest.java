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

import com.rapiddweller.common.context.DefaultContext;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the ExclusiveOrExpression.<br/><br/>
 * Created: 08.06.2011 09:07:34
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class ExclusiveOrExpressionTest {

  /**
   * Test true true.
   */
  @Test
  public void testTrueTrue() {
    assertFalse(new ExclusiveOrExpression(ExpressionUtil.constant(true), ExpressionUtil.constant(true)).evaluate(new DefaultContext()));
  }

  /**
   * Test false false.
   */
  @Test
  public void testFalseFalse() {
    assertFalse(new ExclusiveOrExpression(ExpressionUtil.constant(false), ExpressionUtil.constant(false)).evaluate(new DefaultContext()));
  }

  /**
   * Test true false.
   */
  @Test
  public void testTrueFalse() {
    assertTrue(new ExclusiveOrExpression(ExpressionUtil.constant(true), ExpressionUtil.constant(false)).evaluate(new DefaultContext()));
  }

  /**
   * Test false true.
   */
  @Test
  public void testFalseTrue() {
    assertTrue(new ExclusiveOrExpression(ExpressionUtil.constant(false), ExpressionUtil.constant(true)).evaluate(new DefaultContext()));
  }

}
