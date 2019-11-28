package Expression;

import Sentence.Sentence;

public class SimpleExpression extends Expression {

	private Sentence sentence;

	public SimpleExpression(Sentence sentence) {
		this.sentence = sentence;
	}

	public Sentence getSentence() { return sentence; }
	public void setSentence(Sentence sentence) { this.sentence = sentence; }

}
