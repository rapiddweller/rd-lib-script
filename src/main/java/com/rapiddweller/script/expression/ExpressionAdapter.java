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

import com.rapiddweller.common.Expression;

/**
 * Adapter that makes use of a 'source' expression of one type
 * to calculate and return a result of a possibly different type.<br/><br/>
 * Created: 11.09.2010 07:01:10
 * @param <S> the type parameter
 * @param <T> the type parameter
 * @author Volker Bergmann
 * @since 0.5.4
 */
public abstract class ExpressionAdapter<S, T> implements Expression<T> {

  protected final Expression<S> source;

  public ExpressionAdapter(Expression<S> source) {
    this.source = source;
  }

  @Override
  public boolean isConstant() {
    return source.isConstant();
  }

}
