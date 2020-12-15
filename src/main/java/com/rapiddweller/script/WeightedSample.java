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

import com.rapiddweller.commons.Weighted;

/**
 * Represents a single sample value for a generator.
 * The sample value may have an additional weight information.<br/>
 * <br/>
 * Created: 07.06.2006 19:05:13
 * @since 0.1
 * @author Volker Bergmann
 */
public class WeightedSample<E> implements Weighted {

    /** The value of the sample */
    private E value;

    /** The optional weight of the sample */
    private double weight;

    /** Initializes the Sample to the specified value and weight */
    public WeightedSample(E value, double weight) {
        this.weight = weight;
        this.value = value;
    }

    // properties ------------------------------------------------------------------------------------------------------

    /**
     * Returns the value property
     * @see #weight
     */
    public E getValue() {
        return value;
    }

    /**
     * Sets the weight property
     * @see #weight
     */
    public void setValue(E value) {
        this.value = value;
    }

    /**
     * Returns the weight property value
     * @see #weight
     */
    @Override
	public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight property value
     * @see #weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

	public void addWeight(double weightDelta) {
		this.weight += weightDelta;
	}
	
    @Override
    public String toString() {
        return value + "(" + weight + ')';
    }

}
