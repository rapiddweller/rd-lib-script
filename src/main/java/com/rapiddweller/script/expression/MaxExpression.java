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

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.ComparableComparator;
import com.rapiddweller.common.Context;
import com.rapiddweller.script.Expression;

import java.util.Comparator;

/**
 * Calculates the maximum value of several arguments.
 * <code>null</code> values are ignored <br/><br/>
 * Created: 19.10.2009 01:31:42
 *
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class MaxExpression<E> extends CompositeExpression<E, E> {

  private final Comparator<E> comparator;

  /**
   * Instantiates a new Max expression.
   *
   * @param terms the terms
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public MaxExpression(Expression<E>... terms) {
    this(new ComparableComparator(), terms);
  }

  /**
   * Instantiates a new Max expression.
   *
   * @param comparator the comparator
   * @param terms      the terms
   */
  @SafeVarargs
  public MaxExpression(Comparator<E> comparator, Expression<E>... terms) {
    super("", terms);
    this.comparator = comparator;
  }

  @Override
  public E evaluate(Context context) {
    E max = terms[0].evaluate(context);
    for (int i = 1; i < terms.length; i++) {
      E tmp = terms[i].evaluate(context);
      if (comparator.compare(tmp, max) > 0) {
        max = tmp;
      }
    }
    return max;
  }

  @Override
  public String toString() {
    return "max(" + ArrayFormat.format(terms) + ')';
  }

}
