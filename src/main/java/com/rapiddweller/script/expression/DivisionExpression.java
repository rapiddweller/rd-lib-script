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
 * {@link Expression} that performs a division.<br/><br/>
 * Created: 24.11.2010 14:09:31
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class DivisionExpression extends CompositeExpression<Object, Object> {

  /**
   * Instantiates a new Division expression.
   */
  public DivisionExpression() {
    this("/");
  }

  /**
   * Instantiates a new Division expression.
   *
   * @param terms the terms
   */
  @SuppressWarnings({"rawtypes"})
  public DivisionExpression(Expression... terms) {
    this("/", terms);
  }

  /**
   * Instantiates a new Division expression.
   *
   * @param symbol the symbol
   * @param terms  the terms
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public DivisionExpression(String symbol, Expression... terms) {
    super(symbol, terms);
  }

  @Override
  public Object evaluate(Context context) {
    Object result = terms[0].evaluate(context);
    for (int i = 1; i < terms.length; i++) {
      result = ArithmeticEngine.defaultInstance().divide(result, terms[i].evaluate(context));
    }
    return result;
  }

}