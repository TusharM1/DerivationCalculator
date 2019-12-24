package Expression;

import java.util.HashMap;

public class SubExpression extends Expression {

	private Expression expression;

	public SubExpression(Expression expression) {
		this.expression = expression;
		getSentences().addAll(expression.getSentences());
	}

	@Override
	public boolean evaluate(HashMap<Character, Boolean> truthValues) {
		return expression.evaluate(truthValues);
	}

	@Override
	public String toString() {
		return "(" + expression.toString() + ")";
	}
}
