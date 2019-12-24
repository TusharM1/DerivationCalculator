import Expression.Expression;

import java.util.HashMap;

public class Main {

	public static void main(String[] args) {

		System.out.println("Derivation Calculator");

		Expression.printExpressionTree(Expression.generateExpressionTree("X -> (A = (B -> L))"));
		Expression.printExpressionTree(Expression.generateExpressionTree("(X ^ B) = ~A"));
		Expression.printExpressionTree(Expression.generateExpressionTree("(~B -> ~A) ^ (B -> X)"));
		Expression.printExpressionTree(Expression.generateExpressionTree("~L"));

		HashMap<Character, Boolean> truthValues = new HashMap<>();
		truthValues.put('X', true);
		truthValues.put('A', false);
		truthValues.put('B', true);
		truthValues.put('L', false);

		System.out.println(Expression.evaluateExpression(Expression.generateExpressionTree("X -> (A = (B -> L))"), truthValues));

	}

}
