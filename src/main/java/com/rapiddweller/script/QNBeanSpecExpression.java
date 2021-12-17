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
import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.ExceptionUtil;
import com.rapiddweller.common.Expression;
import com.rapiddweller.common.bean.DefaultClassProvider;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.script.expression.DynamicExpression;

/**
 * {@link Expression} instance that evaluates the Benerator script notation for Java object specification
 * as one of the following:
 * <ul>
 *   <li>reference: <code>myInstance</code></li>
 *   <li>class name: <code>com.my.SpecialClass</code></li>
 *   <li>constructor invocation: <code>new com.my.SpecialClass(3, 'test')</code></li>
 *   <li>JavaBean property syntax: <code>new com.my.SpecialClass[id=3, name='test']</code></li>
 * </ul>
 * <br/>
 * Created at 08.10.2009 18:15:15
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class QNBeanSpecExpression extends DynamicExpression<Object> {

  final String[] qn;

  public QNBeanSpecExpression(String[] qn) {
    this.qn = qn;
  }

  @Override
  public Object evaluate(Context context) {
    return resolve(context).getBean();
  }

  public BeanSpec resolve(Context context) {
    String objectOrClassName = ArrayFormat.format(".", qn);
    try {
      if (context.contains(objectOrClassName)) {
        return BeanSpec.createReference(context.get(objectOrClassName));
      }
      Class<?> type = DefaultClassProvider.resolveByObjectOrDefaultInstance(objectOrClassName, true, context);
      return BeanSpec.createConstruction(BeanUtil.newInstance(type));
    } catch (ConfigurationError e) {
      if (ExceptionUtil.getRootCause(e) instanceof ClassNotFoundException) {
        return new QNExpression(qn).resolve(context);
      } else {
        throw ExceptionFactory.getInstance().configurationError("Cannot resolve " + objectOrClassName, e);
      }
    }
  }

}
