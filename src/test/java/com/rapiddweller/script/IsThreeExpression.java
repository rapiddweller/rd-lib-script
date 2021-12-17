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
import com.rapiddweller.common.Expression;

/**
 * Helper class for testing.<br/><br/>
 * Created: 08.03.2011 14:25:07
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
class IsThreeExpression implements Expression<Boolean> {

  @Override
  public Boolean evaluate(Context context) {
    Integer candidateValue = (Integer) context.get("_candidate");
    return (candidateValue != null && candidateValue == 3);
  }

  @Override
  public boolean isConstant() {
    return false;
  }

}
