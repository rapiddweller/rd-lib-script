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

/**
 * Expression that returns <code>true</code>, if a source expression returns <code>null</code>,
 * else <code>false</code>.<br/><br/>
 * Created: 08.10.2010 23:04:01
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class IsNullExpression extends UnaryExpression<Boolean> {

  /**
   * Instantiates a new Is null expression.
   *
   * @param term the term
   */
  public IsNullExpression(Expression<?> term) {
    super("", term);
  }

  @Override
  public Boolean evaluate(Context context) {
    return term.evaluate(context) == null;
  }

  @Override
  public String toString() {
    return "(" + term + "IS NULL)";
  }

}
