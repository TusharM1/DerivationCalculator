package Expression;

import Operator.UnaryOperator;

import java.util.HashMap;

public class UnaryExpression extends Expression {

	private UnaryOperator operator;
	private Expression expression;

	public UnaryExpression(UnaryOperator operator, Expression expression) {
		this.operator = operator;
		this.expression = expression;
		getSentences().addAll(expression.getSentences());
	}

	public UnaryOperator getOperator() { return operator; }
	public void setOperator(UnaryOperator operator) { this.operator = operator; }

	public Expression getExpression() { return expression; }
	public void setExpression(Expression expression) { this.expression = expression; }

	@Override
	public boolean evaluate(HashMap<Character, Boolean> truthValues) {
		return (operator == UnaryOperator.NEGATION) != expression.evaluate(truthValues);
	}

	@Override
	public String toString() {
		return "~" + expression.toString();
	}
}
