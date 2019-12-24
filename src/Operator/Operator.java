package Operator;

public enum Operator {

	DISJUNCTION("∨"),
	CONJUNCTION("∧"),
	CONDITIONAL("->"),
	BICONDITIONAL("=");

	private String operator;
	Operator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return operator;
	}

}
