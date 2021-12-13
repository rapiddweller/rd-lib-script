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
 * {@link Expression} that evaluates to the logical complement of another expression's result.<br/><br/>
 * Created: 24.11.2010 14:14:21
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class LogicalComplementExpression extends UnaryExpression<Boolean> {

  public LogicalComplementExpression(Expression<?> term) {
    this("!", term);
  }

  public LogicalComplementExpression(String symbol, Expression<?> term) {
    super(symbol, term);
  }

  @Override
  public Boolean evaluate(Context context) {
    return !AnyConverter.convert(term.evaluate(context), Boolean.class);
  }

}