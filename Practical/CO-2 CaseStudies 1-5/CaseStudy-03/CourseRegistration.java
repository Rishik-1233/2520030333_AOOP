
interface Registerable {
    void registerStudent(String studentName);
}

interface Assessable {
    void conductAssessment();
}

interface PrerequisiteCheckable {
    boolean checkPrerequisite(int marks);
}

abstract sealed class Course permits RegularCourse, LabCourse, ElectiveCourse, ResearchCourse {
    String courseName;

    Course(String courseName) {
        this.courseName = courseName;
    }

    abstract void displayCourse();
}

final class RegularCourse extends Course implements Registerable, Assessable {
    RegularCourse(String name) {
        super(name);
    }

    public void registerStudent(String studentName) {
        System.out.println(studentName + " registered for " + courseName);
    }

    public void conductAssessment() {
        System.out.println("Regular course assessment conducted");
    }

    void displayCourse() {
        System.out.println("Regular Course: " + courseName);
    }
}

final class LabCourse extends Course implements Registerable, Assessable {
    LabCourse(String name) {
        super(name);
    }

    public void registerStudent(String studentName) {
        System.out.println(studentName + " registered for " + courseName);
    }

    public void conductAssessment() {
        System.out.println("Practical lab assessment conducted");
    }

    void displayCourse() {
        System.out.println("Lab Course: " + courseName);
    }
}

final class ElectiveCourse extends Course implements Registerable, PrerequisiteCheckable {
    ElectiveCourse(String name) {
        super(name);
    }

    public void registerStudent(String studentName) {
        System.out.println(studentName + " registered for " + courseName);
    }

    public boolean checkPrerequisite(int marks) {
        return marks >= 40;
    }

    void displayCourse() {
        System.out.println("Elective Course: " + courseName);
    }
}

final class ResearchCourse extends Course implements Registerable, PrerequisiteCheckable {
    ResearchCourse(String name) {
        super(name);
    }

    public void registerStudent(String studentName) {
        System.out.println(studentName + " registered for " + courseName);
    }

    public boolean checkPrerequisite(int marks) {
        return marks >= 60;
    }

    void displayCourse() {
        System.out.println("Research Course: " + courseName);
    }
}

public class CourseRegistration {

    public static void main(String[] args) {

        RegularCourse regular = new RegularCourse("Java Programming");
        LabCourse lab = new LabCourse("Java Lab");
        ElectiveCourse elective = new ElectiveCourse("Artificial Intelligence");
        ResearchCourse research = new ResearchCourse("Machine Learning Research");

        regular.displayCourse();
        regular.registerStudent("Rishik");
        regular.conductAssessment();

        lab.displayCourse();
        lab.registerStudent("Rishik");
        lab.conductAssessment();

        elective.displayCourse();
        elective.registerStudent("Rishik");
        System.out.println("Elective Prerequisite: " + elective.checkPrerequisite(50));

        research.displayCourse();
        research.registerStudent("Rishik");
        System.out.println("Research Prerequisite: " + research.checkPrerequisite(70));
    }
}
