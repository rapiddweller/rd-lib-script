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

package com.rapiddweller.script.expression;

import com.rapiddweller.script.Expression;

/**
 * New common interface for scanning through {@link Expression}s that wrap other expressions.<br/><br/>
 * Created: 08.06.2011 13:48:12
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 0.5.8
 */
public interface WrapperExpression<E> extends Expression<E> {
  Expression<?>[] getSourceExpressions();
}
