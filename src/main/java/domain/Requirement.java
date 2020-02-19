package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.business.Interval;
import common.business.TimeTools;
import common.domain.AbstractPersistable;
import common.domain.IdGenerator;
import common.domain.Labeled;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;

import java.util.Date;

@PlanningEntity() //hon choushile halla2 , ba3dein menshuf estu okii
@XStreamAlias("Requirement")
public class Requirement extends AbstractPersistable implements Labeled {


    public static Requirement create(FlightGroup flightGroup, Date startTime, Date endTime, int bufferTime, int classType) {
        Requirement requirement = new Requirement(IdGenerator.getId("requirement"), flightGroup, startTime, endTime, bufferTime, classType);
        flightGroup.addRequirement(requirement);
        return requirement;
    }

    public static Requirement create(FlightGroup flightGroup, Date startTime, Date endTime) {
        return create(flightGroup, startTime, endTime, 15, 1);
    }

    private Date startTime;
    private Date endTime;
    private int bufferTime;
    private FlightGroup flightGroup;
    private transient Counter counter;
    private int classType;


    public int getClassType() {
        return classType;
    }

    /**
     * @param classType 1:business 2:economic
     */
    public void setClassType(int classType) {
        this.classType = classType;
    }


    public Requirement() {
    }

    public Requirement(long id, FlightGroup flightGroup, Date startTime, Date endTime, int bufferTime, int classType) {
        super(id);
        this.startTime = startTime;
        this.endTime = endTime;
        this.bufferTime = bufferTime;
        this.flightGroup = flightGroup;
        this.classType = classType;
    }

    @PlanningVariable(valueRangeProviderRefs = "counterRange")
    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public FlightGroup getFlightGroup() {
        return flightGroup;
    }

    public void setFlightGroup(FlightGroup flightGroup) {
        this.flightGroup = flightGroup;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getBufferTime() {
        return bufferTime;
    }

    public void setBufferTime(int bufferTime) {
        this.bufferTime = bufferTime;
    }

    public Interval getInterval(boolean includeBuffer) {
        long end = this.getEndTime().getTime();
        if (includeBuffer) {
            end += this.bufferTime * 60 * 1000;
        }
        return new Interval(this.startTime.getTime(), end);
    }
    public Interval getInterval() {
        return getInterval(true);
    }

    /**
     * optimise iza elna khle2a
     *
     * @param requirement
     * @return
     */
    public boolean isOverlappedWith(Requirement requirement) {
        float start = startTime.getTime();
        float end = endTime.getTime() + bufferTime * 60 * 1000;
        float otherStart = requirement.getStartTime().getTime();
        float otherEnd = requirement.getEndTime().getTime() + requirement.bufferTime * 60 * 1000;
        return TimeTools.overlap(start, end, otherStart, otherEnd);
    }

    @Override
    public String getLabel() {
        return "id:" + id;
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", flightGroup=" + flightGroup +
                ", counters=" + counter +
                ", classType=" + classType +
                '}';
    }
}
