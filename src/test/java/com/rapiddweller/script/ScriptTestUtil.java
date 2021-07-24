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
 * Helper class for testing.<br/><br/>
 * Created: 18.05.2011 16:18:57
 *
 * @author Volker Bergmann
 * @since 0.6.6
 */
public class ScriptTestUtil {

  /**
   * The constant pubvar.
   */
  public static String pubvar = "pubVarContent";

  /**
   * Say hello string.
   *
   * @param arg the arg
   * @return the string
   */
  public static String sayHello(String arg) {
    return "Hello " + arg;
  }

}
