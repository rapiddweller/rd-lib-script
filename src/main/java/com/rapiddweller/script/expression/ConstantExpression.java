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
 * Expression that represents and returns a constant value.<br/><br/>
 * Created: 18.06.2007 17:38:58
 * @param <E> the type parameter
 * @author Volker Bergmann
 */
public class ConstantExpression<E> implements Expression<E> {

  private E value;

  public ConstantExpression(E value) {
    this.value = value;
  }

  public E getValue() {
    return value;
  }

  public void setValue(E value) {
    this.value = value;
  }

  @Override
  public E evaluate(Context context) {
    return value;
  }

  @Override
  public boolean isConstant() {
    return true;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

}
