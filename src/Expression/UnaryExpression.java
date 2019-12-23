package Expression;

import Operator.UnaryOperator;

public class UnaryExpression extends Expression {

	private UnaryOperator operator;
	private Expression expression;

	public UnaryExpression(UnaryOperator operator, Expression expression) {
		this.operator = operator;
		this.expression = expression;
	}

	public UnaryOperator getOperator() { return operator; }
	public void setOperator(UnaryOperator operator) { this.operator = operator; }

	public Expression getExpression() { return expression; }
	public void setExpression(Expression expression) { this.expression = expression; }

}
