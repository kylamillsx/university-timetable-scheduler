public class Lecturer {
    private final String id;
    private final String name;

    public  Lecturer(String id, String name){
        this.id = id;
        this.name = name;
    }
    public String getId(){return this.id;}

    public String getName(){return this.name;}

    @Override
    public String toString(){return name;}
}
