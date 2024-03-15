import java.util.*;

// Represents a Student
class Student {
    private String rollNumber;
    private String name;
    private Map<String, Integer> marks; // Subject -> Marks

    public Student(String rollNumber, String name) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.marks = new HashMap<>();
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getMarks() {
        return marks;
    }

    public void addMark(String subject, int mark) {
        marks.put(subject, mark);
    }

    public void updateMark(String subject, int mark) {
        if (marks.containsKey(subject)) {
            marks.put(subject, mark);
        } else {
            System.out.println("Subject not found for this student.");
        }
    }
}

// Represents the Central Database for Student Marks
class StudentMarksDatabase {
    public List<Student> students;

    public StudentMarksDatabase() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    // Sorts students by total marks
    public void sortStudentsByTotalMarks() {
        students.sort(Comparator.comparingInt(this::calculateTotalMarks).reversed());
    }

    // Calculates total marks for a student
    public int calculateTotalMarks(Student student) {
        int totalMarks = 0;
        for (int mark : student.getMarks().values()) {
            totalMarks += mark;
        }
        return totalMarks;
    }

    // Find student by roll number
    public Student findStudentByRollNumber(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }
}

// Represents a Subject Teacher
class SubjectTeacher {
    private String subject;

    public SubjectTeacher(String subject) {
        this.subject = subject;
    }

    // Allows the teacher to update marks for their subject
    public void updateMarks(Student student, int mark) {
        student.updateMark(subject, mark);
    }

    // Allows the teacher to add a new student
    public void addStudent(StudentMarksDatabase database, String rollNumber, String name) {
        Student newStudent = new Student(rollNumber, name);
        database.addStudent(newStudent);
        System.out.println("Student added successfully.");
    }
}

class StudentMarksManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentMarksDatabase database = new StudentMarksDatabase();

        while (true) {
            System.out.print("Are you entering as a student or teacher? (student/teacher/exit): ");
            String userType = scanner.nextLine();

            if (userType.equalsIgnoreCase("student")) {
                System.out.print("Enter your roll number: ");
                String rollNumber = scanner.nextLine();
                Student student = database.findStudentByRollNumber(rollNumber);
                if (student != null) {
                    System.out.println("Marks for Student: " + student.getName() + " (Roll Number: " + student.getRollNumber() + ")");
                    for (Map.Entry<String, Integer> entry : student.getMarks().entrySet()) {
                        System.out.println("Subject: " + entry.getKey() + ", Marks: " + entry.getValue());
                    }
                } else {
                    System.out.println("Student not found.");
                }
            } else if (userType.equalsIgnoreCase("teacher")) {
                System.out.print("Enter your subject: ");
                String subject = scanner.nextLine();
                SubjectTeacher teacher = new SubjectTeacher(subject);
                System.out.print("Do you want to add a new student? (yes/no): ");
                String addNewStudent = scanner.nextLine();
                if (addNewStudent.equalsIgnoreCase("yes")) {
                    System.out.print("Enter student's roll number: ");
                    String rollNumber = scanner.nextLine();
                    System.out.print("Enter student's name: ");
                    String name = scanner.nextLine();
                    teacher.addStudent(database, rollNumber, name);
                }
                System.out.print("Enter student's roll number to update marks: ");
                String rollNumber = scanner.nextLine();
                Student student = database.findStudentByRollNumber(rollNumber);
                if (student != null) {
                    System.out.print("Enter marks for " + subject + ": ");
                    int marks = scanner.nextInt();
                    teacher.updateMarks(student, marks);
                    System.out.println("Marks updated successfully.");
                } else {
                    System.out.println("Student not found.");
                }
            } else if (userType.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Student Marks Management System...");
                break;
            } else {
                System.out.println("Invalid user type. Please enter as student, teacher, or exit.");
            }
        }

        scanner.close();
    }
}