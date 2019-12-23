import Expression.Expression;

public class Main {

	public static void main(String[] args) {

		System.out.println("Derivation Calculator");

		Expression.printExpressionTree(Expression.generateExpressionTree("X -> (A = (B -> L))"));
		Expression.printExpressionTree(Expression.generateExpressionTree("(X ^ B) = ~A"));
		Expression.printExpressionTree(Expression.generateExpressionTree("(~B -> ~A) ^ (B -> X)"));

	}

}
