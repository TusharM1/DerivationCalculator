package Expression;

import java.util.HashMap;

public class UnaryExpression extends Expression {

	private Expression expression;

	public UnaryExpression(Expression expression, boolean negation) {
		this.expression = expression;
		if (expression != null)
			getSentences().addAll(expression.getSentences());
		setNegation(negation);
	}

	@Override
	public boolean evaluate(HashMap<Character, Boolean> truthValues) {
		return false;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		if (isNegated())
			return "~" + expression.toString();
		return expression.toString();
	}
}
