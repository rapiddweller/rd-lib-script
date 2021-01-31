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

import com.rapiddweller.script.DefaultScriptContext;
import com.rapiddweller.script.Expression;
import com.rapiddweller.script.QNInvocationExpression;
import com.rapiddweller.script.ScriptContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link QNInvocationExpression}.<br/><br/>
 * Created: 18.05.2011 16:50:14
 * @since 0.6.6
 * @author Volker Bergmann
 */
public class QNInvocationExpressionTest {

	private ScriptContext context;
	
	@Before
	public void setup() {
		context = new DefaultScriptContext();
	}

	@Test
	public void testClass() {
		check("Hello Alice", "com.rapiddweller.script.ScriptTestUtil.sayHello", "Alice");
	}

	@Test
	public void testImportedClass() {
		context.importClass("com.rapiddweller.script.*");
		check("Hello Alice", "ScriptTestUtil.sayHello", "Alice");
	}

	private void check(Object expected, String qn, String arg) {
		QNInvocationExpression ex = new QNInvocationExpression(
				qn.split("\\."), new Expression<?>[] { ExpressionUtil.constant(arg) });
		Object actual = ex.evaluate(context);
		assertEquals(expected, actual);
	}

}
