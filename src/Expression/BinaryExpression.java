package Expression;

import Operator.BinaryOperator;

import java.util.HashMap;

public class BinaryExpression extends Expression {

	private Expression leftExpression, rightExpression;
	private BinaryOperator operator;

	public BinaryExpression(Expression leftExpression, Expression rightExpression, BinaryOperator operator) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.operator = operator;
		if (leftExpression != null)
			getSentences().addAll(leftExpression.getSentences());
		if (rightExpression != null)
			getSentences().addAll(rightExpression.getSentences());
	}

	public Expression getLeftExpression() { return leftExpression; }
	public void setLeftExpression(Expression leftExpression) {
		this.leftExpression = leftExpression;
		if (leftExpression != null)
			getSentences().addAll(leftExpression.getSentences());
	}

	public Expression getRightExpression() { return rightExpression; }
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
		if (rightExpression != null)
			getSentences().addAll(rightExpression.getSentences());
	}

	public BinaryOperator getOperator() { return operator; }
	public void setOperator(BinaryOperator operator) { this.operator = operator; }

	@Override
	public boolean evaluate(HashMap<Character, Boolean> truthValues) {
		boolean leftEvaluate = leftExpression.evaluate(truthValues), rightEvaluate = rightExpression.evaluate(truthValues);
		switch (operator) {
			case DISJUNCTION:
				return leftEvaluate | rightEvaluate;
			case CONJUNCTION:
				return leftEvaluate & rightEvaluate;
			case CONDITIONAL:
				return !leftEvaluate || rightEvaluate;
			case BICONDITIONAL:
				return leftEvaluate == rightEvaluate;
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + leftExpression.toString() + " " + operator.toString() + " " + rightExpression.toString() + ")";
	}
}
