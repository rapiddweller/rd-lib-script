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

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.Assert;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.Expression;
import com.rapiddweller.common.ParseUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.exception.SyntaxError;
import com.rapiddweller.script.antlr.DatabeneScriptLexer;
import com.rapiddweller.script.expression.AssignmentExpression;
import com.rapiddweller.script.expression.BeanConstruction;
import com.rapiddweller.script.expression.BitwiseAndExpression;
import com.rapiddweller.script.expression.BitwiseComplementExpression;
import com.rapiddweller.script.expression.BitwiseExclusiveOrExpression;
import com.rapiddweller.script.expression.BitwiseOrExpression;
import com.rapiddweller.script.expression.ConditionalAndExpression;
import com.rapiddweller.script.expression.ConditionalExpression;
import com.rapiddweller.script.expression.ConditionalOrExpression;
import com.rapiddweller.script.expression.ConstantExpression;
import com.rapiddweller.script.expression.DivisionExpression;
import com.rapiddweller.script.expression.EqualsExpression;
import com.rapiddweller.script.expression.FieldExpression;
import com.rapiddweller.script.expression.ForNameExpression;
import com.rapiddweller.script.expression.GreaterExpression;
import com.rapiddweller.script.expression.GreaterOrEqualsExpression;
import com.rapiddweller.script.expression.IndexExpression;
import com.rapiddweller.script.expression.InvocationExpression;
import com.rapiddweller.script.expression.LeftShiftExpression;
import com.rapiddweller.script.expression.LessExpression;
import com.rapiddweller.script.expression.LessOrEqualsExpression;
import com.rapiddweller.script.expression.LogicalComplementExpression;
import com.rapiddweller.script.expression.ModuloExpression;
import com.rapiddweller.script.expression.MultiplicationExpression;
import com.rapiddweller.script.expression.NotEqualsExpression;
import com.rapiddweller.script.expression.ParameterizedConstruction;
import com.rapiddweller.script.expression.RightShiftExpression;
import com.rapiddweller.script.expression.SubtractionExpression;
import com.rapiddweller.script.expression.SumExpression;
import com.rapiddweller.script.expression.TypeConvertingExpression;
import com.rapiddweller.script.expression.UnaryMinusExpression;
import com.rapiddweller.script.expression.UnsignedRightShiftExpression;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;

/**
 * Parses Benerator Script statements and converts expressions and statements to Java objects.<br/><br/>
 * Created at 05.10.2009 18:52:31
 * @author Volker Bergmann
 * @since 0.6.0
 */
public class DatabeneScriptParser {

  private static final Logger logger = LoggerFactory.getLogger(DatabeneScriptParser.class);
  private static final Expression<?>[] NO_EXPRESSIONS = {};
  private static final WeightedTransition[] NO_TRANSITIONS = {};
  private static final WeightedSample<?>[] NO_SAMPLES = {};
  public static final String ILLEGAL_STATE_IN_REGEX_PARSING = "Encountered illegal state in regex parsing";

  // interface -------------------------------------------------------------------------------------------------------

  private DatabeneScriptParser() {
    // private constructor to prevent instantiation of this utility class
  }

  public static WeightedSample<?>[] parseWeightedLiteralList(String text) throws SyntaxError {
    if (StringUtil.isEmpty(text)) {
      return new WeightedSample[0];
    }
    try {
      com.rapiddweller.script.antlr.DatabeneScriptParser parser = parser(text);
      com.rapiddweller.script.antlr.DatabeneScriptParser.weightedLiteralList_return r = parser.weightedLiteralList();
      checkForSyntaxErrors(text, "weightedLiteralList", parser, r);
      if (r != null) {
        CommonTree tree = (CommonTree) r.getTree();
        logParseResult(text, tree);
        return convertWeightedLiteralList(tree);
      } else {
        return NO_SAMPLES;
      }
    } catch (RuntimeException e) {
      if (e.getCause() instanceof RecognitionException) {
        throw mapToSyntaxError((RecognitionException) e.getCause(), text);
      } else {
        throw e;
      }
    } catch (IOException e) {
      throw new IllegalStateException("Encountered illegal state in weightedLiteralList parsing", e);
    } catch (RecognitionException e) {
      e.printStackTrace();
      throw mapToSyntaxError(e, text);
    }
  }

  public static Expression<?> parseExpression(String text) throws SyntaxError {
    if (StringUtil.isEmpty(text)) {
      return null;
    }
    try {
      com.rapiddweller.script.antlr.DatabeneScriptParser parser = parser(text);
      com.rapiddweller.script.antlr.DatabeneScriptParser.expression_return r = parser.expression();
      checkForSyntaxErrors(text, "expression", parser, r);
      CommonTree tree = (CommonTree) r.getTree();
      logParseResult(text, tree);
      return convertNode(tree);
    } catch (RuntimeException e) {
      if (e.getCause() instanceof RecognitionException) {
        throw mapToSyntaxError((RecognitionException) e.getCause(), text);
      } else {
        throw e;
      }
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().internalError(ILLEGAL_STATE_IN_REGEX_PARSING, e);
    } catch (RecognitionException e) {
      throw mapToSyntaxError(e, text);
    }
  }

  public static WeightedTransition[] parseTransitionList(String text) throws SyntaxError {
    if (StringUtil.isEmpty(text)) {
      return NO_TRANSITIONS;
    }
    try {
      com.rapiddweller.script.antlr.DatabeneScriptParser parser = parser(text);
      com.rapiddweller.script.antlr.DatabeneScriptParser.transitionList_return r = parser.transitionList();
      checkForSyntaxErrors(text, "transitionList", parser, r);
      if (r != null) {
        CommonTree tree = (CommonTree) r.getTree();
        logParseResult(text, tree);
        return convertTransitionList(tree);
      } else {
        return NO_TRANSITIONS;
      }
    } catch (RuntimeException e) {
      if (e.getCause() instanceof RecognitionException) {
        throw mapToSyntaxError((RecognitionException) e.getCause(), text);
      } else {
        throw e;
      }
    } catch (IOException e) {
      throw new IllegalStateException(ILLEGAL_STATE_IN_REGEX_PARSING, e);
    } catch (RecognitionException e) {
      e.printStackTrace();
      throw mapToSyntaxError(e, text);
    }
  }

  public static Expression<?>[] parseBeanSpecList(String text) throws SyntaxError {
    if (StringUtil.isEmpty(text)) {
      return NO_EXPRESSIONS;
    }
    CommonTree tree = parseBeanSpecListAsTree(text);
    return convertBeanSpecList(Objects.requireNonNull(tree));
  }

  public static CommonTree parseBeanSpecListAsTree(String text) throws SyntaxError {
    if (StringUtil.isEmpty(text)) {
      return null;
    }
    try {
      com.rapiddweller.script.antlr.DatabeneScriptParser parser = parser(text);
      com.rapiddweller.script.antlr.DatabeneScriptParser.beanSpecList_return r = parser.beanSpecList();
      checkForSyntaxErrors(text, "beanSpecList", parser, r);
      if (r != null) {
        CommonTree tree = (CommonTree) r.getTree();
        logParseResult(text, tree);
        return tree;
      } else {
        return null;
      }
    } catch (RuntimeException e) {
      if (e.getCause() instanceof RecognitionException) {
        throw mapToSyntaxError((RecognitionException) e.getCause(), text);
      } else {
        throw e;
      }
    } catch (IOException e) {
      throw new IllegalStateException(ILLEGAL_STATE_IN_REGEX_PARSING, e);
    } catch (RecognitionException e) {
      throw mapToSyntaxError(e, text);
    }
  }

  public static BeanSpec[] resolveBeanSpecList(String text, Context context) {
    if (StringUtil.isEmpty(text)) {
      return null;
    }
    CommonTree tree = parseBeanSpecListAsTree(text);
    return resolveBeanSpecList(Objects.requireNonNull(tree), context);
  }

  public static Expression<?> parseBeanSpec(String text) throws SyntaxError {
    if (StringUtil.isEmpty(text)) {
      return null;
    }
    try {
      com.rapiddweller.script.antlr.DatabeneScriptParser parser = parser(text);
      com.rapiddweller.script.antlr.DatabeneScriptParser.beanSpec_return r = parser.beanSpec();
      checkForSyntaxErrors(text, "beanSpec", parser, r);
      if (r != null) {
        CommonTree tree = (CommonTree) r.getTree();
        if (logger.isDebugEnabled()) {
          logParseResult(text, tree);
        }
        return convertBeanSpec(tree);
      } else {
        return null;
      }
    } catch (RuntimeException e) {
      if (e.getCause() instanceof RecognitionException) {
        throw mapToSyntaxError((RecognitionException) e.getCause(), text);
      } else {
        throw e;
      }
    } catch (IOException e) {
      throw new IllegalStateException(ILLEGAL_STATE_IN_REGEX_PARSING, e);
    } catch (RecognitionException e) {
      throw mapToSyntaxError(e, text);
    }
  }

  public static BeanSpec resolveBeanSpec(String text, Context context) throws SyntaxError {
    if (StringUtil.isEmpty(text)) {
      return null;
    }
    try {
      com.rapiddweller.script.antlr.DatabeneScriptParser parser = parser(text);
      com.rapiddweller.script.antlr.DatabeneScriptParser.beanSpec_return r = parser.beanSpec();
      checkForSyntaxErrors(text, "beanSpec", parser, r);
      if (r != null) {
        CommonTree tree = (CommonTree) r.getTree();
        if (logger.isDebugEnabled()) {
          logParseResult(text, tree);
        }
        return resolveBeanSpec(tree, context);
      } else {
        return null;
      }
    } catch (RuntimeException e) {
      if (e.getCause() instanceof RecognitionException) {
        throw mapToSyntaxError((RecognitionException) e.getCause(), text);
      } else {
        throw e;
      }
    } catch (IOException e) {
      throw new IllegalStateException(ILLEGAL_STATE_IN_REGEX_PARSING, e);
    } catch (RecognitionException e) {
      throw mapToSyntaxError(e, text);
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static void checkForSyntaxErrors(String text, String type,
                                           com.rapiddweller.script.antlr.DatabeneScriptParser parser, ParserRuleReturnScope r) {
    if (parser.getNumberOfSyntaxErrors() > 0) {
      throw ExceptionFactory.getInstance().syntaxErrorForText("Illegal " + type, text, -1, -1);
    }
    CommonToken stop = (CommonToken) r.stop;
    if (stop.getStopIndex() < StringUtil.trimRight(text).length() - 1) {
      if (stop.getStopIndex() == 0) {
        throw ExceptionFactory.getInstance().syntaxErrorForText("Syntax error after " + stop.getText(), text);
      } else {
        throw ExceptionFactory.getInstance().syntaxErrorForText("Unspecific syntax error", text);
      }
    }
  }

  private static com.rapiddweller.script.antlr.DatabeneScriptParser parser(String text) throws IOException {
    DatabeneScriptLexer lex = new DatabeneScriptLexer(new ANTLRReaderStream(new StringReader(text)));
    CommonTokenStream tokens = new CommonTokenStream(lex);
    return new com.rapiddweller.script.antlr.DatabeneScriptParser(tokens);
  }

  private static SyntaxError mapToSyntaxError(RecognitionException cause, String text) {
    return ExceptionFactory.getInstance().syntaxErrorForText("Error parsing Benerator Script expression", cause,
        text, cause.line, cause.charPositionInLine);
  }

  private static WeightedSample<?>[] convertWeightedLiteralList(CommonTree node) throws SyntaxError {
    if (!node.isNil()) {
      return new WeightedSample[] {convertWeightedLiteral(node)};
    } else {
      int childCount = node.getChildCount();
      WeightedSample<?>[] transitions = new WeightedSample[childCount];
      for (int i = 0; i < childCount; i++) {
        transitions[i] = convertWeightedLiteral(childAt(i, node));
      }
      return transitions;
    }
  }

  private static WeightedSample<?> convertWeightedLiteral(CommonTree node) throws SyntaxError {
    if (node.getType() == DatabeneScriptLexer.CARET) {
      Expression<?> value = convertNode(childAt(0, node));
      Expression<Double> weight;
      if (node.getChildCount() > 1) {
        weight = new TypeConvertingExpression<>(convertNode(childAt(1, node)), Double.class);
      } else {
        weight = new ConstantExpression<>(1.);
      }
      return new WeightedSample<>(value.evaluate(null), weight.evaluate(null));
    } else {
      return new WeightedSample<>(convertNode(node).evaluate(null), 1.);
    }
  }

  private static WeightedTransition[] convertTransitionList(CommonTree node) throws SyntaxError {
    if (node.getType() == DatabeneScriptLexer.ARROW) {
      return new WeightedTransition[] {convertTransition(node)};
    } else if (node.isNil()) {
      int childCount = node.getChildCount();
      WeightedTransition[] transitions = new WeightedTransition[childCount];
      for (int i = 0; i < childCount; i++) {
        transitions[i] = convertTransition(childAt(i, node));
      }
      return transitions;
    } else {
      throw ExceptionFactory.getInstance().syntaxErrorForText(
          "Unexpected token in transition list: ", node.getToken().getText(),
          node.getLine(), node.getCharPositionInLine());
    }
  }

  private static WeightedTransition convertTransition(CommonTree node) throws SyntaxError {
    Assert.isTrue(node.getType() == DatabeneScriptLexer.ARROW, "expected transition, found: " + node.getToken());
    Expression<?> from = convertNode(childAt(0, node));
    Expression<?> to = convertNode(childAt(1, node));
    Expression<Double> weight;
    if (node.getChildCount() > 2) {
      weight = new TypeConvertingExpression<>(convertNode(childAt(2, node)), Double.class);
    } else {
      weight = new ConstantExpression<>(1.);
    }
    return new WeightedTransition(from.evaluate(null), to.evaluate(null), weight.evaluate(null));
  }

  private static Expression<?>[] convertBeanSpecList(CommonTree node) throws SyntaxError {
    if (node.getType() == DatabeneScriptLexer.BEANSPEC) {
      return new Expression<?>[] {convertBeanSpec(node)};
    } else if (node.isNil()) {
      int childCount = node.getChildCount();
      Expression<?>[] specs = new Expression<?>[childCount];
      for (int i = 0; i < childCount; i++) {
        specs[i] = convertBeanSpec(childAt(i, node));
      }
      return specs;
    } else {
      throw ExceptionFactory.getInstance().syntaxErrorForText("Unexpected token", node.getToken().getText(), node.getLine(), node.getCharPositionInLine());
    }
  }

  private static BeanSpec[] resolveBeanSpecList(CommonTree node, Context context) throws SyntaxError {
    if (node.getType() == DatabeneScriptLexer.BEANSPEC) {
      return new BeanSpec[] {resolveBeanSpec(node, context)};
    } else if (node.isNil()) {
      int childCount = node.getChildCount();
      BeanSpec[] specs = new BeanSpec[childCount];
      for (int i = 0; i < childCount; i++) {
        specs[i] = resolveBeanSpec(childAt(i, node), context);
      }
      return specs;
    } else {
      throw ExceptionFactory.getInstance().syntaxErrorForText("Unexpected token", node.getToken().getText(), node.getLine(), node.getCharPositionInLine());
    }
  }

  private static Expression<?> convertBeanSpec(CommonTree node) throws SyntaxError {
    Assert.isTrue(node.getType() == DatabeneScriptLexer.BEANSPEC, "BEANSPEC expected, found: " + node.getToken());
    node = childAt(0, node);
    if (node.getType() == DatabeneScriptLexer.QUALIFIEDNAME) {
      return new QNBeanSpecExpression(convertQualifiedNameToStringArray(node));
    } else if (node.getType() == DatabeneScriptLexer.IDENTIFIER) {
      return new QNBeanSpecExpression(new String[] {node.getText()});
    } else if (node.getType() == DatabeneScriptLexer.BEAN) {
      return convertBean(node);
    } else {
      return convertNode(node);
    }
  }

  private static BeanSpec resolveBeanSpec(CommonTree node, Context context) throws SyntaxError {
    Assert.isTrue(node.getType() == DatabeneScriptLexer.BEANSPEC, "BEANSPEC expected, found: " + node.getToken());
    node = childAt(0, node);
    if (node.getType() == DatabeneScriptLexer.QUALIFIEDNAME) {
      return new QNBeanSpecExpression(convertQualifiedNameToStringArray(node)).resolve(context);
    } else if (node.getType() == DatabeneScriptLexer.IDENTIFIER) {
      return new QNBeanSpecExpression(new String[] {node.getText()}).resolve(context);
    } else if (node.getType() == DatabeneScriptLexer.BEAN) {
      return BeanSpec.createConstruction(convertBean(node).evaluate(context));
    } else if (node.getType() == DatabeneScriptLexer.CONSTRUCTOR) {
      return BeanSpec.createConstruction(convertNode(node).evaluate(context));
    } else {
      return BeanSpec.createReference(convertNode(node).evaluate(context));
    }
  }

  private static Expression<?> convertNode(CommonTree node) throws SyntaxError {
    switch (node.getType()) {
      case DatabeneScriptLexer.NULL:
        return new ConstantExpression<>(null);
      case DatabeneScriptLexer.BOOLEANLITERAL:
        return convertBooleanLiteral(node);
      case DatabeneScriptLexer.INTLITERAL:
        return convertIntLiteral(node);
      case DatabeneScriptLexer.DECIMALLITERAL:
        return convertDecimalLiteral(node);
      case DatabeneScriptLexer.STRINGLITERAL:
        return convertStringLiteral(node);
      case DatabeneScriptLexer.IDENTIFIER:
        return convertIdentifier(node);
      case DatabeneScriptLexer.QUALIFIEDNAME:
        return convertQualifiedName(node);
      case DatabeneScriptLexer.TYPE:
        return convertType(node);
      case DatabeneScriptLexer.CONSTRUCTOR:
        return convertCreator(node);
      case DatabeneScriptLexer.BEAN:
        return convertBean(node);
      case DatabeneScriptLexer.INVOCATION:
        return convertInvocation(node);
      case DatabeneScriptLexer.SUBINVOCATION:
        return convertSubInvocation(node);
      case DatabeneScriptLexer.INDEX:
        return convertIndex(node);
      case DatabeneScriptLexer.FIELD:
        return convertField(node);
      case DatabeneScriptLexer.CAST:
        return convertCast(node);
      case DatabeneScriptLexer.NEGATION:
        return convertNegation(node);
      case DatabeneScriptLexer.BANG:
        return convertLogicalComplement(node);
      case DatabeneScriptLexer.TILDE:
        return convertBitwiseComplement(node);
      case DatabeneScriptLexer.PLUS:
        return convertPlus(node);
      case DatabeneScriptLexer.SUB:
        return convertMinus(node);
      case DatabeneScriptLexer.STAR:
        return convertStar(node);
      case DatabeneScriptLexer.SLASH:
        return convertSlash(node);
      case DatabeneScriptLexer.PERCENT:
        return convertPercent(node);
      case DatabeneScriptLexer.AMP:
        return convertAnd(node);
      case DatabeneScriptLexer.BAR:
        return convertInclusiveOr(node);
      case DatabeneScriptLexer.CARET:
        return convertExclusiveOr(node);
      case DatabeneScriptLexer.EQEQ:
        return convertEquals(node);
      case DatabeneScriptLexer.BANGEQ:
        return convertNotEquals(node);
      case DatabeneScriptLexer.LT:
        return convertLess(node);
      case DatabeneScriptLexer.LE:
        return convertLessOrEquals(node);
      case DatabeneScriptLexer.GT:
        return convertGreater(node);
      case DatabeneScriptLexer.GE:
        return convertGreaterOrEquals(node);
      case DatabeneScriptLexer.SHIFT_LEFT:
        return convertShiftLeft(node);
      case DatabeneScriptLexer.SHIFT_RIGHT:
        return convertShiftRight(node);
      case DatabeneScriptLexer.SHIFT_RIGHT2:
        return convertShiftRight2(node);
      case DatabeneScriptLexer.AMPAMP:
        return convertConditionalAnd(node);
      case DatabeneScriptLexer.BARBAR:
        return convertConditionalOr(node);
      case DatabeneScriptLexer.QUES:
        return convertConditionalExpression(node);
      case DatabeneScriptLexer.EQ:
        return convertAssignment(node);
      default:
        throw ExceptionFactory.getInstance().syntaxErrorForText("Unknown token type", String.valueOf(node.getType()),
            node.getLine(), node.getCharPositionInLine());
    }
  }

  private static Expression<Boolean> convertBooleanLiteral(CommonTree node) {
    return new ConstantExpression<>(ParseUtil.parseBoolean(node.getText()));
  }

  private static Expression<String> convertStringLiteral(CommonTree node) {
    String rawString = node.getText();
    String text = rawString.substring(1, rawString.length() - 1);
    text = StringUtil.unescape(text);
    return new ConstantExpression<>(text);
  }

  private static Expression<String> convertIdentifier(CommonTree node) {
    return new ConstantExpression<>(node.getText());
  }

  private static Expression<?> convertQualifiedName(CommonTree node) {
    return new QNExpression(convertQualifiedNameToStringArray(node));
  }

  private static String[] convertQualifiedNameToStringArray(CommonTree node) {
    int childCount = node.getChildCount();
    String[] result = new String[childCount];
    List<CommonTree> childNodes = getChildNodes(node);
    for (int i = 0; i < childCount; i++) {
      result[i] = childNodes.get(i).getText();
    }
    return result;
  }

  private static Expression<Class<?>> convertType(CommonTree node) {
    String[] classNameParts = convertQualifiedNameToStringArray(childAt(0, node));
    final String className = ArrayFormat.format(".", classNameParts);
    PrimitiveType primitiveType = PrimitiveType.getInstance(className);
    if (primitiveType != null) {
      return new ConstantExpression<>(primitiveType.getJavaType());
    } else {
      return new ForNameExpression(new ConstantExpression<>(className));
    }
  }

  private static Expression<? extends Number> convertIntLiteral(CommonTree node) {
    String text = node.getText();
    Number number;
    if (text.length() > 10) {
      number = Long.parseLong(text);
    } else if (text.length() == 10) {
      long l = Long.parseLong(text);
      if (l <= Integer.MAX_VALUE) {
        number = (int) l;
      } else {
        number = l;
      }
    } else {
      number = Integer.parseInt(text);
    }
    return new ConstantExpression<>(number);
  }

  private static Expression<Double> convertDecimalLiteral(CommonTree node) {
    return new ConstantExpression<>(Double.parseDouble(node.getText()));
  }

  private static Expression<?> convertCreator(CommonTree node) throws SyntaxError {
    List<CommonTree> childNodes = getChildNodes(node);
    String className = parseQualifiedNameOfClass(childNodes.get(0));
    Expression<?>[] params = parseArguments(childNodes.get(1));
    return new ParameterizedConstruction<>(className, params);
  }

  private static Expression<?> convertBean(CommonTree node) throws SyntaxError {
    List<CommonTree> childNodes = getChildNodes(node);
    String className = parseQualifiedNameOfClass(childNodes.get(0));
    Assignment[] props = parseFieldAssignments(childNodes, 1);
    return new BeanConstruction<>(className, props);
  }

  private static Assignment[] parseFieldAssignments(List<CommonTree> nodes, int firstIndex) throws SyntaxError {
    Assignment[] assignments = new Assignment[nodes.size() - firstIndex];
    for (int i = firstIndex; i < nodes.size(); i++) {
      CommonTree assignmentNode = nodes.get(i);
      CommonTree nameNode = childAt(0, assignmentNode);
      String name = (nameNode.getType() == DatabeneScriptLexer.QUALIFIEDNAME ? childAt(0, nameNode).getText() : nameNode.getText());
      CommonTree exNode = childAt(1, assignmentNode);
      Expression<?> ex = convertNode(exNode);
      assignments[i - firstIndex] = new Assignment(name, ex);
    }
    return assignments;
  }

  private static Expression<?> convertInvocation(CommonTree node) throws SyntaxError {
    String[] qn = convertQualifiedNameToStringArray(childAt(0, node));
    Expression<?>[] argExpressions = parseArguments(childAt(1, node));
    return new QNInvocationExpression(qn, argExpressions);
  }

  private static Expression<?> convertSubInvocation(CommonTree node) throws SyntaxError {
    Expression<?> object = convertNode(childAt(0, node));
    String methodMame = (String) convertNode(childAt(1, node)).evaluate(null);
    Expression<?>[] argsExpressions = parseArguments(childAt(2, node));
    return new InvocationExpression(object, methodMame, argsExpressions);
  }

  private static Expression<?> convertIndex(CommonTree node) throws SyntaxError {
    return new IndexExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<?> convertField(CommonTree node) throws SyntaxError {
    return new FieldExpression(convertNode(childAt(0, node)), convertIdentifier(childAt(1, node)).evaluate(null));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static Expression<?> convertCast(CommonTree node) throws SyntaxError {
    Class<?> targetType = (Class<?>) convertNode(childAt(0, node)).evaluate(null);
    Expression<?> sourceExpression = convertNode(childAt(1, node));
    return new TypeConvertingExpression(sourceExpression, targetType);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static Expression<?> convertNegation(CommonTree node) throws SyntaxError {
    return new UnaryMinusExpression(convertNode(childAt(0, node)));
  }

  private static Expression<?> convertLogicalComplement(CommonTree node) throws SyntaxError {
    return new LogicalComplementExpression(convertNode(childAt(0, node)));
  }

  private static Expression<?> convertBitwiseComplement(CommonTree node) throws SyntaxError {
    return new BitwiseComplementExpression(convertNode(childAt(0, node)));
  }

  private static String parseQualifiedNameOfClass(CommonTree node) {
    List<CommonTree> childNodes = getChildNodes(node);
    StringBuilder className = new StringBuilder();
    for (CommonTree childNode : childNodes) {
      if (className.length() > 0) {
        className.append('.');
      }
      className.append(childNode.getText());
    }
    return className.toString();
  }

  private static Expression<?>[] parseArguments(CommonTree node) throws SyntaxError {
    List<CommonTree> childNodes = getChildNodes(node);
    if (childNodes == null) {
      return NO_EXPRESSIONS;
    }
    Expression<?>[] result = new Expression[childNodes.size()];
    for (int i = 0; i < childNodes.size(); i++) {
      CommonTree childNode = childNodes.get(i);
      result[i] = convertNode(childNode);
    }
    return result;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static Expression<?> convertPlus(CommonTree node) throws SyntaxError {
    SumExpression result = new SumExpression();
    for (CommonTree child : getChildNodes(node)) {
      result.addTerm((Expression) convertNode(child));
    }
    return result;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static Expression<?> convertMinus(CommonTree node) throws SyntaxError {
    SubtractionExpression result = new SubtractionExpression();
    for (CommonTree child : getChildNodes(node)) {
      result.addTerm((Expression) convertNode(child));
    }
    return result;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static Expression<?> convertStar(CommonTree node) throws SyntaxError {
    MultiplicationExpression result = new MultiplicationExpression();
    for (CommonTree child : getChildNodes(node)) {
      result.addTerm((Expression) convertNode(child));
    }
    return result;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static Expression<?> convertSlash(CommonTree node) throws SyntaxError {
    DivisionExpression result = new DivisionExpression();
    for (CommonTree child : getChildNodes(node)) {
      result.addTerm((Expression) convertNode(child));
    }
    return result;
  }

  private static Expression<Object> convertPercent(CommonTree node) throws SyntaxError {
    return new ModuloExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Object> convertShiftLeft(CommonTree node) throws SyntaxError {
    return new LeftShiftExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Object> convertShiftRight(CommonTree node) throws SyntaxError {
    return new RightShiftExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Object> convertShiftRight2(CommonTree node) throws SyntaxError {
    return new UnsignedRightShiftExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Object> convertAnd(CommonTree node) throws SyntaxError {
    return new BitwiseAndExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Object> convertInclusiveOr(CommonTree node) throws SyntaxError {
    return new BitwiseOrExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Object> convertExclusiveOr(CommonTree node) throws SyntaxError {
    return new BitwiseExclusiveOrExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Boolean> convertEquals(CommonTree node) throws SyntaxError {
    return new EqualsExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Boolean> convertNotEquals(CommonTree node) throws SyntaxError {
    return new NotEqualsExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Boolean> convertLess(CommonTree node) throws SyntaxError {
    return new LessExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Boolean> convertLessOrEquals(CommonTree node) throws SyntaxError {
    return new LessOrEqualsExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Boolean> convertGreater(CommonTree node) throws SyntaxError {
    return new GreaterExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  private static Expression<Boolean> convertGreaterOrEquals(CommonTree node) throws SyntaxError {
    return new GreaterOrEqualsExpression(convertNode(childAt(0, node)), convertNode(childAt(1, node)));
  }

  @SuppressWarnings("unchecked")
  private static Expression<Boolean> convertConditionalOr(CommonTree node) throws SyntaxError {
    ConditionalOrExpression result = new ConditionalOrExpression("||");
    for (CommonTree child : getChildNodes(node)) {
      result.addTerm((Expression<Object>) convertNode(child));
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private static Expression<Boolean> convertConditionalAnd(CommonTree node) throws SyntaxError {
    ConditionalAndExpression result = new ConditionalAndExpression("&&");
    for (CommonTree child : getChildNodes(node)) {
      result.addTerm((Expression<Object>) convertNode(child));
    }
    return result;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static Expression<?> convertConditionalExpression(CommonTree node) throws SyntaxError {
    return new ConditionalExpression(
        convertNode(childAt(0, node)),  // condition
        convertNode(childAt(1, node)),  // true alternative
        convertNode(childAt(2, node))); // false alternative
  }

  private static Expression<?> convertAssignment(CommonTree node) throws SyntaxError {
    return new AssignmentExpression(
        convertQualifiedNameToStringArray(childAt(0, node)),
        convertNode(childAt(1, node)));
  }

  private static void logParseResult(String input, CommonTree result) {
    logger.debug("parsed {} to {}", input, result.toStringTree());
  }

  // CommonTree helpers ----------------------------------------------------------------------------------------------

  private static CommonTree childAt(int index, CommonTree node) {
    return (CommonTree) node.getChild(index);
  }

  @SuppressWarnings("unchecked")
  private static List<CommonTree> getChildNodes(CommonTree node) {
    return node.getChildren();
  }

}
