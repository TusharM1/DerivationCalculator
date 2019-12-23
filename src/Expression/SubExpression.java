package Expression;

public class SubExpression extends Expression {

	private Expression expression;

	public SubExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "(" + expression.toString() + ")";
	}
}
