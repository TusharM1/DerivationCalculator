import Expression.Expression;

import java.util.HashMap;

public class Main {

	public static void main(String[] args) {

		System.out.println("Derivation Calculator");

		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("A")));
		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("A & B")));
		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("A | B")));
		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("A -> B")));
		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("A = B")));
		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("A & !A")));

		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("X -> (A = (B -> L))")));
		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("(X ^ B) = ~A")));
		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("(~B -> ~A) ^ (B -> X)")));
		System.out.println(Expression.findTruthValues(Expression.generateExpressionTree("~L")));

		System.out.println(Expression.validateArgument(new String[]{"X -> (A = (B -> L))", "(X ^ B) = ~A", "(~B -> ~A) ^ (B -> X)"}, "~L"));
		System.out.println(Expression.validateArgument(new String[]{"X -> (A = (B -> L))", "(X ^ B) = ~A", "(~B -> ~A) ^ (B -> X)"}, "L"));

//		Expression.printExpression(Expression.generateExpressionTree("A"));
//		Expression.printExpression(Expression.generateExpressionTree("~A"));
//		Expression.printExpression(Expression.generateExpressionTree("A & B"));
//		Expression.printExpression(Expression.generateExpressionTree("A | B"));
//		Expression.printExpression(Expression.generateExpressionTree("A -> B"));
//		Expression.printExpression(Expression.generateExpressionTree("A = B"));
//
//		Expression.printExpression(Expression.generateExpressionTree("(A)"));
//		Expression.printExpression(Expression.generateExpressionTree("((A))"));
//		Expression.printExpression(Expression.generateExpressionTree("(((A)))"));
//		Expression.printExpression(Expression.generateExpressionTree("~(A)"));
//		Expression.printExpression(Expression.generateExpressionTree("~((A))"));
//		Expression.printExpression(Expression.generateExpressionTree("(~(A))"));
//		Expression.printExpression(Expression.generateExpressionTree("~(~(A))"));
//		Expression.printExpression(Expression.generateExpressionTree("~(~(~A))"));
//		Expression.printExpression(Expression.generateExpressionTree("(((A)))"));
//		Expression.printExpression(Expression.generateExpressionTree("~(((A)))"));
//
//		Expression.printExpression(Expression.generateExpressionTree("(~A)"));
//		Expression.printExpression(Expression.generateExpressionTree("~(A & B)"));
//		Expression.printExpression(Expression.generateExpressionTree("~(A | B)"));
//		Expression.printExpression(Expression.generateExpressionTree("~(A -> B)"));
//		Expression.printExpression(Expression.generateExpressionTree("~~(A = B)"));
//
//
//		Expression.printExpression(Expression.generateExpressionTree("~~(A = B)"));

//		Expression.printExpression(Expression.generateExpressionTree("X -> (A = (B -> L))"));
//		Expression.printExpression(Expression.expand(Expression.generateExpressionTree("X -> (A = (B -> L))")));
//		System.out.println();
//
//		Expression.printExpression(Expression.generateExpressionTree("(X ^ B) = ~A"));
//		Expression.printExpression(Expression.expand(Expression.generateExpressionTree("(X ^ B) = ~A")));
//		System.out.println();
//
//		Expression.printExpression(Expression.generateExpressionTree("(~B -> ~A) ^ (B -> X)"));
//		Expression.printExpression(Expression.expand(Expression.generateExpressionTree("(~B -> ~A) ^ (B -> X)")));
//		System.out.println();
//
//		Expression.printExpression(Expression.generateExpressionTree("~L"));
//		Expression.printExpression(Expression.expand(Expression.generateExpressionTree("~L")));
//		System.out.println();

//		HashMap<Character, Boolean> truthValues = new HashMap<>();
//		truthValues.put('X', true);
//		truthValues.put('A', false);
//		truthValues.put('B', true);
//		truthValues.put('L', false);
//
//		System.out.println(Expression.evaluateExpression(Expression.generateExpressionTree("X -> (A = (B -> L))"), truthValues));

	}

}
