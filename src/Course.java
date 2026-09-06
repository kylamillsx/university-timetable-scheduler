public class Course {
    private final String id;
    private final String name;
    private final Lecturer lecturer;
    private final int enrolledCount;
    private final int roomSpace;

    public Course(String id, String name, Lecturer lecturer, int enrolledCount, int roomSpace){
        this.id = id;
        this.name = name;
        this.lecturer = lecturer;
        this.enrolledCount = enrolledCount;
        this.roomSpace = roomSpace;
    }

    public String getName(){return name;}
    public String getId(){return id;}
    public Lecturer getLecturer(){return lecturer;}
    public int getEnrolledCount(){return enrolledCount;}
    public int getRoomSpace(){return roomSpace;}

    @Override
    public String toString(){return name;}

}
