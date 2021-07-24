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

package com.rapiddweller.script.math;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.converter.AnyConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides arithmetic operations.<br/>
 * <br/>
 * Created at 06.10.2009 08:00:44
 *
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class ArithmeticEngine {

  // initialization --------------------------------------------------------------------------------------------------

  private static final Map<Class<?>, TypeArithmetic<?>> typeArithmetics;
  private static final ArithmeticEngine defaultInstance = new ArithmeticEngine();

  static {
    typeArithmetics = new HashMap<>();
    addTypeArithmetics(
        new DateArithmetic(),
        new TimeArithmetic(),
        new TimestampArithmetic());
  }

  // singleton stuff -------------------------------------------------------------------------------------------------

  private static void addTypeArithmetics(TypeArithmetic<?>... arithmetics) {
    for (TypeArithmetic<?> arithmetic : arithmetics) {
      typeArithmetics.put(arithmetic.getBaseType(), arithmetic);
    }
  }

  /**
   * Default instance arithmetic engine.
   *
   * @return the arithmetic engine
   */
  public static ArithmeticEngine defaultInstance() {
    return defaultInstance;
  }

  // interface -------------------------------------------------------------------------------------------------------

  /**
   * Add object.
   *
   * @param summand1 the summand 1
   * @param summand2 the summand 2
   * @return the object
   */
  public Object add(Object summand1, Object summand2) {
    // null conversion
    if (summand1 == null) {
      return summand2;
    } else if (summand2 == null) {
      return summand1;
    }
    // arbitrary conversion
    Class<?> resultType = TypeManager.combinedType(summand1.getClass(), summand2.getClass());
    TypeArithmetic<?> typeArithmetic = typeArithmetics.get(resultType);
    if (typeArithmetic != null) {
      return typeArithmetic.add(summand1, summand2);
    }
    Object s1 = AnyConverter.convert(summand1, resultType);
    Object s2 = AnyConverter.convert(summand2, resultType);
    if (resultType == String.class) {
      return (String) s1 + s2;
    } else if (resultType == Boolean.class) {
      return (Boolean) s1 || (Boolean) s2;
    } else if (resultType == Character.class) {
      return (Character) s1 + (Character) s2;
    } else if (resultType == Byte.class) {
      return (Byte) s1 + (Byte) s2;
    } else if (resultType == Short.class) {
      return (Short) s1 + (Short) s2;
    } else if (resultType == Integer.class) {
      return (Integer) s1 + (Integer) s2;
    } else if (resultType == Long.class) {
      return (Long) s1 + (Long) s2;
    } else if (resultType == Float.class) {
      return (Float) s1 + (Float) s2;
    } else if (resultType == Double.class) {
      return (Double) s1 + (Double) s2;
    } else if (resultType == BigInteger.class) {
      return ((BigInteger) s1).add((BigInteger) s2);
    } else if (resultType == BigDecimal.class) {
      return ((BigDecimal) s1).add((BigDecimal) s2);
    } else {
      throw new UnsupportedOperationException("Addition of types " + BeanUtil.simpleClassName(summand1) +
          " and " + BeanUtil.simpleClassName(summand2) + " is not supported");
    }
  }

  /**
   * Subtract object.
   *
   * @param minuend    the minuend
   * @param subtrahend the subtrahend
   * @return the object
   */
  public Object subtract(Object minuend, Object subtrahend) {
    // null conversion
    if (subtrahend == null) {
      return minuend;
    } else if (minuend == null) {
      return negate(subtrahend);
    }
    // arbitrary conversion
    Class<?> resultType = TypeManager.combinedType(minuend.getClass(), subtrahend.getClass());
    TypeArithmetic<?> typeArithmetic = typeArithmetics.get(resultType);
    if (typeArithmetic != null) {
      return typeArithmetic.subtract(minuend, subtrahend);
    }
    Object s1 = AnyConverter.convert(minuend, resultType);
    Object s2 = AnyConverter.convert(subtrahend, resultType);
    if (resultType == Character.class) {
      return (Character) s1 - (Character) s2;
    } else if (resultType == Byte.class) {
      return (Byte) s1 - (Byte) s2;
    } else if (resultType == Short.class) {
      return (Short) s1 - (Short) s2;
    } else if (resultType == Integer.class) {
      return (Integer) s1 - (Integer) s2;
    } else if (resultType == Long.class) {
      return (Long) s1 - (Long) s2;
    } else if (resultType == Float.class) {
      return (Float) s1 - (Float) s2;
    } else if (resultType == Double.class) {
      return (Double) s1 - (Double) s2;
    } else if (resultType == BigInteger.class) {
      return ((BigInteger) s1).subtract((BigInteger) s2);
    } else if (resultType == BigDecimal.class) {
      return ((BigDecimal) s1).subtract((BigDecimal) s2);
    } else {
      throw new UnsupportedOperationException("Subtraction of type " + BeanUtil.simpleClassName(subtrahend) +
          " from " + BeanUtil.simpleClassName(minuend) + " is not supported");
    }
  }

  /**
   * Negate object.
   *
   * @param value the value
   * @return the object
   */
  public Object negate(Object value) {
    // null handling
    if (value == null) {
      return null;
    }
    // arbitrary conversion
    Class<?> type = value.getClass();
    if (type == Byte.class) {
      return -(Byte) value;
    } else if (type == Short.class) {
      return -(Short) value;
    } else if (type == Integer.class) {
      return -(Integer) value;
    } else if (type == Long.class) {
      return -(Long) value;
    } else if (type == Float.class) {
      return -(Float) value;
    } else if (type == Double.class) {
      return -(Double) value;
    } else if (type == BigInteger.class) {
      return ((BigInteger) value).negate();
    } else if (type == BigDecimal.class) {
      return ((BigDecimal) value).negate();
    } else {
      throw new UnsupportedOperationException("Cannot negate " + BeanUtil.simpleClassName(value));
    }
  }

  /**
   * Multiply object.
   *
   * @param factor1 the factor 1
   * @param factor2 the factor 2
   * @return the object
   */
  public Object multiply(Object factor1, Object factor2) {
    // null handling
    if (factor1 == null || factor2 == null) {
      return null;
    }
    // arbitrary conversion
    Class<?> resultType = TypeManager.combinedType(factor1.getClass(), factor2.getClass());
    TypeArithmetic<?> typeArithmetic = typeArithmetics.get(resultType);
    if (typeArithmetic != null) {
      return typeArithmetic.multiply(factor1, factor2);
    }
    Object s1 = AnyConverter.convert(factor1, resultType);
    Object s2 = AnyConverter.convert(factor2, resultType);
    if (resultType == Byte.class) {
      return (Byte) s1 * (Byte) s2;
    } else if (resultType == Short.class) {
      return (Short) s1 * (Short) s2;
    } else if (resultType == Integer.class) {
      return (Integer) s1 * (Integer) s2;
    } else if (resultType == Long.class) {
      return (Long) s1 * (Long) s2;
    } else if (resultType == Float.class) {
      return (Float) s1 * (Float) s2;
    } else if (resultType == Double.class) {
      return (Double) s1 * (Double) s2;
    } else if (resultType == BigInteger.class) {
      return ((BigInteger) s1).multiply((BigInteger) s2);
    } else if (resultType == BigDecimal.class) {
      return ((BigDecimal) s1).multiply((BigDecimal) s2);
    } else {
      throw new UnsupportedOperationException("Multiplication of type " + BeanUtil.simpleClassName(factor1) +
          " with " + BeanUtil.simpleClassName(factor2) + " is not supported");
    }
  }

  /**
   * Divide object.
   *
   * @param dividend the dividend
   * @param divisor  the divisor
   * @return the object
   */
  public Object divide(Object dividend, Object divisor) {
    // null handling
    if (divisor == null) {
      throw new IllegalArgumentException("Division by null");
    }
    if (dividend == null) {
      return null;
    }
    // arbitrary conversion
    Class<?> resultType = TypeManager.combinedType(dividend.getClass(), divisor.getClass());
    TypeArithmetic<?> typeArithmetic = typeArithmetics.get(resultType);
    if (typeArithmetic != null) {
      return typeArithmetic.multiply(dividend, divisor);
    }
    Object s1 = AnyConverter.convert(dividend, resultType);
    Object s2 = AnyConverter.convert(divisor, resultType);
    if (resultType == Byte.class) {
      return (Byte) s1 / (Byte) s2;
    } else if (resultType == Short.class) {
      return (Short) s1 / (Short) s2;
    } else if (resultType == Integer.class) {
      return (Integer) s1 / (Integer) s2;
    } else if (resultType == Long.class) {
      return (Long) s1 / (Long) s2;
    } else if (resultType == Float.class) {
      return (Float) s1 / (Float) s2;
    } else if (resultType == Double.class) {
      return (Double) s1 / (Double) s2;
    } else if (resultType == BigInteger.class) {
      return ((BigInteger) s1).divide((BigInteger) s2);
    } else if (resultType == BigDecimal.class) {
      return ((BigDecimal) s1).divide((BigDecimal) s2);
    } else {
      throw new UnsupportedOperationException(
          "Multiplication of type " +
              BeanUtil.simpleClassName(dividend) + " (" + dividend + ") with " +
              BeanUtil.simpleClassName(divisor) + " (" + divisor + ") is not supported");
    }
  }

  /**
   * Less boolean.
   *
   * @param part1 the part 1
   * @param part2 the part 2
   * @return the boolean
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public boolean less(Object part1, Object part2) {
    // null handling
    if (part2 == null || part1 == null) {
      throw new IllegalArgumentException("Cannot compare null");
    }
    Class<?> resultType = TypeManager.combinedType(part1.getClass(), part2.getClass());
    // convert the terms to the same type and compare them
    Object s1 = AnyConverter.convert(part1, resultType);
    Object s2 = AnyConverter.convert(part2, resultType);
    if (Comparable.class.isAssignableFrom(resultType)) {
      return (((Comparable) s1).compareTo(s2) < 0);
    } else {
      throw new UnsupportedOperationException("Cannot compare type " +
          BeanUtil.simpleClassName(part1) + " with " + BeanUtil.simpleClassName(part2));
    }
  }

  /**
   * Less or equals boolean.
   *
   * @param part1 the part 1
   * @param part2 the part 2
   * @return the boolean
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public boolean lessOrEquals(Object part1, Object part2) {
    // null handling
    if (part2 == null || part1 == null) {
      throw new IllegalArgumentException("Cannot compare null");
    }
    Class<?> resultType = TypeManager.combinedType(part1.getClass(), part2.getClass());
    // convert the terms to the same type and compare them
    Object s1 = AnyConverter.convert(part1, resultType);
    Object s2 = AnyConverter.convert(part2, resultType);
    if (Comparable.class.isAssignableFrom(resultType)) {
      return (((Comparable) s1).compareTo(s2) <= 0);
    } else {
      throw new UnsupportedOperationException("Cannot compare type " +
          BeanUtil.simpleClassName(part1) + " with " + BeanUtil.simpleClassName(part2));
    }
  }

  /**
   * Equals boolean.
   *
   * @param part1 the part 1
   * @param part2 the part 2
   * @return the boolean
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public boolean equals(Object part1, Object part2) {
    // null handling
    if (part2 == null && part1 == null) {
      return true;
    }
    if (part2 == null || part1 == null) {
      return false;
    }
    // comparing non-null values
    Class<?> resultType = TypeManager.combinedType(part1.getClass(), part2.getClass());
    // convert the terms to the same type and compare them
    Object s1 = AnyConverter.convert(part1, resultType);
    Object s2 = AnyConverter.convert(part2, resultType);
    if (Comparable.class.isAssignableFrom(resultType)) {
      return (((Comparable) s1).compareTo(s2) == 0);
    } else {
      throw new UnsupportedOperationException("Cannot compare type " +
          BeanUtil.simpleClassName(part1) + " with " + BeanUtil.simpleClassName(part2));
    }
  }

  /**
   * Greater boolean.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the boolean
   */
  public Boolean greater(Object o1, Object o2) {
    return less(o2, o1);
  }

  /**
   * Greater or equals boolean.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the boolean
   */
  public Boolean greaterOrEquals(Object o1, Object o2) {
    return !less(o1, o2);
  }

  /**
   * Bitwise and object.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the object
   */
  public Object bitwiseAnd(Object o1, Object o2) {
    Class<?> resultType = TypeManager.combinedType(o1.getClass(), o2.getClass());
    if (resultType == Boolean.class) {
      return (Boolean) o1 & (Boolean) o2;
    }
    if (!(o1 instanceof Number)) {
      throw new IllegalArgumentException("Not a number or boolean: " + o1);
    }
    if (!(o2 instanceof Number)) {
      throw new IllegalArgumentException("Not a number or boolean: " + o2);
    }
    Number n1 = (Number) o1;
    Number n2 = (Number) o2;
    if (resultType == Integer.class) {
      return n1.intValue() & n2.intValue();
    } else if (resultType == Long.class) {
      return n1.longValue() & n2.longValue();
    } else if (resultType == Short.class) {
      return n1.shortValue() & n2.shortValue();
    } else {
      throw new IllegalStateException("Illegal state for " + resultType.getName());
    }
  }

  /**
   * Bitwise or object.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the object
   */
  public Object bitwiseOr(Object o1, Object o2) {
    Class<?> resultType = TypeManager.combinedType(o1.getClass(), o2.getClass());
    if (resultType == Boolean.class) {
      return (Boolean) o1 | (Boolean) o2;
    }
    if (!(o1 instanceof Number)) {
      throw new IllegalArgumentException("Not a number or boolean: " + o1);
    }
    if (!(o2 instanceof Number)) {
      throw new IllegalArgumentException("Not a number or boolean: " + o2);
    }
    Number n1 = (Number) o1;
    Number n2 = (Number) o2;
    if (resultType == Integer.class) {
      return n1.intValue() | n2.intValue();
    } else if (resultType == Long.class) {
      return n1.longValue() | n2.longValue();
    } else if (resultType == Short.class) {
      return n1.shortValue() | n2.shortValue();
    } else {
      throw new IllegalStateException("Illegal state for " + resultType.getName());
    }
  }

  /**
   * Bitwise exclusive or object.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the object
   */
  public Object bitwiseExclusiveOr(Object o1, Object o2) {
    Class<?> resultType = TypeManager.combinedType(o1.getClass(), o2.getClass());
    if (resultType == Boolean.class) {
      return (Boolean) o1 ^ (Boolean) o2;
    }
    if (!(o1 instanceof Number)) {
      throw new IllegalArgumentException("Not a number or boolean: " + o1);
    }
    if (!(o2 instanceof Number)) {
      throw new IllegalArgumentException("Not a number or boolean: " + o2);
    }
    Number n1 = (Number) o1;
    Number n2 = (Number) o2;
    if (resultType == Integer.class) {
      return n1.intValue() ^ n2.intValue();
    } else if (resultType == Long.class) {
      return n1.longValue() ^ n2.longValue();
    } else if (resultType == Short.class) {
      return n1.shortValue() ^ n2.shortValue();
    } else {
      throw new IllegalStateException("Illegal state for " + resultType.getName());
    }
  }

  /**
   * Shift left object.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the object
   */
  public Object shiftLeft(Object o1, Object o2) {
    if (!(o1 instanceof Number)) {
      throw new IllegalArgumentException("Not a number: " + o1);
    }
    if (!(o2 instanceof Number)) {
      throw new IllegalArgumentException("Not a number: " + o2);
    }
    Number n1 = (Number) o1;
    Number n2 = (Number) o2;
    if (n1 instanceof Integer) {
      return n1.intValue() << n2.intValue();
    } else if (n1 instanceof Long) {
      return n1.longValue() << n2.intValue();
    } else if (n1 instanceof Short) {
      return n1.shortValue() << n2.intValue();
    } else if (n1 instanceof Byte) {
      return n1.byteValue() << n2.intValue();
    } else {
      throw new IllegalArgumentException("Cannot shift " + BeanUtil.simpleClassName(o1));
    }
  }

  /**
   * Shift right object.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the object
   */
  public Object shiftRight(Object o1, Object o2) {
    if (!(o1 instanceof Number)) {
      throw new IllegalArgumentException("Not a number: " + o1);
    }
    if (!(o2 instanceof Number)) {
      throw new IllegalArgumentException("Not a number: " + o2);
    }
    Number n1 = (Number) o1;
    Number n2 = (Number) o2;
    if (n1 instanceof Integer) {
      return n1.intValue() >> n2.intValue();
    } else if (n1 instanceof Long) {
      return n1.longValue() >> n2.intValue();
    } else if (n1 instanceof Short) {
      return n1.shortValue() >> n2.intValue();
    } else if (n1 instanceof Byte) {
      return n1.byteValue() >> n2.intValue();
    } else {
      throw new IllegalArgumentException("Cannot shift " + BeanUtil.simpleClassName(o1));
    }
  }

  /**
   * Shift right unsigned object.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the object
   */
  public Object shiftRightUnsigned(Object o1, Object o2) {
    if (!(o1 instanceof Number)) {
      throw new IllegalArgumentException("Not a number: " + o1);
    }
    if (!(o2 instanceof Number)) {
      throw new IllegalArgumentException("Not a number: " + o2);
    }
    Number n1 = (Number) o1;
    Number n2 = (Number) o2;
    if (n1 instanceof Integer) {
      return n1.intValue() >>> n2.intValue();
    } else if (n1 instanceof Long) {
      return n1.longValue() >>> n2.intValue();
    } else if (n1 instanceof Short) {
      return n1.shortValue() >>> n2.intValue();
    } else if (n1 instanceof Byte) {
      return n1.byteValue() >>> n2.intValue();
    } else {
      throw new IllegalArgumentException("Cannot shift " + BeanUtil.simpleClassName(o1));
    }
  }

  /**
   * Mod object.
   *
   * @param o1 the o 1
   * @param o2 the o 2
   * @return the object
   */
  public Object mod(Object o1, Object o2) {
    if (!(o1 instanceof Number)) {
      throw new IllegalArgumentException("Not a number: " + o1);
    }
    if (!(o2 instanceof Number)) {
      throw new IllegalArgumentException("Not a number: " + o2);
    }
    Number n1 = (Number) o1;
    Number n2 = (Number) o2;
    if (n1 instanceof Integer) {
      return n1.intValue() % n2.intValue();
    } else if (n1 instanceof Long) {
      return n1.longValue() % n2.intValue();
    } else if (n1 instanceof Short) {
      return n1.shortValue() % n2.intValue();
    } else if (n1 instanceof Byte) {
      return n1.byteValue() % n2.intValue();
    } else if (n1 instanceof BigInteger) {
      return ((BigInteger) n1).mod((BigInteger) n2);
    } else {
      throw new IllegalArgumentException("Cannot calculate the modulo of " + BeanUtil.simpleClassName(o1));
    }
  }

  /**
   * Logical complement object.
   *
   * @param value the value
   * @return the object
   */
  public Object logicalComplement(Object value) {
    if (!(value instanceof Boolean)) {
      throw new IllegalArgumentException("Not a boolean: " + value);
    }
    return !(Boolean) value;
  }

  /**
   * Bitwise complement object.
   *
   * @param value the value
   * @return the object
   */
  public Object bitwiseComplement(Object value) {
    if (!(value instanceof Number)) {
      throw new IllegalArgumentException("Not a number: " + value);
    }
    Number number = (Number) value;
    if (number instanceof Integer) {
      return ~number.intValue();
    } else if (number instanceof Long) {
      return ~number.longValue();
    } else if (number instanceof Short) {
      return ~number.shortValue();
    } else if (number instanceof Byte) {
      return ~number.byteValue();
    } else {
      throw new IllegalArgumentException("Cannot complement " + BeanUtil.simpleClassName(value));
    }
  }

}
