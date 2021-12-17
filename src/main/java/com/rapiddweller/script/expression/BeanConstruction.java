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
import com.rapiddweller.common.context.ContextAware;
import com.rapiddweller.common.mutator.AnyMutator;
import com.rapiddweller.script.Assignment;
import com.rapiddweller.common.Expression;

/**
 * {@link Expression} implementation that instantiates a JavaBean by default constructor and
 * calls its property setters for initializing state.<br/><br/>
 * Created at 06.10.2009 11:48:59
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class BeanConstruction<E> extends DynamicExpression<E> {

  private final Expression<E> instantiation;
  private final Assignment[] assignments;

  public BeanConstruction(String beanClassName, Assignment[] assignments) {
    this(new DefaultConstruction<>(beanClassName), assignments);
  }

  public BeanConstruction(Expression<E> instantiation, Assignment[] assignments) {
    this.instantiation = instantiation;
    this.assignments = assignments;
  }

  @Override
  public E evaluate(Context context) {
    E bean = instantiation.evaluate(context);
    for (Assignment assignment : assignments) {
      String name = assignment.getName();
      Object value = assignment.getExpression().evaluate(context);
      if (BeanUtil.hasProperty(bean.getClass(), name)) {
        BeanUtil.setPropertyValue(bean, name, value, false);
      } else {
        AnyMutator.setValue(bean, name, value, true, true);
      }
    }
    if (bean instanceof ContextAware) {
      ((ContextAware) bean).setContext(context);
    }
    return bean;
  }

}
