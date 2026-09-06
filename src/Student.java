import java.util.List;
public class Student {
    private final String id;
    private final List<Course> enrolledCourses;

    public Student(String id, List<Course> enrolledCourses) {
        this.id = id;
        this.enrolledCourses = enrolledCourses;
    }

    public String getId(){return id;}
    public List<Course> getEnrolledCourses(){return enrolledCourses;}


}
