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

import com.rapiddweller.common.comparator.DoubleComparator;

import java.util.Comparator;

/**
 * {@link Comparator} which compares {@link WeightedSample} objects by their 'weight' property value.<br/><br/>
 * Created: 11.11.2011 17:01:10
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 0.7.3
 */
public class WeightedSampleComparator<E> implements Comparator<WeightedSample<E>> {

  @Override
  public int compare(WeightedSample<E> s1, WeightedSample<E> s2) {
    return DoubleComparator.compare(s1.getWeight(), s2.getWeight());
  }

}
