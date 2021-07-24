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
 * Transition class that adds a weight property.<br/><br/>
 * Created: 24.10.2009 08:20:21
 *
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class WeightedTransition extends Transition {

  private final double weight;

  /**
   * Instantiates a new Weighted transition.
   *
   * @param from   the from
   * @param to     the to
   * @param weight the weight
   */
  public WeightedTransition(Object from, Object to, double weight) {
    super(from, to);
    this.weight = weight;
  }

  /**
   * Gets weight.
   *
   * @return the weight
   */
  public double getWeight() {
    return weight;
  }

}
