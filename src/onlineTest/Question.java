package onlineTest;

public class Question implements Comparable<Question>{
	private int questionNumber;
	private String text;
	private double points;
	
	public Question(int questionNumber, String text, double points) {
		this.questionNumber = questionNumber;
		this.text = text;
		this.points = points;
	}
	
	public int getQuestionNumber() {
		return questionNumber;
	}
	
	public String getText() {
		return text;
	}
	
	public double getPoints() {
		return points;
	}
	
	public void setPoints(double points) {
		this.points = points;
	}
	
	public void addPoints(double delta) {
		points += delta;
	}
	
	@Override // sorted based on questionNumber
	public int compareTo(Question question) {
		return questionNumber - question.questionNumber;
	}
	
	@Override // two questions are equal if they have the same questionNumber
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Question)) {
			return false;
		}
		
		Question question = (Question) obj;
		
		if(questionNumber == question.questionNumber) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		String message = "Question Text: " + text;
		message += "\nPoints: " + points;
		
		return message;
	}
	
}
