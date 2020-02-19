package common.business;

public class Interval {
    private long start;
    private long end;

    public Interval(long start, long end) {
        this.start = start;
        this.end = end;
    }


    public boolean overlapped(long start, long end) {
        return (start <= this.end) && (end >= this.start);
    }

    public boolean overlapped(Interval interval) {
        return overlapped(interval.start, interval.end);
    }

    public void expand(long start, long end) {
        this.start = Math.min(this.start, start);
        this.end = Math.max(this.end, end);
    }

    public void expand(Interval interval) {
        this.start = Math.min(this.start, interval.start);
        this.end = Math.max(this.end, interval.end);
    }
}