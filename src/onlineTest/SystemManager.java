package onlineTest;

import java.util.*;

public class SystemManager implements Manager {
	
	/* each exam is associated with its questions with corresponding
	 * correct answers of the questions;
	 * 
	 * each student is associated with the exams they took and the answers 
	 * they gave for each question.
	 * 
	 * each double in the cutoff is the lower bound for a certain letter grade
	 * 
	 * saved contains the saved managers and their associated titles
	 */
	Map<Exam, TreeSet<Question>> exams = new HashMap<>(); 
	Map<String, Map<Exam, TreeSet<Question>>> students = new HashMap<>(); 
	Map<Double, String> cutOff = new LinkedHashMap<>(); 
	Map<String, Manager> saved = new HashMap<>(); 
	
	@Override
	public boolean addExam(int examId, String title) {
		Exam exam = new Exam(examId, title);
		
		if(!exams.containsKey(exam)) {
			exams.put(exam, new TreeSet<Question>());
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		Exam exam = new Exam(examId);
		TrueFalseQuestion question = new TrueFalseQuestion(questionNumber, text, points, answer);
		
		if(exams.containsKey(exam)) {
			TreeSet<Question> questions = exams.get(exam);
			
			questions.add(question);
		
		}
		
	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		Exam exam = new Exam(examId);
		MultipleChoiceQuestion question = new MultipleChoiceQuestion(questionNumber, text, points, answer);
		
		if(exams.containsKey(exam)) {
			TreeSet<Question> questions = exams.get(exam);
			
			questions.add(question);
		}
		
	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		Exam exam = new Exam(examId);
		FillInTheBlanksQuestion question = new FillInTheBlanksQuestion(questionNumber, text, points, answer);
		
		if(exams.containsKey(exam)) {
			TreeSet<Question> questions = exams.get(exam);
			
			questions.add(question);
		}
		
	}
	
	@Override
	public String getKey(int examId) {
		String key = "";
		Exam exam = new Exam(examId);
		
		if(exams.containsKey(exam)) {
			TreeSet<Question> questions = exams.get(exam);
			
			for(Question q : questions) {
				key += q.toString()+ "\n";
			}
		}
		
		return key;
	}
	
	@Override
	public boolean addStudent(String name) {
		if(!students.containsKey(name)) {
			students.put(name, new HashMap<Exam, TreeSet<Question>>());
		}
		
		return false;
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		if(students.containsKey(studentName)) {
			Map<Exam, TreeSet<Question>> studentExams = students.get(studentName);
			Exam exam = new Exam(examId);
			
			if(!studentExams.containsKey(exam)) {
				studentExams.put(exam, new TreeSet<Question>());
			}
			
			TreeSet<Question> setOfQuestions = studentExams.get(exam);
			
			Object[] keys = exams.get(exam).toArray();
			TrueFalseQuestion key = (TrueFalseQuestion) keys[questionNumber - 1];
			
			TrueFalseQuestion studentAnswer = new TrueFalseQuestion(questionNumber, answer);
			
			setOfQuestions.add(studentAnswer);

			if(answer == key.getAnswer()) {
				studentAnswer.setPoints(key.getPoints());
			}
		}
		
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		if(students.containsKey(studentName)) {
			Map<Exam, TreeSet<Question>> studentExams = students.get(studentName);
			Exam exam = new Exam(examId);
			
			if(!studentExams.containsKey(exam)) {
				studentExams.put(exam, new TreeSet<Question>());
			}
			
			TreeSet<Question> setOfQuestions = studentExams.get(exam);
			
			Object[] keys = exams.get(exam).toArray();
			MultipleChoiceQuestion key = (MultipleChoiceQuestion) keys[questionNumber - 1];
			
			MultipleChoiceQuestion studentAnswer = new MultipleChoiceQuestion(questionNumber, answer);
			
			setOfQuestions.add(studentAnswer);
			
			if(correctMultipleChoice(answer, key.getAnswer())) {
				studentAnswer.setPoints(key.getPoints());
			} // otherwise, students points for the question is by default 0
		}
		
	}
	
	// check if the multiple choice question answer is correct
	private static boolean correctMultipleChoice(String[] answer, String[] key) {
		if(answer.length != key.length) {
			return false;
		} 
		
		for(int i = 0; i < key.length; i++) {
			if(!answer[i].equals(key[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		if(students.containsKey(studentName)) {
			Map<Exam, TreeSet<Question>> studentExams = students.get(studentName);
			Exam exam = new Exam(examId);
			
			if(!studentExams.containsKey(exam)) {
				studentExams.put(exam, new TreeSet<Question>());
			}
			
			TreeSet<Question> setOfQuestions = studentExams.get(exam);
			
			Object[] keys = exams.get(exam).toArray();
			FillInTheBlanksQuestion key = (FillInTheBlanksQuestion) keys[questionNumber - 1];
			
			FillInTheBlanksQuestion studentAnswer = new FillInTheBlanksQuestion(questionNumber, answer);
			
			setOfQuestions.add(studentAnswer);
			
			for(String ans : answer) {
				if(containAnswer(key.getAnswer(), ans)) {
					studentAnswer.addPoints(key.getPoints()/key.getAnswer().length);
				}
			}
		}
		
	}
	
	// check if a student's part of fill in the blanks answer is contained in the key
	private boolean containAnswer(String[] keys, String answer) {
		for(String key : keys) {
			if(key.equals(answer)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		if(students.containsKey(studentName)) {
			Map<Exam, TreeSet<Question>> studentExams = students.get(studentName);
			Exam exam = new Exam(examId);
			
			if(studentExams.containsKey(exam)) {
				TreeSet<Question> studentQuestions = studentExams.get(exam);
				double score = 0;
				
				for(Question q: studentQuestions) {
					score += q.getPoints();
				}
				
				return score;
			}
			
			return 0; // if the student did not take the exam, we return 0
		}
		
		return 0; // it there is no such student, we return 0
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		Map<Exam, TreeSet<Question>> studentExams = students.get(studentName);
		Exam exam = new Exam(examId);
		String report = "";
	
		TreeSet<Question> studentQuestions = studentExams.get(exam);
		TreeSet<Question> examQuestions = exams.get(exam);
		
		double studentScore = getExamScore(studentName, examId);
		int pos = 1;
		Iterator<Question> iter = examQuestions.iterator();
		
		for(Question q: studentQuestions) {
			report += "Question #" + pos + " " + q.getPoints();
			report += " points out of " +  iter.next().getPoints() + "\n";
			pos++;
		}
		
		report += "Final Score: " + studentScore +" out of " + examTotalScore(examQuestions);
		
		return report;
	}

	// computes the total score in a set of questions
	private double examTotalScore(Set<Question> set) {
		double totalScore = 0;
		
		for(Question q: set) {
			totalScore += q.getPoints();
		}
		
		return totalScore;
	}
	
	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		for(int i = 0; i < cutoffs.length; i++) {
			cutOff.put(cutoffs[i], letterGrades[i]);
		}
	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		Map<Exam, TreeSet<Question>> studentExams = students.get(studentName);
		double sum = 0;
		
		for(Exam exam: exams.keySet()) {
			double total = 0;
			double studentTotal = 0;
			total += examTotalScore(exams.get(exam));
			
			if(studentExams.containsKey(exam)) {
				studentTotal += getExamScore(studentName, exam.getId());
			}
			
			sum += (studentTotal/total)*100;
		}
		
		return sum/exams.size();
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		double numericGrade = getCourseNumericGrade(studentName);
		Iterator<Double> cutoffs = cutOff.keySet().iterator();
		Iterator<String> letterGrades = cutOff.values().iterator();
		
		while(cutoffs.hasNext()) {
			if(numericGrade >= cutoffs.next()) {
				return letterGrades.next();
			} else {
				letterGrades.next();
			}
			
		}
		
		return "F";
	}

	@Override
	public String getCourseGrades() {
		TreeSet<String> names = new TreeSet<>(students.keySet());
		String grades = "";
		
		for(String name : names) {
			grades += name + " ";
			grades += getCourseNumericGrade(name) + " ";
			grades += getCourseLetterGrade(name) + "\n";
		}
		
		return grades;
	}

	@Override
	public double getMaxScore(int examId) {
		ArrayList<Double> grades = new ArrayList<>();
		TreeSet<String> names = new TreeSet<>(students.keySet());
		
		for(String name : names) {
			grades.add(getExamScore(name, examId));
		}
		
		return Collections.max(grades);
	}

	@Override
	public double getMinScore(int examId) {
		ArrayList<Double> grades = new ArrayList<>();
		TreeSet<String> names = new TreeSet<>(students.keySet());
		
		for(String name : names) {
			grades.add(getExamScore(name, examId));
		}
		
		return Collections.min(grades);
	}

	@Override
	public double getAverageScore(int examId) {
		TreeSet<String> names = new TreeSet<>(students.keySet());
		double total = 0;
		
		for(String name : names) {
			total += getExamScore(name, examId);
		}
		
		return total/names.size();
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		saved.put(fileName, manager);
		
	}

	@Override
	public Manager restoreManager(String fileName) {
		return saved.get(fileName);
	}

}
