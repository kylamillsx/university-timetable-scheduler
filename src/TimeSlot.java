import java.util.Objects;
public class TimeSlot {
    private final Day day;
    private final int period;

    public TimeSlot(Day day, int period) {
        this.day = day;
        this.period = period;
    }

    //Getters
    public Day getDay(){
        return day;
    }
    public int getPeriod(){
        return period;
    }

    @Override
    public String toString(){return day + " P"+period;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot t = (TimeSlot) o;
        return period == t.period && day == t.day;
    }

    @Override
    public int hashCode() {return Objects.hash(day, period);}

}
