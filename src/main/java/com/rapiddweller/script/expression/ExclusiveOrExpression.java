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
import com.rapiddweller.common.converter.AnyConverter;
import com.rapiddweller.script.Expression;

/**
 * Boolean {@link Expression} that checks if the result of one
 * expressions is less than the result of another one.<br/><br/>
 * Created: 24.11.2010 14:23:13
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class ExclusiveOrExpression extends BinaryExpression<Boolean> {

  /**
   * Instantiates a new Exclusive or expression.
   *
   * @param term1 the term 1
   * @param term2 the term 2
   */
  public ExclusiveOrExpression(Expression<?> term1, Expression<?> term2) {
    super("XOR", term1, term2);
  }

  @Override
  public Boolean evaluate(Context context) {
    Boolean arg1 = AnyConverter.convert(term1.evaluate(context), Boolean.class);
    Boolean arg2 = AnyConverter.convert(term2.evaluate(context), Boolean.class);
    return arg1 ^ arg2;
  }

}