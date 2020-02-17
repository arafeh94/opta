package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.Comparator;

@XStreamAlias("Range")
public class Range extends AbstractPersistable {
    private Zone zone;
    private ArrayList<Counter> counters;
    private int positionInZone;

    public Range() {

    }

    public Range(long id, Zone zone, int positionInZone) {
        super(id);
        this.zone = zone;
        this.positionInZone = positionInZone;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public ArrayList<Counter> getCounters() {
        if (counters == null) counters = new ArrayList<>();
        return counters;
    }

    public void setCounters(ArrayList<Counter> counters) {
        if (counters == null) counters = new ArrayList<>();
        this.counters = counters;
    }

    public void addCounter(Counter counter) {
        if (counters == null) counters = new ArrayList<>();
        counters.add(counter);
    }

    public int getPositionInZone() {
        return positionInZone;
    }

    public void setPositionInZone(int positionInZone) {
        this.positionInZone = positionInZone;
    }

    public Range next() {
        return zone.getRanges().stream().sorted(Comparator.comparingInt(c -> c.positionInZone)).filter(c -> c.positionInZone > positionInZone).findFirst().orElse(null);
    }
}
