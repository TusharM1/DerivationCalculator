package Expression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

import Expression.ComplexExpression.Operator;

public abstract class Expression {

	// EXPRESSION SUBCLASSES

	private HashSet<Character> sentences;
	private int negation;

	public Expression() {
		sentences = new HashSet<>();
		negation = 0;
	}

	public abstract boolean evaluate(HashMap<Character, Boolean> truthValues);

	public HashSet<Character> getSentences() {
		return sentences;
	}
	public void setSentences(HashSet<Character> sentences) {
		this.sentences = sentences;
	}

	public boolean isNegated() {
		return negation % 2 != 0;
	}
	public int getNegation() {
		return negation;
	}
	public void setNegation(int negation) {
		this.negation = negation;
	}

	// GENERATE EXPRESSION TREE

	public static Expression generateExpressionTree(String expression) throws IllegalArgumentException {
		// TODO add operator precedence & over |
		// TODO left to right precedence A ^ B ^ C => (A ^ B) ^ C
		try {
			Expression root = null;
			int index = 0;
			while (index < expression.length()) {
				if (expression.charAt(index) == ' ') {
					index++;
					continue;
				}
				if (expression.charAt(index) == '(') {
					index++;
					int previousIndex = index;
					int count = 1;
					while (index < expression.length() && count != 0) {
						if (expression.charAt(index) == '(')
							count++;
						else if (expression.charAt(index) == ')')
							count--;
						index++;
					}
					root = addExpressionToRoot(root, generateExpressionTree(expression.substring(previousIndex, index - 1)));
					continue;
				}
				if ("∧^∨⊃≡˜·*+→↔¬&|!=~".contains(String.valueOf(expression.charAt(index)))) {
					if ("∧^·*&".contains(String.valueOf(expression.charAt(index))))
						root = new ComplexExpression(root, null, Operator.CONJUNCTION);
					else if ("∨+|".contains(String.valueOf(expression.charAt(index))))
						root = new ComplexExpression(root, null, Operator.DISJUNCTION);
					else if ("⊃→".contains(String.valueOf(expression.charAt(index))))
						root = new ComplexExpression(root, null, Operator.CONDITIONAL);
					else if ("≡↔=".contains(String.valueOf(expression.charAt(index))))
						root = new ComplexExpression(root, null, Operator.BICONDITIONAL);
					else if ("˜¬!~".contains(String.valueOf(expression.charAt(index)))) {
						// TODO add XOR
//						if (expression.charAt(index) == '!' && expression.charAt(index + 1) == '=') {
//							index += 2;
//						}
						index++;
						Expression subExpression = generateExpressionTree(expression.substring(index, index = findSubExpressionIndex(expression, index)));
						subExpression.negation++;
						root = addExpressionToRoot(root, subExpression);
						continue;
					}
					index++;
					if ("&|=".contains(String.valueOf(expression.charAt(index))))
						index++;
					continue;
				}
				else if (expression.charAt(index) == '-' && expression.charAt(index + 1) == '>') {
					root = new ComplexExpression(root, null, Operator.CONDITIONAL);
					index+=2;
					continue;
				}
				if (expression.charAt(index) >= 'A' && expression.charAt(index) <= 'Z') {
					root = addExpressionToRoot(root, new SimpleExpression(expression.charAt(index)));
					index++;
				}
			}
			return root;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Incorrectly formatted String");
		}
	}

	private static Expression addExpressionToRoot(Expression root, Expression expression) throws Exception {
		if (root == null)
			return expression;
		ComplexExpression complexExpression = (ComplexExpression) root;
		if (complexExpression.getLeftExpression() == null)
			complexExpression.setLeftExpression(expression);
		else if (complexExpression.getRightExpression() == null) {
			complexExpression.setRightExpression(expression);
			complexExpression.getSentences().addAll(complexExpression.getRightExpression().getSentences());
		}
		else
			throw new Exception("Ill-formatted Input");
		complexExpression.getSentences().addAll(complexExpression.getLeftExpression().getSentences());
		return complexExpression;
	}

	private static int findSubExpressionIndex(String expression, int startingIndex) {
		int count = 0;
		while (startingIndex < expression.length()) {
			if (("∧^∨⊃≡·*+→↔&|=".contains(String.valueOf(expression.charAt(startingIndex))) ||
					expression.charAt(startingIndex) == '-' && expression.charAt(startingIndex + 1) == '>') && count == 0)
				break;
			if (expression.charAt(startingIndex) == '(')
				count++;
			else if (expression.charAt(startingIndex) == ')')
				count--;
			startingIndex++;
		}
		return startingIndex;
	}

	// DERIVE CONCLUSION

	public static void derivation(Expression[] premises, Expression conclusion) { }

	// VALIDATE ARGUMENT

	public static boolean validateArgument(String[] premises, String conclusion) {
		Expression[] expressionPremises = new Expression[premises.length];
		for (int i = 0; i < expressionPremises.length; i++)
			expressionPremises[i] = generateExpressionTree(premises[i]);
		return validateArgument(expressionPremises, generateExpressionTree(conclusion));
	}

	public static boolean validateArgument(Expression[] premises, Expression conclusion) {
		conclusion = conclusion.clone();
		conclusion.negation++;
		ComplexExpression root = new ComplexExpression(null, conclusion, Operator.CONJUNCTION);
		ComplexExpression current = root;
		for (int i = 0; i < premises.length - 1; i++) {
			ComplexExpression complexExpression = new ComplexExpression(null, premises[i], Operator.CONJUNCTION);
			current.setLeftExpression(complexExpression);
			current = complexExpression;
		}
		current.setLeftExpression(premises[premises.length - 1]);
		return findTruthValues(root).isEmpty();
	}

	public static HashSet<HashMap<Character, Boolean>> findTruthValues(String expression) {
		return generateTruthValuesSet(generateExpressionTree(expression));
	}

	public static HashSet<HashMap<Character, Boolean>> findTruthValues(Expression expression) {
		return generateTruthValuesSet(expand(expression));
	}

	private static HashSet<HashMap<Character, Boolean>> generateTruthValuesSet(Expression expression) {
		if (expression instanceof ComplexExpression) {
			ComplexExpression complexExpression = (ComplexExpression) expression;
			if (complexExpression.getOperator() == Operator.CONJUNCTION) {
				HashSet<HashMap<Character, Boolean>> leftTruthValues = generateTruthValuesSet(complexExpression.getLeftExpression());
				HashSet<HashMap<Character, Boolean>> rightTruthValues = generateTruthValuesSet(complexExpression.getRightExpression());
				HashSet<HashMap<Character, Boolean>> combinedTruthValues = new HashSet<>();
				for (HashMap<Character, Boolean> leftHashMap : leftTruthValues) {
					for (HashMap<Character, Boolean> rightHashMap : rightTruthValues) {
						//noinspection unchecked
						HashMap<Character, Boolean> leftHashMapClone = (HashMap<Character, Boolean>) leftHashMap.clone();
						AtomicBoolean valid = new AtomicBoolean(true);
						rightHashMap.forEach((key, value) -> leftHashMapClone.merge(key, value, (leftValue, rightValue) -> {
							if (leftValue != rightValue)
								valid.set(false);
							return value;
						}));
						if (valid.get())
							combinedTruthValues.add(leftHashMapClone);
					}
				}
				return combinedTruthValues;
			}
			if (complexExpression.getOperator() == Operator.DISJUNCTION) {
				HashSet<HashMap<Character, Boolean>> leftTruthValues = generateTruthValuesSet(complexExpression.getLeftExpression());
				HashSet<HashMap<Character, Boolean>> rightTruthValues = generateTruthValuesSet(complexExpression.getRightExpression());
				leftTruthValues.addAll(rightTruthValues);
				return leftTruthValues;
			}
		}
		if (expression instanceof SimpleExpression) {
			SimpleExpression simpleExpression = (SimpleExpression) expression;
			HashSet<HashMap<Character, Boolean>> singletonHashSet = new HashSet<>();
			HashMap<Character, Boolean> hashMap = new HashMap<>();
			hashMap.put(simpleExpression.getSentence(), !simpleExpression.isNegated());
			singletonHashSet.add(hashMap);
			return singletonHashSet;
		}
		return new HashSet<>();
	}

	// EVALUATE EXPRESSION FOR GIVEN TRUTH VALUES

	public static boolean evaluateExpression(Expression expressionTree, HashMap<Character, Boolean> truthValues) {
		return expressionTree.evaluate(truthValues);
	}

	// REMOVE DOUBLE NEGATIVES

	public static Expression removeDoubleNegatives(Expression expression) {
		expression.negation %= 2;
		return expression;
	}

	public static Expression removeAllDoubleNegatives(Expression expression) {
		if (expression.negation > 1)
			expression.negation %= 2;
		if (expression instanceof ComplexExpression) {
			removeAllDoubleNegatives(((ComplexExpression) expression).getLeftExpression());
			removeAllDoubleNegatives(((ComplexExpression) expression).getRightExpression());
		}
		return expression;
	}

	// EXPAND EXPRESSION

	public static Expression expand(Expression expression) {
		if (expression == null)
			return null;
		expression = expression.clone();
		return expandExpressionTree(expression);
	}

	public static Expression expandExpressionTree(Expression expression) {
		if (expression == null)
			return null;
		expression = expandExpression(expression);
		if (expression instanceof ComplexExpression) {
			ComplexExpression complexExpression = (ComplexExpression) expression;
			complexExpression.setLeftExpression(removeDoubleNegatives(expandExpressionTree(complexExpression.getLeftExpression())));
			complexExpression.setRightExpression(removeDoubleNegatives(expandExpressionTree(complexExpression.getRightExpression())));
		}
		return expression;
	}

	private static Expression expandExpression(Expression expression) {
		if (expression instanceof ComplexExpression) {
			ComplexExpression complexExpression = (ComplexExpression) expression;
			removeDoubleNegatives(complexExpression);
			switch (complexExpression.getOperator()) {
				case DISJUNCTION:
					if (expression.isNegated()) {
						complexExpression.setOperator(Operator.CONJUNCTION);
						complexExpression.getLeftExpression().negation++;
						complexExpression.getRightExpression().negation++;
						expression.negation--;
					}
					break;
				case CONJUNCTION:
					if (expression.isNegated()) {
						complexExpression.setOperator(Operator.DISJUNCTION);
						complexExpression.getLeftExpression().negation++;
						complexExpression.getRightExpression().negation++;
						expression.negation--;
					}
					break;
				case CONDITIONAL:
					if (expression.isNegated()) {
						complexExpression.setOperator(Operator.CONJUNCTION);
						complexExpression.getRightExpression().negation++;
						expression.negation--;
					}
					else {
						complexExpression.setOperator(Operator.DISJUNCTION);
						complexExpression.getLeftExpression().negation++;
					}
					break;
				case BICONDITIONAL:
					ComplexExpression biconditional = new ComplexExpression(new ComplexExpression(complexExpression.getLeftExpression(), complexExpression.getRightExpression(), Operator.CONJUNCTION),
							new ComplexExpression(complexExpression.getLeftExpression().clone(), complexExpression.getRightExpression().clone(), Operator.CONJUNCTION), Operator.DISJUNCTION);
					((ComplexExpression) biconditional.getRightExpression()).getLeftExpression().negation++;
					if (expression.isNegated())
						((ComplexExpression) biconditional.getLeftExpression()).getRightExpression().negation++;
					else
						((ComplexExpression) biconditional.getRightExpression()).getRightExpression().negation++;
					expression = biconditional;
					break;
			}
		}
		return removeDoubleNegatives(expression);
	}

	// CHECK IF TWO EXPRESSION TREES ARE EQUIVALENT

	public static boolean checkEquivalence(Expression firstExpression, Expression secondExpression) {
		firstExpression = expand(firstExpression);
		secondExpression = expand(secondExpression);
		if (firstExpression == null || secondExpression == null)
			return false;
		// really lazy implementation
		// TODO fix this implementation later
		return firstExpression.toString().equals(secondExpression.toString());
	}

	// PRINT EXPRESSION TREE

	public static String expressionToString(Expression expression) {
		if (expression == null)
			return "";
		if (expression instanceof ComplexExpression) {
			ComplexExpression complexExpression = (ComplexExpression) expression;
			return complexExpression.getLeftExpression().toString() + " " +
					complexExpression.getOperator().toString() + " " +
					complexExpression.getRightExpression().toString();
		}
		return expression.toString();
	}

	public static void printExpression(Expression expression) {
		System.out.println(expressionToString(expression));
	}

	@Override
	protected Expression clone() {
		if (this instanceof ComplexExpression)
			return ((ComplexExpression) this).clone();
		if (this instanceof SimpleExpression)
			return ((SimpleExpression) this).clone();
		try {
			return (Expression) super.clone();
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		return checkEquivalence(this, (Expression) object);
	}

}
