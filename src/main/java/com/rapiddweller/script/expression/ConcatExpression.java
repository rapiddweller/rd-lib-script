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
import com.rapiddweller.common.Expression;

/**
 * String {@link Expression} that concatenates the output of other String Expressions.<br/><br/>
 * Created: 07.06.2011 22:03:08
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class ConcatExpression extends CompositeExpression<Object, String> {

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ConcatExpression(String symbol, Expression... terms) {
    super(symbol, terms);
  }

  @Override
  public String evaluate(Context context) {
    StringBuilder result = new StringBuilder(String.valueOf(terms[0].evaluate(context)));
    for (int i = 1; i < terms.length; i++) {
      result.append(terms[i].evaluate(context));
    }
    return result.toString();
  }

}
