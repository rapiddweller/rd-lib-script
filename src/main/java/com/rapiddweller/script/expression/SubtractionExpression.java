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

import com.rapiddweller.common.Context;
import com.rapiddweller.script.Expression;
import com.rapiddweller.script.math.ArithmeticEngine;

/**
 * Numerical {@link Expression} that performs a subtraction.<br/><br/>
 * Created: 24.11.2010 14:04:08
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class SubtractionExpression extends CompositeExpression<Object, Object> {

  /**
   * Instantiates a new Subtraction expression.
   */
  public SubtractionExpression() {
    super("-");
  }

  /**
   * Instantiates a new Subtraction expression.
   *
   * @param symbol the symbol
   * @param terms  the terms
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public SubtractionExpression(String symbol, Expression... terms) {
    super(symbol, terms);
  }

  @Override
  public Object evaluate(Context context) {
    Object result = terms[0].evaluate(context);
    for (int i = 1; i < terms.length; i++) {
      result = ArithmeticEngine.defaultInstance().subtract(result, terms[i].evaluate(context));
    }
    return result;
  }

}
