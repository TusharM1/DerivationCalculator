package Expression;

import Operator.BinaryOperator;

public class BinaryExpression extends Expression {

	private Expression leftExpression, rightExpression;
	private BinaryOperator operator;

	public BinaryExpression(Expression leftExpression, Expression rightExpression, BinaryOperator operator) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.operator = operator;
	}

	public Expression getLeftExpression() { return leftExpression; }
	public void setLeftExpression(Expression leftExpression) { this.leftExpression = leftExpression; }

	public Expression getRightExpression() { return rightExpression; }
	public void setRightExpression(Expression rightExpression) { this.rightExpression = rightExpression; }

	public BinaryOperator getOperator() { return operator; }
	public void setOperator(BinaryOperator operator) { this.operator = operator; }

}
