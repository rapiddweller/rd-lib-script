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

/**
 * Parent class for defining arithmetics for special JDK or custom data types.<br/>
 * <br/>
 * Created at 06.10.2009 10:28:30
 *
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 0.6.0
 */
public abstract class TypeArithmetic<E> {

  /**
   * The Base type.
   */
  protected final Class<E> baseType;

  /**
   * Instantiates a new Type arithmetic.
   *
   * @param baseType the base type
   */
  public TypeArithmetic(Class<E> baseType) {
    this.baseType = baseType;
  }

  /**
   * Gets base type.
   *
   * @return the base type
   */
  public Class<E> getBaseType() {
    return baseType;
  }

  /**
   * Add e.
   *
   * @param summand1 the summand 1
   * @param summand2 the summand 2
   * @return the e
   * @throws IllegalArgumentException      the illegal argument exception
   * @throws UnsupportedOperationException the unsupported operation exception
   */
  public abstract E add(Object summand1, Object summand2) throws IllegalArgumentException, UnsupportedOperationException;

  /**
   * Subtract object.
   *
   * @param minuend    the minuend
   * @param subtrahend the subtrahend
   * @return the object
   * @throws IllegalArgumentException      the illegal argument exception
   * @throws UnsupportedOperationException the unsupported operation exception
   */
  public abstract Object subtract(Object minuend, Object subtrahend) throws IllegalArgumentException, UnsupportedOperationException;

  /**
   * Multiply object.
   *
   * @param factor1 the factor 1
   * @param factor2 the factor 2
   * @return the object
   * @throws IllegalArgumentException      the illegal argument exception
   * @throws UnsupportedOperationException the unsupported operation exception
   */
  public abstract Object multiply(Object factor1, Object factor2) throws IllegalArgumentException, UnsupportedOperationException;

  /**
   * Divide object.
   *
   * @param quotient the quotient
   * @param divisor  the divisor
   * @return the object
   * @throws IllegalArgumentException      the illegal argument exception
   * @throws UnsupportedOperationException the unsupported operation exception
   */
  public abstract Object divide(Object quotient, Object divisor) throws IllegalArgumentException, UnsupportedOperationException;

}
