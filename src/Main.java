//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.sql.Time;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        //Lecturers
        Lecturer l1 = new Lecturer("L1", "Dr. Smith");
        Lecturer l2 = new Lecturer("L2", "Dr. Jones");
        Lecturer l3 = new Lecturer("L3", "Dr. Patel");

        // Courses — c1 and c3 share a lecturer (l1), to test lecturer-clash logic
        Course c1 = new Course("C1", "Algorithms", l1, 25, 30);
        Course c2 = new Course("C2", "Databases", l2, 20, 25);
        Course c3 = new Course("C3", "AI Fundamentals", l1, 15, 20);
        Course c4 = new Course("C4", "Networks", l3, 30, 30);
        Course c5 = new Course("C5", "Software Engineering", l2, 18, 20);

        List<Course> allCourses = List.of(c1, c2, c3, c4, c5);

        // Timeslots: 3 days x 3 periods = 9 slots
        List<TimeSlot> allTimeSlots = new ArrayList<>();
        for (Day day : Day.values()) {
            for (int period = 1; period <= 3; period++) {
                allTimeSlots.add(new TimeSlot(day, period));
            }
        }

        // Students — s1 and s2 both take (c1, c2), to test student-clash logic
        // s3 takes (c4, c5) — no overlap with anyone else, a "clean" case
        Student s1 = new Student("S1", List.of(c1, c2));
        Student s2 = new Student("S2", List.of(c1, c2));
        Student s3 = new Student("S3", List.of(c4, c5));
        Student s4 = new Student("S4", List.of(c3, c4));

        List<Student> allStudents = List.of(s1, s2, s3, s4);

        // Sanity check output
        System.out.println("Courses: " + allCourses);
        System.out.println("TimeSlots: " + allTimeSlots);
        System.out.println("Students: " + allStudents.size());
        System.out.println("Setup complete.");

        Map<Course, TimeSlot> testAssignment = new HashMap<>();
        TimeSlot mon1 = allTimeSlots.get(0); //MON P1

        testAssignment.put(c1, mon1);// c1 (lecturer l1) assigned to MON P1

        //Should be FALSE: c3 shares lecturer l1 with c1, same slot
        System.out.println("c3 same slot as c1(lecturer clash): "+Scheduler.isValid(c3,mon1,testAssignment,allStudents));

        //Should be FALSE: c2 shares student s1/s2 with c1, same slot
        System.out.println("c2 same slot as c1 (student clash): "+ Scheduler.isValid(c2,mon1,testAssignment,allStudents));

        //Should be TRUE: c4 shares nothing with c1
        System.out.println("c4 same slot as c1 (should be ok): " + Scheduler.isValid(c4,mon1,testAssignment,allStudents));

        Map<Course, TimeSlot> finalAssignment = new HashMap<>();
        List<Course> unassigned = new ArrayList<>(allCourses);//mutable copy

        boolean success = Scheduler.solve(unassigned,allTimeSlots,finalAssignment,allStudents);

        if(success){
            System.out.println("Timetable found:");
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
        }
        else{
            System.out.println("Timetable not found.");
        }

    }


}