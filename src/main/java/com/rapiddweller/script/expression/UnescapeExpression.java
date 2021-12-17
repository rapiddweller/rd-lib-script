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

import com.rapiddweller.common.Context;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.Expression;

/**
 * {@link Expression} proxy which unescapes the output of its source.<br/><br/>
 * Created: 11.04.2011 14:12:16
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class UnescapeExpression extends ExpressionProxy<String> {

  public UnescapeExpression(Expression<String> source) {
    super(source);
  }

  @Override
  public String evaluate(Context context) {
    return StringUtil.unescape(super.evaluate(context));
  }

}