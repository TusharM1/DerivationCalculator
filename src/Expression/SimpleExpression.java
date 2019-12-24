package Expression;

import java.util.HashMap;

public class SimpleExpression extends Expression {

	private char sentence;

	public SimpleExpression(char sentence) {
		this.sentence = sentence;
		getSentences().add(sentence);
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
}
