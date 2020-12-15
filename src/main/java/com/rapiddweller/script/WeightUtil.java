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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.rapiddweller.commons.comparator.ReverseComparator;

/**
 * Provides utility methods for handling weighted data.<br/><br/>
 * Created: 11.11.2011 17:04:17
 * @since 0.7.3
 * @author Volker Bergmann
 */
public class WeightUtil {

	public static <T> void sortByWeight(List<WeightedSample<T>> list, boolean ascending) {
		Comparator<WeightedSample<T>> comparator = new WeightedSampleComparator<T>();
		if (!ascending)
			comparator = new ReverseComparator<WeightedSample<T>>(comparator);
		Collections.sort(list, comparator);
	}
	
}
