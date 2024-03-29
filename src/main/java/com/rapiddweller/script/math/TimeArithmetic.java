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
import com.rapiddweller.common.TimeUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.sql.Time;
import java.util.Date;

/**
 * {@link TypeArithmetic} implementation for Time objects.<br/><br/>
 * Created at 06.10.2009 10:31:14
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class TimeArithmetic extends TypeArithmetic<Time> {

  public TimeArithmetic() {
    super(Time.class);
  }

  // Arithmetic interface implementation -----------------------------------------------------------------------------

  private static Time addImpl(Time summand1, Object summand2) {
    if (summand2 instanceof Number) {
      return new Time(summand1.getTime() + ((Number) summand2).longValue());
    } else if (summand2 instanceof Date) {
      return new Time(summand1.getTime() + TimeUtil.millisSinceOwnEpoch((Date) summand2));
    } else {
      throw ExceptionFactory.getInstance().illegalArgument("Cannot add " +
          BeanUtil.simpleClassName(summand2) + " to java.util.Date");
    }
  }

  @Override
  public Time add(Object summand1, Object summand2) {
    if (summand1 instanceof Time) {
      return addImpl((Time) summand1, summand2);
    } else if (summand2 instanceof Number) {
      return addImpl((Time) summand2, summand1);
    } else {
      throw ExceptionFactory.getInstance().illegalArgument("No argument is of type " + baseType + ": " +
          summand1 + ", " + summand2);
    }
  }

  @Override
  public Object subtract(Object minuend, Object subtrahend) {
    if (minuend instanceof Date) {
      long minuendMillis = ((Date) minuend).getTime();
      if (subtrahend instanceof Date) {
        return new Time(minuendMillis - TimeUtil.millisSinceOwnEpoch((Date) subtrahend));
      } else if (subtrahend instanceof Number) {
        return new Time(minuendMillis - ((Number) subtrahend).longValue());
      } else {
        throw ExceptionFactory.getInstance().illegalArgument("Subtrahend must be Date, Time, Timestamp or Number, but was: " +
            subtrahend.getClass().getName());
      }
    } else {
      throw ExceptionFactory.getInstance().illegalArgument("Minuend needs to be of type " + baseType + ", but was: " +
          minuend.getClass().getName());
    }
  }

  @Override
  public Object multiply(Object factor1, Object factor2) {
    throw new UnsupportedOperationException("Cannot multiply times");
  }

  // private methods -------------------------------------------------------------------------------------------------

  @Override
  public Object divide(Object quotient, Object divisor) {
    throw new UnsupportedOperationException("Cannot divide times");
  }

}
