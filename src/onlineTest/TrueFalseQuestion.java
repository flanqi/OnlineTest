package onlineTest;

public class TrueFalseQuestion extends Question{
	private boolean answer;
	
	public TrueFalseQuestion(int questionNumber, String text, double points, boolean answer) {
		super(questionNumber, text, points);
		this.answer = answer;
	}
	
	public TrueFalseQuestion(int questionNumber, boolean answer) {
		this(questionNumber, "", 0, answer);
	}
	
	// copy constructor, deep copy
	public TrueFalseQuestion(TrueFalseQuestion q) {
		this(q.getQuestionNumber(), q.getText(), q.getPoints(), q.answer);
	}
	
	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
	
	public boolean getAnswer() {
		return answer;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nCorrect Answer: " + ((answer)? "True":"False");
	}
}
