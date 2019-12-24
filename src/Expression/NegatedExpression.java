package Expression;

import java.util.HashMap;

public class NegatedExpression extends Expression {

	private Expression expression;

	public NegatedExpression(Expression expression) {
		this.expression = expression;
		if (expression != null)
			getSentences().addAll(expression.getSentences());
		setNegation(true);
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

	@Override
	protected NegatedExpression clone() {
		return new NegatedExpression(expression.clone());
	}
}
