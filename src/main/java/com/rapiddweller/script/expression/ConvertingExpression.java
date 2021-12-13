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
import com.rapiddweller.common.Converter;
import com.rapiddweller.script.Expression;

/**
 * {@link Expression} adapter that uses a {@link Converter} for converting
 * the result of a given expression (e.g. to a different type).<br/><br/>
 * Created: 11.09.2010 06:59:38
 * @param <S> the type parameter
 * @param <T> the type parameter
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class ConvertingExpression<S, T> extends ExpressionAdapter<S, T> {

  protected final Converter<S, T> converter;

  public ConvertingExpression(Expression<S> source, Converter<S, T> converter) {
    super(source);
    this.converter = converter;
  }

  @Override
  public T evaluate(Context context) {
    return converter.convert(source != null ? source.evaluate(context) : null);
  }

}
