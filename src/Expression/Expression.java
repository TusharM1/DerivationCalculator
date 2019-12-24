package Expression;

import Operator.Operator;

import java.util.HashMap;
import java.util.HashSet;

public abstract class Expression {

	// EXPRESSION SUBCLASSES

	private HashSet<Character> sentences;
	private boolean negation;

	public Expression() {
		sentences = new HashSet<>();
		negation = false;
	}

	public abstract boolean evaluate(HashMap<Character, Boolean> truthValues);

	public HashSet<Character> getSentences() {
		return sentences;
	}
	public void setSentences(HashSet<Character> sentences) {
		this.sentences = sentences;
	}

	public boolean isNegated() {
		return negation;
	}
	public void setNegation(boolean negation) {
		this.negation = negation;
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
					root = addExpressionToRoot(root, generateExpressionTree(expression.substring(previousIndex, index - 1)));
					continue;
				}
				if ("∧^∨⊃≡˜·*+→↔¬&|!=~".contains(String.valueOf(expression.charAt(index)))) {
					if ("∧^·*&".contains(String.valueOf(expression.charAt(index))))
						root = new BinaryExpression(root, null, Operator.CONJUNCTION);
					else if ("∨+|".contains(String.valueOf(expression.charAt(index))))
						root = new BinaryExpression(root, null, Operator.DISJUNCTION);
					else if ("⊃→".contains(String.valueOf(expression.charAt(index))))
						root = new BinaryExpression(root, null, Operator.CONDITIONAL);
					else if ("≡↔=".contains(String.valueOf(expression.charAt(index))))
						root = new BinaryExpression(root, null, Operator.BICONDITIONAL);
					else if ("˜¬!~".contains(String.valueOf(expression.charAt(index)))) {
						// TODO add XOR
//						if (expression.charAt(index) == '!' && expression.charAt(index + 1) == '=') {
//							index += 2;
//						}
						index++;
						Expression subExpression = generateExpressionTree(expression.substring(index, index = findSubExpressionIndex(expression, index)));
						if (subExpression instanceof SimpleExpression) {
							subExpression.negation = true;
							root = addExpressionToRoot(root, subExpression);
						}
						else
							root = addExpressionToRoot(root, new NegatedExpression(subExpression));
						continue;
					}
					index++;
					if ("&|=".contains(String.valueOf(expression.charAt(index))))
						index++;
					continue;
				}
				else if (expression.charAt(index) == '-' && expression.charAt(index + 1) == '>') {
					root = new BinaryExpression(root, null, Operator.CONDITIONAL);
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
			if (("∧^∨⊃≡·*+→↔&|=".contains(String.valueOf(expression.charAt(startingIndex))) ||
					expression.charAt(startingIndex) == '-' && expression.charAt(startingIndex + 1) == '>') && count == 0)
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
		if (expression instanceof NegatedExpression) {
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

	// REMOVE DOUBLE NEGATIVES

	public static Expression removeDoubleNegatives(Expression expression) {
//		if (expression )
		return null;
	}

	// EXPAND EXPRESSION

	public static Expression expandExpression(Expression expression) {
		expression = expression.clone();
		if (expression instanceof BinaryExpression) {
			BinaryExpression binaryExpression = (BinaryExpression) expression;
			switch (binaryExpression.getOperator()) {
				case DISJUNCTION:
					if (expression.negation) {
						binaryExpression.setOperator(Operator.CONJUNCTION);
						resolveNegativesLeft(binaryExpression);
						resolveNegativesRight(binaryExpression);
					}
					binaryExpression.setLeftExpression(expandExpression(binaryExpression.getLeftExpression()));
					binaryExpression.setRightExpression(expandExpression(binaryExpression.getRightExpression()));
					return binaryExpression;
				case CONJUNCTION:
					if (expression.negation) {
						binaryExpression.setOperator(Operator.DISJUNCTION);
						resolveNegativesLeft(binaryExpression);
						resolveNegativesRight(binaryExpression);
					}
					binaryExpression.setLeftExpression(expandExpression(binaryExpression.getLeftExpression()));
					binaryExpression.setRightExpression(expandExpression(binaryExpression.getRightExpression()));
					return binaryExpression;
				case CONDITIONAL:
					if (expression.negation) {
						binaryExpression.setOperator(Operator.CONJUNCTION);
						resolveNegativesRight(binaryExpression);
					}
					else {
						binaryExpression.setOperator(Operator.DISJUNCTION);
						resolveNegativesLeft(binaryExpression);
					}
					binaryExpression.setLeftExpression(expandExpression(binaryExpression.getLeftExpression()));
					binaryExpression.setRightExpression(expandExpression(binaryExpression.getRightExpression()));
					return binaryExpression;
				// Two halves point to the same thing. Don't make it a problem if it doesn't have to be
				case BICONDITIONAL:
					BinaryExpression biconditional = new BinaryExpression(new BinaryExpression(binaryExpression.getLeftExpression(), binaryExpression.getRightExpression(), Operator.CONJUNCTION),
							new BinaryExpression(binaryExpression.getLeftExpression().clone(), binaryExpression.getRightExpression(), Operator.CONJUNCTION), Operator.DISJUNCTION);
					if (expression.negation) {
						biconditional.getRightExpression();
					}
			}
		}
//		if (expression.negation) {
//			if (expression instanceof BinaryExpression) {
//				switch (((BinaryExpression) expression).getOperator()) {
//					case BICONDITIONAL:
//				}
//			}
//			if (expression instanceof UnaryExpression)
//		}
		return null;
	}

	private static void resolveNegativesRight(BinaryExpression binaryExpression) {
		Expression rightExpression = binaryExpression.getRightExpression();
		if (rightExpression instanceof BinaryExpression)
			rightExpression.negation = !rightExpression.negation;
		else if (rightExpression instanceof NegatedExpression) {
			Expression rightInner = ((NegatedExpression) rightExpression).getExpression();
			if (rightInner.isNegated()) {
				rightInner.setNegation(false);
				binaryExpression.setRightExpression(rightInner);
			}
		}
	}

	private static void resolveNegativesLeft(BinaryExpression binaryExpression) {
		Expression leftExpression = binaryExpression.getLeftExpression();
		if (leftExpression instanceof BinaryExpression)
			leftExpression.negation = !leftExpression.negation;
		else if (leftExpression instanceof NegatedExpression) {
			Expression leftInner = ((NegatedExpression) leftExpression).getExpression();
			if (leftInner.isNegated()) {
				leftInner.setNegation(false);
				binaryExpression.setLeftExpression(leftInner);
			}
		}
	}

	// PRINT EXPRESSION TREE

	public static String expressionToString(Expression expression) {
		if (expression == null)
			return "";
		if (expression instanceof BinaryExpression) {
			BinaryExpression binaryExpression = (BinaryExpression) expression;
			return binaryExpression.getLeftExpression().toString() + " " +
					binaryExpression.getOperator().toString() + " " +
					binaryExpression.getRightExpression().toString();
		}
		return expression.toString();
	}

	public static void printExpression(Expression expression) {
		System.out.println(expressionToString(expression));
	}

	@Override
	protected Expression clone() {
		return null;
	}
}
