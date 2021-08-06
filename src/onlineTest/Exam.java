package onlineTest;

public class Exam {
	private int examId;
	private String title;
	
	public Exam(int examId, String title) {
		this.examId = examId;
		this.title = title;
	}
	
	public Exam(int examId) {
		this(examId, "");
	}
	
	public int getId() {
		return examId;
	}
	
	public String getTitle() {
		return title;
	}
	
	@Override // exams are equal if they have the same id
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Exam)) {
			return false;
		}
		
		Exam exam = (Exam) obj;
		
		if(examId == exam.examId) {
			return true;
		}
		
		return false;
	}
	
	@Override // when two exams are equal, they will have the same hashCode
	public int hashCode() {
		return examId;
	}
}
