package Expression;

import Operator.BinaryOperator;
import Operator.UnaryOperator;
import Sentence.Sentence;

public abstract class Expression {

	public static Expression generateExpressionTree(String expression) throws IllegalArgumentException {
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
					SubExpression subExpression = new SubExpression(generateExpressionTree(expression.substring(previousIndex, index - 1)));
					if (root == null)
						root = subExpression;
					else if (root instanceof BinaryExpression) {
						BinaryExpression binaryExpression = (BinaryExpression) root;
						if (binaryExpression.getLeftExpression() == null)
							binaryExpression.setLeftExpression(subExpression);
						else if (binaryExpression.getRightExpression() == null)
							binaryExpression.setRightExpression(subExpression);
//						else
//							throw new Exception();
					}
					continue;
				}
//				if (expression.charAt(index) == '~') {
////					if (root == null)
////						root = new UnaryExpression(null, UnaryOperator.NEGATION);
//					continue;
//				}
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
						index++;
						int previousIndex = index;
						int count = 0;
						while (index < expression.length()) {
							if ("∧^∨⊃≡·*+→↔&|=".contains(String.valueOf(expression.charAt(index))) && count == 0)
								break;
							if (expression.charAt(index) == '(')
								count++;
							else if (expression.charAt(index) == ')')
								count--;
							index++;
						}
						UnaryExpression unaryExpression = new UnaryExpression(UnaryOperator.NEGATION, generateExpressionTree(expression.substring(previousIndex, index)));
						if (root == null)
							root = unaryExpression;
						else if (root instanceof BinaryExpression) {
							BinaryExpression binaryExpression = (BinaryExpression) root;
							if (binaryExpression.getLeftExpression() == null)
								binaryExpression.setLeftExpression(unaryExpression);
							else if (binaryExpression.getRightExpression() == null)
								binaryExpression.setRightExpression(unaryExpression);
//						else
//							throw new Exception();
						}
						continue;
					}
					index++;
					continue;
				}
				else if (expression.charAt(index) == '-' && expression.charAt(index + 1) == '>') {
					root = new BinaryExpression(root, null, BinaryOperator.CONDITIONAL);
					index+=2;
					continue;
				}
//				if ("&|!".contains(String.valueOf(expression.charAt(index))) ||
//					"∧")
				if (expression.charAt(index) >= 'A' && expression.charAt(index) <= 'Z') {
					SimpleExpression simpleExpression = new SimpleExpression(new Sentence(expression.charAt(index)));
					if (root == null)
						root = simpleExpression;
//					else if (root instanceof UnaryExpression) {
//						UnaryExpression currentExpression = (UnaryExpression) root;
//						while (!(currentExpression.getExpression() instanceof UnaryExpression))
//							currentExpression = (UnaryExpression) currentExpression.getExpression();
//						currentExpression.setExpression(simpleExpression);
//					}
					else if (root instanceof BinaryExpression) {
						BinaryExpression binaryExpression = (BinaryExpression) root;
						if (binaryExpression.getLeftExpression() == null)
							binaryExpression.setLeftExpression(simpleExpression);
						else if (binaryExpression.getRightExpression() == null)
							binaryExpression.setRightExpression(simpleExpression);
//						else
//							throw new Exception();
					}
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

	public static void printExpressionTree(Expression expressionTree) {
		if (expressionTree == null)
			return;
		System.out.println(expressionTree.toString());
	}

}
