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
 * A bean specification which can declare if it wraps a 'value' or just represents a 'reference'.
 * This is used for managing scopes with 'local' objects and references to 'global' ones.<br/><br/>
 * Created: 13.04.2011 19:07:09
 * @since 0.6.6
 * @author Volker Bergmann
 */
public class BeanSpec {
	
	private Object bean;
	private boolean reference;

	public BeanSpec(Object bean, boolean reference) {
		this.bean = bean;
		this.reference = reference;
	}

	public Object getBean() {
		return bean;
	}
	
	public boolean isReference() {
		return reference;
	}

	public static BeanSpec createReference(Object bean) {
		return new BeanSpec(bean, true);
	}

	public static BeanSpec createConstruction(Object bean) {
		return new BeanSpec(bean, false);
	}
	
	@Override
	public String toString() {
		return (reference ? "reference to " : "creation of ") + bean;
	}
	
}
