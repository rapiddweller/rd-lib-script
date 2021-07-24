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

import com.rapiddweller.common.Context;
import com.rapiddweller.common.bean.ClassProvider;

import java.io.Closeable;

/**
 * Base interface for Databene Script actors to interoperate with their environment.<br/><br/>
 * Created: 01.11.2011 10:43:49
 *
 * @author Volker Bergmann
 * @since 0.7.3
 */
public interface ScriptContext extends Context, ClassProvider, Closeable {
  /**
   * Import class.
   *
   * @param className the class name
   */
  void importClass(String className);

  @Override
  void close();
}
