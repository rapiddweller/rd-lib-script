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
 * Boolean {@link Expression} that evaluates if the first term is greater than the second.<br/><br/>
 * Created: 24.11.2010 14:23:40
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class GreaterExpression extends BinaryExpression<Boolean> {

  /**
   * Instantiates a new Greater expression.
   *
   * @param term1 the term 1
   * @param term2 the term 2
   */
  public GreaterExpression(Expression<?> term1, Expression<?> term2) {
    super(">", term1, term2);
  }

  @Override
  public Boolean evaluate(Context context) {
    return ArithmeticEngine.defaultInstance().greater(term1.evaluate(context), term2.evaluate(context));
  }

}