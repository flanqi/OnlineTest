package onlineTest;

import java.util.*;

public class FillInTheBlanksQuestion extends Question {
	private String[] answer;

	public FillInTheBlanksQuestion(int questionNumber, String text, double points, String[] answer) {
		super(questionNumber, text, points);
		this.answer = answer;
	}
	
	
	// this constructor is used when students answering questions
	public FillInTheBlanksQuestion(int questionNumber, String[] answer) {
		this(questionNumber, "", 0, answer);
	}
	
	// deep copy
	public FillInTheBlanksQuestion(FillInTheBlanksQuestion q) {
		this(q.getQuestionNumber(), q.getText(), q.getPoints(), q.answer);
		
		String[] answerCopy = new String[q.answer.length];
		
		for(int i = 0; i < q.answer.length; i++) {
			answerCopy[i] = q.answer[i];
		}
		
		setAnswer(answerCopy);
	}
	
	public String[] getAnswer() {
		return answer;
	}
	
	public void setAnswer(String[] answer) {
		this.answer = answer;
	}
	
	@Override
	public String toString() {
		String message = super.toString() + "\nCorrect Answer: ";
		ArrayList<String> answerCopy = new ArrayList<String>();
		
		for(String blank : answer) {
			answerCopy.add(blank);
		}
		
		Collections.sort(answerCopy);
		
		String answerText = "[";
		
		for(int i = 0; i < answerCopy.size() - 1; i++) {
			answerText += answerCopy.get(i) + ", ";
		}
		answerText += answerCopy.get(answer.length - 1) + "]";
		
		return message + answerText;
	}
}
