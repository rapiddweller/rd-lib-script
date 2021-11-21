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

package com.rapiddweller.script;

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.bean.DefaultClassProvider;
import com.rapiddweller.script.expression.DynamicExpression;
import com.rapiddweller.script.expression.ExpressionUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * {@link Expression} implementation that evaluates an invocation syntax on a qualified name
 * as static method call or call on an object reference.<br/><br/>
 * Created at 07.10.2009 22:27:26
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class QNInvocationExpression extends DynamicExpression<Object> {

  private static final Logger logger = LoggerFactory.getLogger(QNInvocationExpression.class);

  private final String[] qn;
  private final Expression<?>[] argExpressions;

  public QNInvocationExpression(String[] qn, Expression<?>[] argExpressions) {
    this.qn = qn;
    this.argExpressions = argExpressions;
  }

  @Override
  public Object evaluate(Context context) {
    Object[] args = ExpressionUtil.evaluateAll(argExpressions, context);
    String methodName = ArrayUtil.lastElementOf(qn);
    return invoke(qn, qn.length - 1, methodName, args, context);
  }

  private static Object invoke(String[] qn, int qnLength, String methodName, Object[] args, Context context) {
    String objectOrClassName = ArrayFormat.formatPart(".", 0, qnLength, qn);
    if (context.contains(objectOrClassName)) {
      Object target = context.get(objectOrClassName);
      return BeanUtil.invoke(target, methodName, args);
    }
    try {
      Class<?> type = DefaultClassProvider.resolveByObjectOrDefaultInstance(objectOrClassName, false, context);
      if (type != null)
        return BeanUtil.invokeStatic(type, methodName, false, args);
    } catch (ConfigurationError e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Class not found: " + objectOrClassName);
      }
    }
    QNExpression ownerEx = new QNExpression(Arrays.copyOfRange(qn, 0, qnLength));
    Object owner = ownerEx.evaluate(context);
    if (owner != null) {
      return BeanUtil.invoke(false, owner, methodName, args);
    }
    throw new UnsupportedOperationException("Cannot evaluate " + objectOrClassName);
  }

  @Override
  public String toString() {
    return ArrayFormat.format(".", qn) + '(' + ArrayFormat.format(argExpressions) + ')';
  }
}
