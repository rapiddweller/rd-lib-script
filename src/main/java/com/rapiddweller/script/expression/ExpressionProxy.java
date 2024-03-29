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
 * Parent class for all expression classes that act as proxy of another class.<br/><br/>
 * Created: 21.10.2009 14:43:08
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ExpressionProxy<E> extends ExpressionAdapter<E, E> {

  public ExpressionProxy(Expression<E> source) {
    super(source);
  }

  @Override
  public E evaluate(Context context) {
    return source.evaluate(context);
  }

}
