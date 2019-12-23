package Sentence;

public class Sentence {

	char sentenceLetter;

	public Sentence(char sentenceLetter) {
		this.sentenceLetter = sentenceLetter;
	}

	@Override
	public String toString() {
		return String.valueOf(sentenceLetter);
	}

}
