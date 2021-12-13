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
 * Boolean {@link Expression} that tells if a value lies within a given range.<br/><br/>
 * Created: 07.06.2011 22:07:56
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class InRangeExpression extends CompositeExpression<Object, Boolean> {

  @SuppressWarnings({"rawtypes", "unchecked"})
  public InRangeExpression(Expression value, Expression min, Expression max) {
    super(value, min, max);
  }

  @Override
  public Boolean evaluate(Context context) {
    Object value = terms[0].evaluate(context);
    Object minValue = terms[1].evaluate(context);
    Object maxValue = terms[2].evaluate(context);
    return ArithmeticEngine.defaultInstance().lessOrEquals(minValue, value) &&
        ArithmeticEngine.defaultInstance().lessOrEquals(value, maxValue);
  }

  @Override
  public String toString() {
    return "(" + terms[0] + " <= " + terms[1] + " <= " + terms[2] + ")";
  }

}
