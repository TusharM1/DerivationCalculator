import Expression.Expression;

import java.util.HashMap;

public class Main {

	public static void main(String[] args) {

		System.out.println("Derivation Calculator");

		Expression.printExpression(Expression.generateExpressionTree("A"));
		Expression.printExpression(Expression.generateExpressionTree("~A"));
		Expression.printExpression(Expression.generateExpressionTree("A & B"));
		Expression.printExpression(Expression.generateExpressionTree("A | B"));
		Expression.printExpression(Expression.generateExpressionTree("A -> B"));
		Expression.printExpression(Expression.generateExpressionTree("A = B"));

		Expression.printExpression(Expression.generateExpressionTree("(A)"));
		Expression.printExpression(Expression.generateExpressionTree("((A))"));
		Expression.printExpression(Expression.generateExpressionTree("(((A)))"));
		Expression.printExpression(Expression.generateExpressionTree("~(A)"));
		Expression.printExpression(Expression.generateExpressionTree("~((A))"));
		Expression.printExpression(Expression.generateExpressionTree("(~(A))"));
		Expression.printExpression(Expression.generateExpressionTree("~(~(A))"));
		Expression.printExpression(Expression.generateExpressionTree("~(~(~A))"));
		Expression.printExpression(Expression.generateExpressionTree("(((A)))"));
		Expression.printExpression(Expression.generateExpressionTree("~(((A)))"));

		Expression.printExpression(Expression.generateExpressionTree("(~A)"));
		Expression.printExpression(Expression.generateExpressionTree("~(A & B)"));
		Expression.printExpression(Expression.generateExpressionTree("~(A | B)"));
		Expression.printExpression(Expression.generateExpressionTree("~(A -> B)"));
		Expression.printExpression(Expression.generateExpressionTree("~~(A = B)"));


		Expression.printExpression(Expression.generateExpressionTree("~~(A = B)"));

		Expression.printExpression(Expression.generateExpressionTree("X -> (A = (B -> L))"));
		Expression.printExpression(Expression.generateExpressionTree("(X ^ B) = ~A"));
		Expression.printExpression(Expression.generateExpressionTree("(~B -> ~A) ^ (B -> X)"));
		Expression.printExpression(Expression.generateExpressionTree("~L"));

		HashMap<Character, Boolean> truthValues = new HashMap<>();
		truthValues.put('X', true);
		truthValues.put('A', false);
		truthValues.put('B', true);
		truthValues.put('L', false);

		System.out.println(Expression.evaluateExpression(Expression.generateExpressionTree("X -> (A = (B -> L))"), truthValues));

	}

}
