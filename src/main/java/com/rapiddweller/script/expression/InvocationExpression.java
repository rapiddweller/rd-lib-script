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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.Expression;

/**
 * {@link Expression} implementation that evaluates a method on a class or object.<br/><br/>
 * Created at 07.10.2009 22:10:06
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class InvocationExpression extends DynamicExpression<Object> {

  private final Expression<?> target;
  private final String methodName;
  private final Expression<?>[] argExpressions;

  public InvocationExpression(Expression<?> target, String methodMame, Expression<?>[] argExpressions) {
    this.target = target;
    this.methodName = methodMame;
    this.argExpressions = argExpressions;
  }

  @Override
  public Object evaluate(Context context) {
    Object[] args = ExpressionUtil.evaluateAll(argExpressions, context);
    return BeanUtil.invoke(target.evaluate(context), methodName, args);
  }

}
