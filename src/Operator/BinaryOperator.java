package Operator;

public enum BinaryOperator {

	DISJUNCTION(" | "),
	CONJUNCTION(" & "),
	CONDITIONAL(" -> "),
	BICONDITIONAL(" = ");

	private String operator;
	BinaryOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return operator;
	}

}
