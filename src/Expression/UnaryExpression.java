package Expression;

import Operator.UnaryOperator;

public class UnaryExpression extends Expression {

	private Expression expression;
	private UnaryOperator operator;

	public UnaryExpression(Expression expression, UnaryOperator operator) {
		this.expression = expression;
		this.operator = operator;
	}

	public Expression getExpression() { return expression; }
	public void setExpression(Expression expression) { this.expression = expression; }

	public UnaryOperator getOperator() { return operator; }
	public void setOperator(UnaryOperator operator) { this.operator = operator; }

}
