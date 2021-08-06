package cmdLineInterpreter;

import java.util.*;

import onlineTest.*;

/**
 * 
 * By running the main method of this class we will be able to
 * execute commands associated with the SystemManager.  This command
 * interpreter is text based. 
 *
 */
public class Interpreter {

	public static void main(String[] args) {
		SystemManager manager = new SystemManager();
		Scanner scanner = new Scanner(System.in);
		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Enter 1 to add a student\r\n" + 
					"Enter 2 to add an exam\r\n" + 
					"Enter 3 to add a true/false question\r\n" + 
					"Enter 4 to answer a true/false question\r\n" + 
					"Enter 5 to get the exam score for a student\r\n");
			
			int option = scanner.nextInt();	
			
			if(option == 1) {
				System.out.println("Enter student name: ");
				String name = scanner.next();
				
				manager.addStudent(name);
			} else if(option == 2) {
				System.out.println("Enter exam ID: ");
				int examId = scanner.nextInt();
				
				System.out.println("Enter exam title: ");
				String title = scanner.next();
				
				manager.addExam(examId, title);
			} else if(option == 3) {
				System.out.println("Enter exam ID: ");
				int examId = scanner.nextInt();
				
				System.out.println("Enter question number: ");
				int questionNumber = scanner.nextInt();
				
				scanner.nextLine();// resolves the problem with nextLine
				
				System.out.println("Enter question text: ");
				String text = scanner.nextLine();
				
				System.out.println("Enter question points: ");
				double points = scanner.nextDouble();
				
				System.out.println("Enter question answer: ");
				boolean answer = scanner.nextBoolean();
				
				manager.addTrueFalseQuestion(examId, questionNumber, text, points, answer);
			} else if(option == 4) {
				System.out.println("Enter student name: ");
				String name = scanner.next();
				
				System.out.println("Enter exam ID: ");
				int examId = scanner.nextInt();
				
				System.out.println("Enter question number: ");
				int questionNumber = scanner.nextInt();
				
				System.out.println("Enter question answer: ");
				boolean answer = scanner.nextBoolean();
				
				manager.answerTrueFalseQuestion(name, examId, questionNumber, answer);
			} else if(option == 5) {
				System.out.println("Enter student name: ");
				String name = scanner.next();
				
				System.out.println("Enter exam ID: ");
				int examId = scanner.nextInt();
				
				System.out.println(manager.getExamScore(name, examId));
			}
			
			System.out.println("Enter 0 to return to the menu\nEnter 1 to exit\n");
			if(scanner.nextInt() == 1) {
				exit = true;
			}
			
		}
		
		scanner.close();
	}
}
