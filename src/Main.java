import Expression.Expression;

public class Main {

	public static void main(String[] args) {

		System.out.println("Derivation Calculator");

		Expression expression = Expression.generateExpressionTree("X -> (A = (B -> L))");

		Expression.printExpressionTree(expression);

	}

}
