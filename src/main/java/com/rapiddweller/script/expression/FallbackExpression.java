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
 * Expression which evaluates a list of expressions and returns the first result which is not null.<br/><br/>
 * Created: 16.06.2010 07:39:20
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 0.5.3
 */
public class FallbackExpression<E> extends CompositeExpression<E, E> {

  @SafeVarargs
  public FallbackExpression(Expression<E>... terms) {
    super(terms);
  }

  @Override
  public E evaluate(Context context) {
    for (Expression<E> term : terms) {
      E result = (term != null ? term.evaluate(context) : null);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

}
