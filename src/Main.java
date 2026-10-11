//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.sql.Time;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        // Lecturers
        Lecturer l1 = new Lecturer("L1", "Dr. Smith");
        Lecturer l2 = new Lecturer("L2", "Dr. Jones");
        Lecturer l3 = new Lecturer("L3", "Dr. Patel");
        Lecturer l4 = new Lecturer("L4", "Dr. Nguyen");
        Lecturer l5 = new Lecturer("L5", "Dr. Oduya");

        // Courses — l1 teaches c1 & c6, l2 teaches c2 & c7, to test lecturer-clash logic
        Course c1 = new Course("C1", "Algorithms", l1, 25, 30);
        Course c2 = new Course("C2", "Databases", l2, 20, 25);
        Course c3 = new Course("C3", "AI Fundamentals", l3, 15, 20);
        Course c4 = new Course("C4", "Networks", l4, 30, 30);
        Course c5 = new Course("C5", "Software Engineering", l5, 18, 20);
        Course c6 = new Course("C6", "Operating Systems", l1, 22, 25);
        Course c7 = new Course("C7", "Web Development", l2, 20, 20);
        Course c8 = new Course("C8", "Machine Learning", l3, 12, 15);
        Course c9 = new Course("C9", "Cybersecurity", l4, 16, 20);

        List<Course> allCourses = List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9);

        // Timeslots: 3 days x 3 periods = 9 slots
        List<TimeSlot> allTimeSlots = new ArrayList<>();
        for (Day day : Day.values()) {
            for (int period = 1; period <= 3; period++) {
                allTimeSlots.add(new TimeSlot(day, period));
            }
        }

        // Students — deliberate overlaps to exercise student-clash logic
        Student s1 = new Student("S1", List.of(c1, c2));       // Algorithms + Databases
        Student s2 = new Student("S2", List.of(c1, c2));       // same pair as s1
        Student s3 = new Student("S3", List.of(c4, c5));       // Networks + SWE
        Student s4 = new Student("S4", List.of(c3, c4));       // AI + Networks
        Student s5 = new Student("S5", List.of(c6, c7));       // OS + Web Dev
        Student s6 = new Student("S6", List.of(c6, c8));       // OS + ML
        Student s7 = new Student("S7", List.of(c7, c9));       // Web Dev + Cybersecurity
        Student s8 = new Student("S8", List.of(c8, c9));       // ML + Cybersecurity

        List<Student> allStudents = List.of(s1, s2, s3, s4, s5, s6, s7, s8);
        // Sanity check output
        System.out.println("Courses: " + allCourses);
        System.out.println("TimeSlots: " + allTimeSlots);
        System.out.println("Students: " + allStudents.size());
        System.out.println("Setup complete.\n");

        Map<Course, TimeSlot> finalAssignment = new HashMap<>();
        List<Course> unassigned = new ArrayList<>(allCourses);//mutable copy

        //Constraint Checks

        // Only one time slot available
        List<TimeSlot> oneSlot = List.of(new TimeSlot(Day.MON, 1));

        // Test 1: Lecturer clash — C1 and C6 share lecturer L1
        System.out.println("Lecturer clash: " + Scheduler.solve(new ArrayList<>(List.of(c1, c6)), oneSlot, new HashMap<>(), allStudents));

        // Test 2: Student clash — S1 takes both C1 and C2
        System.out.println("Student clash: " + Scheduler.solve(new ArrayList<>(List.of(c1, c2)), oneSlot, new HashMap<>(), allStudents));

        boolean success = Scheduler.solve(unassigned,allTimeSlots,finalAssignment,allStudents);

        if(success){
            System.out.println("\nTimetable found:");
            for(Map.Entry<Course, TimeSlot> entry : finalAssignment.entrySet()){
                System.out.println(entry.getKey()+" --> "+entry.getValue());
            }


            System.out.println("\nScore before optimisation: "+Scheduler.score(finalAssignment));


            Scheduler.improve(finalAssignment,allStudents,allTimeSlots,1000);


            System.out.println("Score after optimisation: "+Scheduler.score(finalAssignment));
            System.out.println("\nOptimised timetable:");
            for (Map.Entry<Course, TimeSlot>entry:finalAssignment.entrySet()){
                System.out.println(entry.getKey()+" --> "+entry.getValue());
            }


            System.out.println("\nFinal optimised timetable: ");
            Scheduler.printGrid(finalAssignment,allTimeSlots);
        }
        else{
            System.out.println("Timetable not found.");
        }


    }


}

