package Expression;

import java.util.HashMap;

public class SimpleExpression extends Expression {

	private char sentence;

	// TODO save references in truthValues of actual SimpleExpressions so they can be referenced multiple times and changed easily

	public SimpleExpression(char sentence, boolean negation) {
		this.sentence = sentence;
		setNegation(negation);
		getSentences().add(sentence);
	}

	public SimpleExpression(char sentence) {
		this(sentence, false);
	}

	public char getSentence() { return sentence; }
	public void setSentence(char sentence) { this.sentence = sentence; }

	@Override
	public boolean evaluate(HashMap<Character, Boolean> truthValues) {
		if (truthValues.containsKey(sentence))
			return truthValues.get(sentence);
		return false;
	}

	@Override
	public String toString() {
		if (isNegated())
			return "~" + sentence;
		return String.valueOf(sentence);
	}

	@Override
	protected SimpleExpression clone() {
		return new SimpleExpression(sentence);
	}
}
