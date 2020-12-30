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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.rapiddweller.common.comparator.TypeComparator;

/**
 * Provides information how types can be combined in arithmetic operations.<br/>
 * <br/>
 * Created at 06.10.2009 09:49:53
 * @since 0.6.0
 * @author Volker Bergmann
 */

public class TypeManager {

	private static final TypeComparator comparator = new TypeComparator(
			boolean.class, Boolean.class, 
			char.class, Character.class,
			byte.class, Byte.class,
			short.class, Short.class,
			int.class, Integer.class,
			long.class, Long.class,
			BigInteger.class,
			float.class, Float.class,
			double.class, Double.class,
			BigDecimal.class,
			Time.class,
			Date.class,
			Timestamp.class,
			String.class
		);
	
	public static Class<?> combinedType(Class<?> type1, Class<?> type2) {
		return (comparator.compare(type1, type2) > 0 ? type1 : type2);
	}

}
