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
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.ObjectNotFoundException;
import com.rapiddweller.common.accessor.FeatureAccessor;
import com.rapiddweller.common.bean.DefaultClassProvider;
import com.rapiddweller.script.expression.DynamicExpression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@link Expression} implementation that evaluates a qualified name as attributes of an object reference or
 * static fields of a Java class.<br/><br/>
 * Created at 08.10.2009 07:18:53
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class QNExpression extends DynamicExpression<Object> {

  private static final Logger LOGGER = LogManager.getLogger(DatabeneScriptParser.class);

  private final String[] qnParts;

  public QNExpression(String[] qnParts) {
    this.qnParts = qnParts;
  }

  @Override
  public Object evaluate(Context context) {
    try {
      return resolveNamePart(qnParts, qnParts.length, context);
    } catch (ObjectNotFoundException e) {
      throw new ObjectNotFoundException("Unable to resolve " + ArrayFormat.format(".", qnParts));
    }
  }

  public static Object resolveNamePart(String[] qnParts, int qnLength, Context context) {
    String objectOrClassName = ArrayFormat.formatPart(".", 0, qnLength, qnParts);
    if (context.contains(objectOrClassName)) {
      return context.get(objectOrClassName);
    } else {
      Class<?> result = DefaultClassProvider.resolveByObjectOrDefaultInstance(objectOrClassName, context);
      if (result != null) {
        return result;
      }
      LOGGER.debug("Class not found: {}", objectOrClassName);
      if (qnLength > 1) {
        return readField(qnParts, qnLength - 1, qnParts[qnLength - 1], context);
      } else {
        throw new ObjectNotFoundException("'" + objectOrClassName + "' is not defined");
      }
    }
  }

  private static Object readField(String[] qnParts, int qnLength, String fieldName, Context context) {
    return FeatureAccessor.getValue(resolveNamePart(qnParts, qnLength, context), fieldName);
  }

  public BeanSpec resolve(Context context) {
    String qn = ArrayFormat.format(".", qnParts);
    if (context.contains(qn)) {
      return BeanSpec.createReference(context.get(qn));
    } else {
      try {
        Class<?> bean = DefaultClassProvider.resolveByObjectOrDefaultInstance(qn, context);
        return BeanSpec.createConstruction(bean);
      } catch (ConfigurationError e) {
        // ignore this
      }
      LOGGER.debug("Class not found: {}", qn);
      Object bean = readField(qnParts, qnParts.length - 1, ArrayUtil.lastElementOf(qnParts), context);
      return BeanSpec.createReference(bean);
    }
  }

  @Override
  public String toString() {
    return ArrayFormat.format(".", qnParts);
  }

}

