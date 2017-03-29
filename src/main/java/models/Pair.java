package models;

public class Pair<First, Last> {
    public final First first;
    public final Last last;
    public Pair(First first, Last last) {
        this.first = first;
        this.last = last;
    }
}