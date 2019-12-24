package Expression;

import Operator.Operator;

import java.util.HashMap;

public class BinaryExpression extends Expression {

	private Expression leftExpression, rightExpression;
	private Operator operator;

	public BinaryExpression(Expression leftExpression, Expression rightExpression, Operator operator, int negation) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.operator = operator;
		setNegation(negation);
		if (leftExpression != null)
			getSentences().addAll(leftExpression.getSentences());
		if (rightExpression != null)
			getSentences().addAll(rightExpression.getSentences());
	}

	public BinaryExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		this(leftExpression, rightExpression, operator, 0);
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

	public Operator getOperator() { return operator; }
	public void setOperator(Operator operator) { this.operator = operator; }

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
		return "~".repeat(Math.max(0, getNegation())) +
				"(" + leftExpression.toString() + " " + operator.toString() + " " + rightExpression.toString() + ")";
	}

	@Override
	protected BinaryExpression clone() {
		return new BinaryExpression(leftExpression.clone(), rightExpression.clone(), operator, getNegation());
	}
}
