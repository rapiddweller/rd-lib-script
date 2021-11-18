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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.ObjectNotFoundException;
import com.rapiddweller.common.exception.SyntaxError;
import com.rapiddweller.common.TimeUtil;
import com.rapiddweller.common.context.DefaultContext;
import com.rapiddweller.common.converter.ConverterManager;
import com.rapiddweller.script.expression.ExpressionUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Tests the {@link DatabeneScriptParser}.<br/><br/>
 * Created at 05.10.2009 19:02:05
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class DatabeneScriptParserTest {

  private ScriptContext context;

  @Before
  public void setUpContext() {
    this.context = new DefaultScriptContext();
  }

  @Before
  public void setup() {
    ConverterManager.getInstance().reset();
  }

  @Test
  public void testNullLiteral() {
    checkExpression(null, "null");
  }

  @Test
  public void testBooleanLiteral() {
    checkExpression(true, "true");
    checkExpression(false, "false");
  }

  @Test
  public void testIntLiteral() {
    checkExpression(1, "1");
    checkExpression(0, "0");
    checkExpression(1000000000, "1000000000");
    checkExpression(Integer.MAX_VALUE, String.valueOf(Integer.MAX_VALUE));
  }

  @Test
  public void testLongLiteral() {
    checkExpression(123456789012345L, "123456789012345");
    long border = Integer.MAX_VALUE + 1L;
    checkExpression(border, String.valueOf(border));
  }

  @Test
  public void testDoubleLiteral() {
    checkExpression(0., "0.0");
    checkExpression(1.5, "1.5");
    checkExpression(100., "1E+2");
    checkExpression(125., "1.25E+2");
  }

  @Test
  public void testStringLiteral() {
    checkExpression("Test", "'Test'");
    checkExpression("'Test'", "'\\'Test\\''");
    checkExpression("\r\n", "'\\r\\n'");
    checkExpression("col='value'", "'col=\\'value\\''");
    checkExpression("", "''");
  }

  @Test
  public void testConstructor() {
    checkExpression("", "new java.lang.String()");
    checkExpression("Test", "new java.lang.String('Test')");
    checkExpression("Test", "new java.lang.String(new java.lang.String('Test'))");
    checkExpression("\r\n", "new java.lang.String('\\r\\n')");
  }

  @Test
  public void testEnum() {
    checkExpression(ScriptTestEnum.ALPHA, "com.rapiddweller.script.ScriptTestEnum.ALPHA");
  }

  @Test
  public void testStaticInvocation() {
    checkExpression("it works!", getClass().getName() + ".exclamate('it works')");
  }

  @Test
  public void testVarargsInvocation() {
    checkExpression("ABC", getClass().getName() + ".varargs1('A', 'B', 'C')");
    checkExpression("BC", getClass().getName() + ".varargs2('A', 'B', 'C')");
  }

  @Test
  public void testReference() {
    Context context = new DefaultContext();
    context.set("testString", "Hello");
    checkExpression("Hello", "testString", context);
  }

  @Test
  public void testReferenceInvocation() {
    Context context = new DefaultContext();
    context.set("testString", "Hello");
    checkExpression(5, "testString.length()", context);
  }

  @Test
  public void testObjectInvocation() {
    checkExpression(3, "'123'.length()");
  }

  @Test
  public void testArrayIndex() {
    Context context = new DefaultContext();
    context.set("testArray", new String[] {"Alice", "Bob", "Charly"});
    checkExpression("Bob", "testArray[1]", context);
  }

  @Test
  public void testListIndex() {
    Context context = new DefaultContext();
    context.set("testList", CollectionUtil.toList("Alice", "Bob", "Charly"));
    checkExpression("Bob", "testList[1]", context);
  }

  @Test
  public void testMapIndex() {
    Context context = new DefaultContext();
    context.set("testMap", CollectionUtil.buildMap("Alice", 23, "Bob", 34, "Charly", 45));
    checkExpression(34, "testMap['Bob']", context);
  }

  @Test
  public void testStringIndex() {
    checkExpression('e', "'Hello'[1]");
  }

  @Test
  public void testStaticCall() {
    checkExpression(2., "Math.sqrt(4)");
  }

  @Test
  public void testSubCall() {
    checkExpression('l', "'Hello'.substring(1,3).charAt(1)");
  }

  @Test
  public void testStaticSubField() {
    checkExpression("hi!!", getClass().getName() + ".staticStringAttrib");
  }

  @Test
  public void testSubField() {
    Context context = new DefaultContext();
    context.set("tc", this);
    checkExpression("hi!", "tc.stringAttrib", context);
    checkExpression("hi", "tc.pubField.text", context);
    checkExpression("hi", "new " + getClass().getName() + "().pubField.text");
  }

  @Test
  public void testSubFieldMethod() {
    Context context = new DefaultContext();
    context.set("tc", this);
    checkExpression("hi!", "tc.stringAttrib.trim()", context);
    checkExpression("hi", "tc.stringAttrib.substring(0, 2)", context);
    checkExpression("hi!", "tc.stringAttrib.trim().trim()", context);
  }

  @Test
  public void testCast() {
    checkExpression(1L, "100000000002 - 100000000001");
    checkExpression(1, "(int) (100000000002 - 100000000001)");
    checkExpression(TimeUtil.date(2009, 9, 6), "(date) '2009-10-06'");
    checkExpression(TimeUtil.date(2009, 9, 6), "(java.util.Date) '2009-10-06'");
    checkExpression("1", "(java.lang.String) 1");
    checkExpression(1, "(java.lang.Integer) 1");
    checkExpression(1, "(int) 1");
    checkExpression(TimeUtil.time(18, 19, 20), "(time) '18:19:20'");
  }

  @Test
  public void testNegation() {
    checkExpression(-1, "-1");
    checkExpression(-3, "- (1 + 2)");
  }

  @Test
  public void testBitwiseComplement() {
    checkExpression(-2, "~1");
    checkExpression(-4, "~ (1 + 2)");
  }

  @Test
  public void testLogicalComplement() {
    checkExpression(false, "! true");
    checkExpression(true, "! (1 + 2 < 2)");
  }

  @Test
  public void testMultipication() {
    checkExpression(35, "7 * 5");
    checkExpression(36, "2 + 7 * 5 - 1");
    checkExpression(4.5, "1.5 * 3");
  }

  @Test
  public void testDivision() {
    checkExpression(2, "6 / 3");
    checkExpression(2., "6.0 / 3.0");
    checkExpression(3, "7 / 2");
    checkExpression(3.5, "7.0 / 2.0");
  }

  @Test
  public void testModulo() {
    checkExpression(2, "11 % 3");
    checkExpression(0, "3 % 3");
    checkExpression(0, "0 % 2");
    checkExpression(0, "10 % 1");
  }

  @Test
  public void testStringSum() {
    checkExpression("", "'' + ''");
    checkExpression("", "'' + null");
    checkExpression("Test123", "'Test' + '123'");
    checkExpression("Test123", "'Test' + 123");
    checkExpression("123Test", "123 + 'Test'");
    checkExpression("Test123true", "'Test' + 123 + true");
    checkExpression("implemented at 2009-10-08T00:00:00", "'implemented at ' + (date) '2009-10-08'");
  }

  @Test
  public void testNumberSum() {
    checkExpression(0, "0 + 0");
    checkExpression(2, "1 + 1");
    checkExpression(100000000001L, "100000000000 + 1");
    checkExpression(5, "(byte) 3 + (byte) 2");
    checkExpression(5, "3 + (byte) 2");
    checkExpression(1.5, "1 + 0.5");
    checkExpression((float) 1.5, "1 + (float) 0.5");
    checkExpression((float) 1.5, "(float) (1 + 0.5)");
    checkExpression(1, "(int) (1 + 0.5)");
  }

  @Test
  public void testDateSum() {
    checkExpression(TimeUtil.date(1970, 0, 1), "(date) '1970-01-01' + 0");
    checkExpression(TimeUtil.date(1970, 0, 2), "(date) '1970-01-01' + (long) 1000 * 3600 * 24");
    checkExpression(TimeUtil.date(1970, 0, 2), "(long) 1000 * 3600 * 24 + (date) '1970-01-01'");
  }

  @Test
  public void testDateTimeSum() {
    checkExpression(TimeUtil.date(1970, 0, 1, 18, 19, 20, 0), "(date) '1970-01-01' + (time) '18:19:20'");
    checkExpression(TimeUtil.date(1970, 0, 1, 18, 19, 20, 0), "(time) '18:19:20' + (date) '1970-01-01'");
    checkExpression(TimeUtil.date(2009, 9, 8, 18, 19, 20, 0), "(date) '2009-10-08' + (time) '18:19:20'");
    checkExpression(TimeUtil.date(2009, 9, 8, 18, 19, 20, 0), "(time) '18:19:20' + (date) '2009-10-08'");
  }

  @Test
  public void testTimestampSum() {
    checkExpression(TimeUtil.timestamp(1970, 0, 1, 0, 0, 0, 0), "(timestamp) '1970-01-01' + 0");
    checkExpression(TimeUtil.timestamp(1970, 0, 2, 0, 0, 0, 0), "(timestamp) '1970-01-01' + (long) 1000 * 3600 * 24");
  }

  @Test
  public void testDateDifference() {
    checkExpression(TimeUtil.date(1970, 0, 1), "(date) '1970-01-02' - (long) 1000 * 3600 * 24");
  }

  @Test
  public void testTimestampDifference() {
    checkExpression(TimeUtil.timestamp(1970, 0, 1, 0, 0, 0, 0), "(timestamp) '1970-01-02' - (long) 1000 * 3600 * 24");
  }

  @Test
  public void testParenthesis() {
    checkExpression(1, "6 - 3 - 2");
    checkExpression(1, "(6 - 3) - 2");
    checkExpression(5, "6 - (3 - 2)");
  }

  @Test
  public void testLeftShift() {
    checkExpression(4, " 1 << 2");
    checkExpression(-32, "-4 << 3");
  }

  @Test
  public void testRightShift() {
    checkExpression(1, "   2  >> 1");
    checkExpression(4, "  32  >> 3");
    checkExpression(-4, "(-32) >> 3");
  }

  @Test
  public void testRightShift2() {
    checkExpression(4, "32 >>> 3");
  }

  @Test
  public void testEquals() {
    checkExpression(false, "2 == 1");
    checkExpression(true, "2 == 2");
  }

  @Test
  public void testNotEquals() {
    checkExpression(false, "2 != 2");
    checkExpression(true, "2 != 1");
  }

  @Test
  public void testLessOrEqual() {
    checkExpression(false, "2 <= 1");
    checkExpression(true, "2 <= 2");
    checkExpression(true, "2 <= 3");
  }

  @Test
  public void testGreaterOrEqual() {
    checkExpression(true, "2 >= 1");
    checkExpression(true, "2 >= 2");
    checkExpression(false, "2 >= 3");
  }

  @Test
  public void testLess() {
    checkExpression(false, "2 < 1");
    checkExpression(false, "2 < 2");
    checkExpression(true, "2 < 3");
  }

  @Test
  public void testGreater() {
    checkExpression(true, "2 > 1");
    checkExpression(false, "2 > 2");
    checkExpression(false, "2 > 3");
  }

  @Test
  public void testAnd() {
    checkExpression(1, "1 & 1");
    checkExpression(0, "1 & 2");
    checkExpression(1, "1 & 1 & 1");
  }

  @Test
  public void testExclusiveOr() {
    checkExpression(0, "1 ^ 1");
    checkExpression(3, "1 ^ 2");
  }

  @Test
  public void testInclusiveOr() {
    checkExpression(1, "1 | 1");
    checkExpression(3, "1 | 2");
  }

  @Test
  public void testConditionalAnd() {
    checkExpression(false, "false && false");
    checkExpression(false, "true  && false");
    checkExpression(false, "false && true");
    checkExpression(true, "true  && true");
  }

  @Test
  public void testConditionalOr() {
    checkExpression(false, "false || false");
    checkExpression(true, "true  || false");
    checkExpression(true, "false || true");
    checkExpression(true, "true  || true");
  }

  @Test
  public void testConditionalExpression() {
    checkExpression(1, "true ? 1 : 2");
    checkExpression(2, "false ? 1 : 2");
    checkExpression(2, "(false ? 1 : 2)");
    checkExpression("2>1!", "(2 > 1 ? '2>1!' : 'error')");
    checkExpression("4", "(2 > 1 ? (4 > 3 ? '4' : '3') : (7 < 6 ? 6 : 7))");
  }

  @Test
  public void testObjectSpecByRef() {
    Context context = new DefaultContext();
    context.set("greeting", "Howdy");
    checkBeanSpec("Howdy", "greeting", context);
  }

  @Test
  public void testObjectSpecByClass() {
    checkBeanSpec("", "java.lang.String");
  }

  @Test
  public void testObjectSpecByConstructor() {
    checkBeanSpec("Test", "new java.lang.String('Test')");
  }

  @Test
  public void testObjectSpecList() {
    Expression<?>[] expressions = DatabeneScriptParser.parseBeanSpecList("java.lang.String," + getClass().getName());
    assert expressions != null;
    Object[] values = ExpressionUtil.evaluateAll(expressions, new DefaultContext());
    assertEquals(2, values.length);
    assertEquals("", values[0]);
    assertSame(values[1].getClass(), this.getClass());
  }

  @Test
  public void testTransitionListOfLength1() {
    WeightedTransition[] ts = DatabeneScriptParser.parseTransitionList("'A'->'B'");
    assert ts != null;
    assertEquals(1, ts.length);
    checkAB1Transition(ts[0]);
  }

  @Test
  public void testTransitionList() {
    WeightedTransition[] ts = DatabeneScriptParser.parseTransitionList("'A'->'B',1->2^0.5");
    assert ts != null;
    assertEquals(2, ts.length);
    checkAB1Transition(ts[0]);
    assertEquals(1, ts[1].getFrom());
    assertEquals(2, ts[1].getTo());
    assertEquals(0.5, ts[1].getWeight(), 0.0001);
  }

  @Test
  public void testWeightedLiteralList() {
    WeightedSample<?>[] ts = DatabeneScriptParser.parseWeightedLiteralList("'A',1^0.5");
    assert ts != null;
    assertEquals(2, ts.length);
    assertEquals("A", ts[0].getValue());
    assertEquals(1., ts[0].getWeight(), 0.0001);
    assertEquals(1, ts[1].getValue());
    assertEquals(0.5, ts[1].getWeight(), 0.0001);
  }


  // tests migrated from BasicParserTest ---------------------------------------

  @Test
  public void testParseCustomConstruction() {
    checkBeanSpec(new ScriptTestPerson("Alice", TimeUtil.date(1972, 1, 3), 102, true, 'A'),
        "new com.rapiddweller.script.ScriptTestPerson('Alice', (date) '1972-02-03', 102, true, 'A')");
  }

  @Test
  public void testParsePropertyConstruction() {
    checkBeanSpec(new ScriptTestPerson("Alice", TimeUtil.date(1972, 1, 3), 102, true, 'A'),
        "new com.rapiddweller.script.ScriptTestPerson{name='Alice', birthDate=(date) '1972-02-03', score=102, " +
            "registered=true, rank='A'}");
    checkBeanSpec(new ScriptTestPerson("X\r\n", null, 0, false, 'A'),
        "new com.rapiddweller.script.ScriptTestPerson{name='X\\r\\n', birthDate=null, score=0, " +
            "registered=false, rank='A'}");
  }

  @Test
  public void testVariableDefinition() {
    Expression<?> expression = DatabeneScriptParser.parseExpression("x = 3");
    assert expression != null;
    assertEquals(3, expression.evaluate(context));
    assertEquals(3, context.get("x"));
  }

  @Test
  public void testVariableAssignment() {
    context.set("x", 3);
    Expression<?> expression = DatabeneScriptParser.parseExpression("x = x + 2");
    assert expression != null;
    assertEquals(5, expression.evaluate(context));
    assertEquals(5, context.get("x"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testMemberAssignment() {
    context.set("x", CollectionUtil.buildMap("y", 3));
    Expression<?> expression = DatabeneScriptParser.parseExpression("x.y = x.y + 2");
    assert expression != null;
    assertEquals(5, expression.evaluate(context));
    assertEquals(5, (int) ((Map<String, Integer>) context.get("x")).get("y"));
  }

  @Test(expected = ObjectNotFoundException.class)
  public void testUndefinedVariableReference() {
    Expression<?> expression = DatabeneScriptParser.parseExpression("x = x + 3");
    assert expression != null;
    expression.evaluate(context);
  }


  // syntax error tests ----------------------------------------------------------------------------------------------

  @Test
  public void testTrailingWhiteSpace() {
    Expression<?> expression = DatabeneScriptParser.parseExpression("   3   ");
    assert expression != null;
    expression.evaluate(context);
  }

  @Test(expected = SyntaxError.class)
  public void testMissingRHS() {
    Expression<?> expression = DatabeneScriptParser.parseExpression("3 + ");
    assert expression != null;
    expression.evaluate(context);
  }

  @Test(expected = SyntaxError.class)
  public void testMissingLHS() {
    Expression<?> expression = DatabeneScriptParser.parseExpression("/ 2");
    assert expression != null;
    expression.evaluate(context);
  }

  @Test(expected = SyntaxError.class)
  public void testMissingOperator() {
    Expression<?> expression = DatabeneScriptParser.parseExpression("'A' 'B'");
    assert expression != null;
    expression.evaluate(context);
  }

  @Test(expected = SyntaxError.class)
  public void testInvalidChoiceCondition() {
    Expression<?> expression = DatabeneScriptParser.parseExpression("1 = 3 ? 'A' : 'B'");
    assert expression != null;
    expression.evaluate(context);
  }

  @Test(expected = SyntaxError.class)
  public void testChoiceWithMissingFalseAlternative() {
    Expression<?> expression = DatabeneScriptParser.parseExpression("1 == 3 ? 'A'");
    assert expression != null;
    expression.evaluate(context);
  }

  @Test(expected = SyntaxError.class)
  public void testChoiceWithMissingTrueAlternative() {
    Expression<?> expression = DatabeneScriptParser.parseExpression("1 == 1 ? : 'B'");
    assert expression != null;
    expression.evaluate(context);
  }


  // test members to be read or called from the tested script expressions --------------------------------------------

  public static String exclamate(String arg) {
    return arg + "!";
  }

  public static String varargs1(String... args) {
    StringBuilder builder = new StringBuilder();
    for (String arg : args) {
      builder.append(arg);
    }
    return builder.toString();
  }

  public static String varargs2(String arg1, String... arg2) {
    return varargs1(arg2);
  }

  public static String staticStringAttrib = "hi!!";

  public String stringAttrib = "hi!";

  public PubField pubField = new PubField();

  public static class PubField {
    public String text = "hi";
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private void checkExpression(Object expected, String script) {
    checkExpression(expected, script, context);
  }

  private static void checkExpression(Object expected, String script, Context context) {
    Expression<?> expression = DatabeneScriptParser.parseExpression(script);
    assert expression != null;
    Object actual = expression.evaluate(context);
    assertEqual(expected, actual, script);
  }

  private static void checkBeanSpec(Object expected, String script) {
    checkBeanSpec(expected, script, new DefaultContext());
  }

  private static void checkBeanSpec(Object expected, String script, Context context) {
    Expression<?> expression = DatabeneScriptParser.parseBeanSpec(script);
    assert expression != null;
    Object actual = expression.evaluate(context);
    assertEqual(expected, actual, script);
  }

  private static void assertEqual(Object expected, Object actual, String script) {
    if (expected != null) {
      assertEquals(expected.getClass(), actual.getClass());
    } else {
      assertNull(script + " is expected to evaluate as null, but was of type " + BeanUtil.simpleClassName(actual), actual);
    }
    assertEquals(expected, actual);
  }

  private static void checkAB1Transition(WeightedTransition t10) {
    assertEquals("A", t10.getFrom());
    assertEquals("B", t10.getTo());
    assertEquals(1., t10.getWeight(), 0.0001);
  }

}
