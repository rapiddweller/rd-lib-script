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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.Context;
import com.rapiddweller.script.Expression;

/**
 * {@link Expression} implementation which assembles other expression that evaluate to arrays
 * and joins their results to a single array.<br/><br/>
 * Created: 11.09.2010 07:57:38
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class ArrayJoinExpression<E> extends CompositeExpression<E[], E[]> {

  private Class<E> componentType;

  @SafeVarargs
  public ArrayJoinExpression(Class<E> componentType, Expression<E[]>... terms) {
    super(terms);
    this.componentType = componentType;
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] evaluate(Context context) {
    E[][] arrays = (E[][]) ExpressionUtil.evaluateAll(terms, context);
    int totalLength = totalLength(arrays);
    E[] result = ArrayUtil.newInstance(componentType(arrays), totalLength);
    int resultIndex = 0;
    for (E[] array : arrays) {
      for (E e : array) {
        result[resultIndex] = e;
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private Class<E> componentType(E[][] arrays) {
    if (this.componentType == null) {
      for (E[] array : arrays) {
        if (array != null) {
          this.componentType = (Class<E>) array.getClass().getComponentType();
          break;
        }
      }
    }
    return (this.componentType != null ? this.componentType : (Class<E>) Object.class);
  }

  private static <T> int totalLength(T[][] arrays) {
    int totalLength = 0;
    for (T[] array : arrays) {
      totalLength += array.length;
    }
    return totalLength;
  }

}
