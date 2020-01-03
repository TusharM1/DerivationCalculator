package Expression;

import java.util.HashMap;

public class ComplexExpression extends Expression {

	private Expression leftExpression, rightExpression;
	private Operator operator;

	public enum Operator {

		DISJUNCTION("∨"),
		CONJUNCTION("∧"),
		CONDITIONAL("->"),
		BICONDITIONAL("=");

		private String operator;
		Operator(String operator) {
			this.operator = operator;
		}

		@Override
		public String toString() {
			return operator;
		}

	}

	public ComplexExpression(Expression leftExpression, Expression rightExpression, Operator operator, int negation) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.operator = operator;
		setNegation(negation);
		if (leftExpression != null)
			getSentences().addAll(leftExpression.getSentences());
		if (rightExpression != null)
			getSentences().addAll(rightExpression.getSentences());
	}

	public ComplexExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
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
	protected ComplexExpression clone() {
		return new ComplexExpression(leftExpression.clone(), rightExpression.clone(), operator, getNegation());
	}
}
