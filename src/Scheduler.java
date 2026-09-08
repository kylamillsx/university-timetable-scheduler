import java.sql.Time;
import java.util.*;
public class Scheduler {
    //Check if a course to a slot is allowed given what's already been assigned
    public static boolean isValid(Course course, TimeSlot timeSlot, Map<Course, TimeSlot> assignment, List<Student> allStudents) {

        //Checking against every Course already assigned to this same slot
        for (Map.Entry<Course, TimeSlot> entry : assignment.entrySet()) {
            Course assignedCourse = entry.getKey();
            TimeSlot assignedTimeSlot = entry.getValue();

            if (assignedTimeSlot.equals(timeSlot)) {
                if(assignedCourse.getLecturer().equals(course.getLecturer())){//Ensuring the same lecturer doesn't teach course assigned at the same time
                    return false;
                }
                if(sharesStudents(course, assignedCourse, allStudents)){
                    return false;//Student Clash
                }

            }


        }

        if (course.getEnrolledCount()>course.getRoomSpace()){
            return false;//Capacity check
        }

        return true; //All checks passed

    }

    //Helper to see if any student takes both course a and b
    public static boolean sharesStudents(Course a, Course b, List<Student> allStudents){
        for(Student s: allStudents){
            List<Course> enrolled = s.getEnrolledCourses();
            if(enrolled.contains(a)&&enrolled.contains(b)){
                return true;
            }
        }
        return false;
    }


    //Backtracker
    public static boolean solve(List<Course> unassigned,List<TimeSlot> allTimeSlots, Map<Course,TimeSlot> assignment, List<Student> allStudents){
        //Base Case: We're finished once there's nothing left to assign
        if(unassigned.isEmpty()){
            return true;
        }

        Course course = unassigned.get(0);
        List<Course> remaining = unassigned.subList(1, unassigned.size());

        for(TimeSlot timeSlot: allTimeSlots){
            if (isValid(course, timeSlot, assignment, allStudents)){
                assignment.put(course, timeSlot); //Try this assignment

                if(solve(remaining,allTimeSlots,assignment,allStudents)){//Recursion called
                    return true; //Rest of the courses worked out, propagate success up
                }

                assignment.remove(course); //Backtracking, try the next slot
            }

        }
        return false; //no slot worked for this course
    }

    /**
     * Scores a timetable based on how evenly spread the courses are across the days.
     * lower score is better (0 is perfectly even distribution)
     * Higher score would infer you have one completely full day where you could just spread it.
     */
    //Scoring function: measures how good the timetable is, so we have something to optimise
    public static int score(Map<Course, TimeSlot> assignment){

        int penalty = 0;

        //Count how many courses fall on each day
        Map<Day, Integer> dayCounts = new HashMap<>();
        for(TimeSlot slot: assignment.values()){
            dayCounts.merge(slot.getDay(),1,Integer::sum);
        }

        //Penalise days that are overloaded relative to an even spread
        int totalCourses = assignment.size();
        int numDays = Day.values().length;
        int idealPerDay = totalCourses/numDays;

        for(int count : dayCounts.values()){
            int diff = count - idealPerDay;
            if(diff>0){
                penalty += diff * diff;//Squared it to punish big clusters more than small
            }
        }
        return penalty;//lower is better, 0 = perfectly even


    }
}
