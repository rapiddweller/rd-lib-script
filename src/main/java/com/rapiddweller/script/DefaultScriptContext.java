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

import com.rapiddweller.common.bean.ClassCache;
import com.rapiddweller.common.context.DefaultContext;

/**
 * Default implementation of the {@link ScriptContext} interface.<br/><br/>
 * Created: 01.11.2011 10:51:50
 * @since 0.7.3
 * @author Volker Bergmann
 */
public class DefaultScriptContext extends DefaultContext implements ScriptContext {

	private final ClassCache classCache;

	public DefaultScriptContext() {
		this.classCache = new ClassCache();
	}
	
	@Override
	public Class<?> forName(String className) {
		return classCache.forName(className);
	}
	
	@Override
	public void importClass(String className) {
		classCache.importClass(className);
	}

	public void importPackage(String packageName) {
		classCache.importPackage(packageName);
	}

	@Override
	public void close() {
		// nothing to do
	}

}
