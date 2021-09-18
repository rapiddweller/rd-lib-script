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
import com.rapiddweller.script.QNExpression;
import com.rapiddweller.script.ScriptContext;
import com.rapiddweller.script.ScriptTestUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link QNExpression}.<br/><br/>
 * Created: 18.05.2011 16:17:22
 * @author Volker Bergmann
 * @since 0.6.6
 */
public class QNExpressionTest {

  ScriptContext context;

  @Before
  public void setUpContext() {
    context = new DefaultScriptContext();
  }

  @Test
  public void testClass() {
    check(ScriptTestUtil.class, "com", "rapiddweller", "script", "ScriptTestUtil");
  }

  @Test
  public void testImportedClass() {
    context.importClass("com.rapiddweller.script.*");
    check(ScriptTestUtil.class, context, "ScriptTestUtil");
  }

  @Test
  public void testStaticField() {
    check("pubVarContent", "com", "rapiddweller", "script", "ScriptTestUtil", "pubvar");
  }

  @Test
  public void testStaticFieldOfImportedClass() {
    ScriptContext context = new DefaultScriptContext();
    context.importClass("com.rapiddweller.script.*");
    check("pubVarContent", context, "ScriptTestUtil", "pubvar");
  }

  @Test
  public void testVariablePath() {
    ScriptContext context = new DefaultScriptContext();
    A a = new A();
    context.set("a", a);
    assertSame(a.getB().getC(), new QNExpression(new String[] { "a", "b", "c" }).evaluate(context));
  }

  // helpers ---------------------------------------------------------------------------------------------------------

  private void check(Object expected, String... parts) {
    check(expected, context, parts);
  }

  private static void check(Object expected, ScriptContext context, String... parts) {
    assertEquals(expected, new QNExpression(parts).evaluate(context));
  }

  public static class A {
    B b;
    public A() {
      this.b = new B();
    }
    public B getB() {
      return b;
    }
  }

  public static class B {
    C c;
    public B() {
      this.c = new C();
    }
    public C getC() {
      return c;
    }
  }

  public static class C {
  }
}
