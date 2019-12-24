package Expression;

import Operator.BinaryOperator;
import Operator.UnaryOperator;

import java.util.HashMap;
import java.util.HashSet;

public abstract class Expression {

	// EXPRESSION SUBCLASSES

	private HashSet<Character> sentences;

	public Expression() {
		sentences = new HashSet<>();
	}

	public abstract boolean evaluate(HashMap<Character, Boolean> truthValues);

	public HashSet<Character> getSentences() {
		return sentences;
	}

	public void setSentences(HashSet<Character> sentences) {
		this.sentences = sentences;
	}

	// GENERATE EXPRESSION TREE

	public static Expression generateExpressionTree(String expression) throws IllegalArgumentException {
		// TODO add operator precedence & over |
		// TODO left to right precedence A ^ B ^ C => (A ^ B) ^ C
		try {
			Expression root = null;
			int index = 0;
			while (index < expression.length()) {
				if (expression.charAt(index) == ' ') {
					index++;
					continue;
				}
				if (expression.charAt(index) == '(') {
					index++;
					int previousIndex = index;
					int count = 1;
					while (index < expression.length() && count != 0) {
						if (expression.charAt(index) == '(')
							count++;
						else if (expression.charAt(index) == ')')
							count--;
						index++;
					}
					root = addExpressionToRoot(root, new SubExpression(generateExpressionTree(expression.substring(previousIndex, index - 1))));
					continue;
				}
				if ("∧^∨⊃≡˜·*+→↔¬&|!=~".contains(String.valueOf(expression.charAt(index)))) {
					if ("∧^·*&".contains(String.valueOf(expression.charAt(index))))
						root = new BinaryExpression(root, null, BinaryOperator.CONJUNCTION);
					else if ("∨+|".contains(String.valueOf(expression.charAt(index))))
						root = new BinaryExpression(root, null, BinaryOperator.DISJUNCTION);
					else if ("⊃→".contains(String.valueOf(expression.charAt(index))))
						root = new BinaryExpression(root, null, BinaryOperator.CONDITIONAL);
					else if ("≡↔=".contains(String.valueOf(expression.charAt(index))))
						root = new BinaryExpression(root, null, BinaryOperator.BICONDITIONAL);
					else if ("˜¬!~".contains(String.valueOf(expression.charAt(index)))) {
						// TODO add XOR
//						if (expression.charAt(index) == '!' && expression.charAt(index + 1) == '=') {
//							index += 2;
//						}
						index++;
						root = addExpressionToRoot(root, new UnaryExpression(UnaryOperator.NEGATION, generateExpressionTree(expression.substring(index, index = findSubExpressionIndex(expression, index)))));
						continue;
					}
					index++;
					if ("&|=".contains(String.valueOf(expression.charAt(index))))
						index++;
					continue;
				}
				else if (expression.charAt(index) == '-' && expression.charAt(index + 1) == '>') {
					root = new BinaryExpression(root, null, BinaryOperator.CONDITIONAL);
					index+=2;
					continue;
				}
				if (expression.charAt(index) >= 'A' && expression.charAt(index) <= 'Z') {
					root = addExpressionToRoot(root, new SimpleExpression(expression.charAt(index)));
					index++;
				}
			}
			return root;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Incorrectly formatted String");
		}
	}

	private static Expression addExpressionToRoot(Expression root, Expression expression) throws Exception {
		if (root == null)
			return expression;
		BinaryExpression binaryExpression = (BinaryExpression) root;
		if (binaryExpression.getLeftExpression() == null)
			binaryExpression.setLeftExpression(expression);
		else if (binaryExpression.getRightExpression() == null) {
			binaryExpression.setRightExpression(expression);
			binaryExpression.getSentences().addAll(binaryExpression.getRightExpression().getSentences());
		}
		else
			throw new Exception("Ill-formatted Input");
		binaryExpression.getSentences().addAll(binaryExpression.getLeftExpression().getSentences());
		return binaryExpression;
	}

	private static int findSubExpressionIndex(String expression, int startingIndex) {
		int count = 0;
		while (startingIndex < expression.length()) {
			if ("∧^∨⊃≡·*+→↔&|=".contains(String.valueOf(expression.charAt(startingIndex))) && count == 0)
				break;
			if (expression.charAt(startingIndex) == '(')
				count++;
			else if (expression.charAt(startingIndex) == ')')
				count--;
			startingIndex++;
		}
		return startingIndex;
	}

	// VALIDATE ARGUMENT

	public static String validateArgument(String[] premises, String conclusion) {
		Expression[] expressionPremises = new Expression[premises.length];
		for (int i = 0; i < expressionPremises.length; i++)
			expressionPremises[i] = generateExpressionTree(premises[i]);
		return validateArgument(expressionPremises, generateExpressionTree(conclusion));
	}

	public static String validateArgument(Expression[] premises, Expression conclusion) {
//		HashSet<HashMap<Character, Boolean>> stuff;
		return null;
	}

	private static HashSet<HashMap<Character, Boolean>> findValidTruthValues(Expression expression) {
		if (expression instanceof UnaryExpression) {
			// not the expression
		}
		if (expression instanceof BinaryExpression) {
			switch (((BinaryExpression) expression).getOperator()) {
				case DISJUNCTION:
					// add two sets together and if there is a conflict, remove the elements
					break;
				case CONJUNCTION:
					// add two sets together and if there is a conflict, then there is no valid truth table
					break;
				case CONDITIONAL:
					// not the left side and do disjunction
					break;
				case BICONDITIONAL:
					//
					break;
			}
		}
		return null;
	}

	public static boolean evaluateExpression(Expression expressionTree, HashMap<Character, Boolean> truthValues) {
		return expressionTree.evaluate(truthValues);
	}

	// PRINT EXPRESSION TREE

	public static void printExpressionTree(Expression expressionTree) {
		if (expressionTree == null)
			return;
		System.out.println(expressionTree.toString());
	}

}
