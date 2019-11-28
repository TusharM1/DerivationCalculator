package Expression;

import Operator.UnaryOperator;
import Sentence.Sentence;

public abstract class Expression {

	public static Expression generateExpressionTree(String expression) throws IllegalArgumentException {
		try {
			Expression root = null, currentExpression = null;
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
					Expression subExpression = generateExpressionTree(expression.substring(previousIndex, index));
//					if (root == null)
//						root = subExpression;
//					else if (root instanceof UnaryExpression)
//						((UnaryExpression) root).setExpression(subExpression);
//					else {
//						BinaryExpression binaryExpression = (BinaryExpression) root;
//						if (binaryExpression.getLeftExpression() == null)
//							binaryExpression.setLeftExpression(subExpression);
//						else if (binaryExpression.getRightExpression() == null)
//							binaryExpression.setRightExpression(subExpression);
//						else
//							throw new Exception();
//					}
					continue;
				}
				if (expression.charAt(index) == '~') {
//					if (root == null)
//						root = new UnaryExpression(null, UnaryOperator.NEGATION);
					continue;
				}
//				if ("&|!".contains(String.valueOf(expression.charAt(index))) ||
//					"∧")
				if (expression.charAt(index) >= 'A' && expression.charAt(index) <= 'Z') {
//					SimpleExpression simpleExpression = new SimpleExpression(new Sentence(expression.charAt(index)));
//					if (root == null)
//						root = simpleExpression;
//					else if (root instanceof UnaryExpression) {
//						UnaryExpression currentExpression = (UnaryExpression) root;
//						while (!(currentExpression.getExpression() instanceof UnaryExpression))
//							currentExpression = (UnaryExpression) currentExpression.getExpression();
//						currentExpression.setExpression(simpleExpression);
//					}
//					else if (root instanceof BinaryExpression) {
//						BinaryExpression binaryExpression = (BinaryExpression) root;
//						if (binaryExpression.getLeftExpression() == null)
//							binaryExpression.setLeftExpression();
//					}
				}
			}
			return root;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Incorrectly formatted String");
		}
	}

}
