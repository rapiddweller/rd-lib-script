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

/**
 * Represents an Expression that evaluates some internal state and returns the result.<br/><br/>
 * Created: 18.06.2007 17:00:22
 * @param <E> the result type of {@link #evaluate(Context)}
 * @author Volker Bergmann
 * @since 0.2
 */
public interface Expression<E> {

  /** Evaluates the expression using the context and returns the resulting value. */
  E evaluate(Context context);

  /** Tells if the expression will return the same value on each invocation. */
  boolean isConstant();

}
