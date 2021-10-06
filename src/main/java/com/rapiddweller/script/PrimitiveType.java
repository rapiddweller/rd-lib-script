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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Describes a primitive benerator type.<br/><br/>
 * Created: 27.02.2008 16:28:22
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class PrimitiveType {

  private static final Map<String, PrimitiveType> instancesByName = new HashMap<>();
  private static final Map<Class<?>, PrimitiveType> instancesByJavaType = new HashMap<>();

  public static final PrimitiveType BYTE_P = new PrimitiveType("byte", byte.class);
  public static final PrimitiveType BYTE = new PrimitiveType("byte", Byte.class);
  public static final PrimitiveType SHORT_P = new PrimitiveType("short", short.class);
  public static final PrimitiveType SHORT = new PrimitiveType("short", Short.class);
  public static final PrimitiveType INT_P = new PrimitiveType("int", int.class);
  public static final PrimitiveType INT = new PrimitiveType("int", Integer.class);
  public static final PrimitiveType LONG_P = new PrimitiveType("long", long.class);
  public static final PrimitiveType LONG = new PrimitiveType("long", Long.class);
  public static final PrimitiveType BIG_INTEGER = new PrimitiveType("big_integer", BigInteger.class);
  public static final PrimitiveType FLOAT_P = new PrimitiveType("float", float.class);
  public static final PrimitiveType FLOAT = new PrimitiveType("float", Float.class);
  public static final PrimitiveType DOUBLE_P = new PrimitiveType("double", double.class);
  public static final PrimitiveType DOUBLE = new PrimitiveType("double", Double.class);
  public static final PrimitiveType BIG_DECIMAL = new PrimitiveType("big_decimal", BigDecimal.class);
  public static final PrimitiveType BOOLEAN_P = new PrimitiveType("boolean", boolean.class);
  public static final PrimitiveType BOOLEAN = new PrimitiveType("boolean", Boolean.class);
  public static final PrimitiveType STRING = new PrimitiveType("string", String.class);
  public static final PrimitiveType DATE = new PrimitiveType("date", Date.class);
  public static final PrimitiveType TIME = new PrimitiveType("time", Time.class);
  public static final PrimitiveType TIMESTAMP = new PrimitiveType("timestamp", Timestamp.class);
  public static final PrimitiveType OBJECT = new PrimitiveType("object", Object.class);
  public static final PrimitiveType BINARY = new PrimitiveType("binary", byte[].class);
  public static final PrimitiveType ARRAY = new PrimitiveType("array", Object[].class);

  private final String name;
  private final Class<?> javaType;

  public PrimitiveType(String name, Class<?> javaType) {
    if (name == null) {
      throw new IllegalArgumentException("name is null");
    }
    if (javaType == null) {
      throw new IllegalArgumentException("javaType is null");
    }
    this.name = name;
    this.javaType = javaType;
    instancesByName.put(name, this);
    instancesByJavaType.put(javaType, this);
  }

  public String getName() {
    return name;
  }

  public Class<?> getJavaType() {
    return javaType;
  }

  public static PrimitiveType getInstance(String name) {
    return instancesByName.get(name);
  }

  public static PrimitiveType findByJavaType(Class<?> javaType) {
    return instancesByJavaType.get(javaType);
  }

  public static Collection<PrimitiveType> getInstances() {
    return instancesByName.values();
  }

  @Override
  public String toString() {
    return name;
  }

}