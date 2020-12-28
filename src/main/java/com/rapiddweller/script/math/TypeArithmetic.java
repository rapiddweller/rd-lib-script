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
 * @since 0.6.0
 * @author Volker Bergmann
 */

public abstract class TypeArithmetic<E> {
	
	protected final Class<E> baseType;
	
    public TypeArithmetic(Class<E> baseType) {
	    this.baseType = baseType;
    }
    
	public Class<E> getBaseType() {
		return baseType;
	}
	
	public abstract E add(Object summand1, Object summand2) throws IllegalArgumentException, UnsupportedOperationException;

    public abstract Object subtract(Object minuend, Object subtrahend) throws IllegalArgumentException, UnsupportedOperationException;

    public abstract Object multiply(Object factor1, Object factor2) throws IllegalArgumentException, UnsupportedOperationException;

    public abstract Object divide(Object quotient, Object divisor) throws IllegalArgumentException, UnsupportedOperationException;

}
