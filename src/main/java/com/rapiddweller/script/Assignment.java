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

/**
 * Represents an assignment of an expression (result) to a target name.<br/><br/>
 * Created at 08.10.2009 19:06:08
 *
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class Assignment {

  private final String name;
  private final Expression<?> expression;

  /**
   * Instantiates a new Assignment.
   *
   * @param name       the name
   * @param expression the expression
   */
  public Assignment(String name, Expression<?> expression) {
    this.name = name;
    this.expression = expression;
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
   * Gets expression.
   *
   * @return the expression
   */
  public Expression<?> getExpression() {
    return expression;
  }

  @Override
  public String toString() {
    return name + "=" + expression;
  }

}
