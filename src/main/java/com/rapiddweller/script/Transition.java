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

import com.rapiddweller.commons.NullSafeComparator;

/**
 * Represents the transition of one state to another.<br/>
 * <br/>
 * Created at 17.07.2009 08:05:07
 * @since 0.6.0
 * @author Volker Bergmann
 */

public class Transition {

	private Object from;
	private Object to;
	
	public Transition(Object from, Object to) {
	    this.from = from;
	    this.to = to;
    }

	public Object getFrom() {
    	return from;
    }

	public Object getTo() {
    	return to;
    }

	@Override
    public int hashCode() {
	    return from.hashCode() * 31 + to.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null || getClass() != obj.getClass())
		    return false;
	    Transition that = (Transition) obj;
	    return NullSafeComparator.equals(this.from, that.from) 
	    	&& NullSafeComparator.equals(this.to, that.to);
    }
	
	@Override
	public String toString() {
	    return from + "->" + to;
	}

}
