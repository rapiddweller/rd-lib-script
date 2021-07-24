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
 * Describes a primitive benerator type.<br/>
 * <br/>
 * Created: 27.02.2008 16:28:22
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class PrimitiveType {

  private static final Map<String, PrimitiveType> instancesByName = new HashMap<>();
  private static final Map<Class<?>, PrimitiveType> instancesByJavaType = new HashMap<>();

  /**
   * The constant BYTE_P.
   */
  public static final PrimitiveType BYTE_P = new PrimitiveType("byte", byte.class);
  /**
   * The constant BYTE.
   */
  public static final PrimitiveType BYTE = new PrimitiveType("byte", Byte.class);
  /**
   * The constant SHORT_P.
   */
  public static final PrimitiveType SHORT_P = new PrimitiveType("short", short.class);
  /**
   * The constant SHORT.
   */
  public static final PrimitiveType SHORT = new PrimitiveType("short", Short.class);
  /**
   * The constant INT_P.
   */
  public static final PrimitiveType INT_P = new PrimitiveType("int", int.class);
  /**
   * The constant INT.
   */
  public static final PrimitiveType INT = new PrimitiveType("int", Integer.class);
  /**
   * The constant LONG_P.
   */
  public static final PrimitiveType LONG_P = new PrimitiveType("long", long.class);
  /**
   * The constant LONG.
   */
  public static final PrimitiveType LONG = new PrimitiveType("long", Long.class);
  /**
   * The constant BIG_INTEGER.
   */
  public static final PrimitiveType BIG_INTEGER = new PrimitiveType("big_integer", BigInteger.class);
  /**
   * The constant FLOAT_P.
   */
  public static final PrimitiveType FLOAT_P = new PrimitiveType("float", float.class);
  /**
   * The constant FLOAT.
   */
  public static final PrimitiveType FLOAT = new PrimitiveType("float", Float.class);
  /**
   * The constant DOUBLE_P.
   */
  public static final PrimitiveType DOUBLE_P = new PrimitiveType("double", double.class);
  /**
   * The constant DOUBLE.
   */
  public static final PrimitiveType DOUBLE = new PrimitiveType("double", Double.class);
  /**
   * The constant BIG_DECIMAL.
   */
  public static final PrimitiveType BIG_DECIMAL = new PrimitiveType("big_decimal", BigDecimal.class);
  /**
   * The constant BOOLEAN_P.
   */
  public static final PrimitiveType BOOLEAN_P = new PrimitiveType("boolean", boolean.class);
  /**
   * The constant BOOLEAN.
   */
  public static final PrimitiveType BOOLEAN = new PrimitiveType("boolean", Boolean.class);
  /**
   * The constant STRING.
   */
  public static final PrimitiveType STRING = new PrimitiveType("string", String.class);
  /**
   * The constant DATE.
   */
  public static final PrimitiveType DATE = new PrimitiveType("date", Date.class);
  /**
   * The constant TIME.
   */
  public static final PrimitiveType TIME = new PrimitiveType("time", Time.class);
  /**
   * The constant TIMESTAMP.
   */
  public static final PrimitiveType TIMESTAMP = new PrimitiveType("timestamp", Timestamp.class);
  /**
   * The constant OBJECT.
   */
  public static final PrimitiveType OBJECT = new PrimitiveType("object", Object.class);
  /**
   * The constant BINARY.
   */
  public static final PrimitiveType BINARY = new PrimitiveType("binary", byte[].class);
  /**
   * The constant ARRAY.
   */
  public static final PrimitiveType ARRAY = new PrimitiveType("array", Object[].class);

  private final String name;
  private final Class<?> javaType;

  /**
   * Instantiates a new Primitive type.
   *
   * @param name     the name
   * @param javaType the java type
   */
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

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets java type.
   *
   * @return the java type
   */
  public Class<?> getJavaType() {
    return javaType;
  }

  /**
   * Gets instance.
   *
   * @param name the name
   * @return the instance
   */
  public static PrimitiveType getInstance(String name) {
    return instancesByName.get(name);
  }

  /**
   * Find by java type primitive type.
   *
   * @param javaType the java type
   * @return the primitive type
   */
  public static PrimitiveType findByJavaType(Class<?> javaType) {
    return instancesByJavaType.get(javaType);
  }

  /**
   * Gets instances.
   *
   * @return the instances
   */
  public static Collection<PrimitiveType> getInstances() {
    return instancesByName.values();
  }

  @Override
  public String toString() {
    return name;
  }

}