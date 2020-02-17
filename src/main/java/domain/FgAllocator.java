package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.domain.AbstractPersistable;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;

import java.util.List;

@PlanningSolution
@XStreamAlias("FgAllocator")
public class FgAllocator extends AbstractPersistable {
    private List<Requirement> requirementsList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "counterRange")
    private List<Counter> countersList;


    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "flightGroupsRange")
    private List<FlightGroup> flightGroupsList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "TerminalRange")
    private List<Terminal> terminalList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "ZonesRange")
    private List<Zone> zoneList;


    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "RangeRane")
    private List<Range> rangeList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "ConjunctionRange")
    private List<Conjunction> conjunctionList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "BeltRange")
    private List<Belt> beltList;

    public FgAllocator(long id) {
        super(id);
    }

    public FgAllocator() {
    }

    @PlanningEntityCollectionProperty
    public List<Requirement> getRequirementsList() {
        return requirementsList;
    }

    public List<Counter> getCountersList() {
        return countersList;
    }

    public List<FlightGroup> getFlightGroupsList() {
        return flightGroupsList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public List<Zone> getZoneList() {
        return zoneList;
    }

    @PlanningScore(bendableHardLevelsSize = 1, bendableSoftLevelsSize = 2)
    private BendableScore score;

    public void setCountersList(List<Counter> countersList) {
        this.countersList = countersList;
    }

    public void setFlightGroupsList(List<FlightGroup> flightGroupsList) {
        this.flightGroupsList = flightGroupsList;
    }

    public void setRequirementsList(List<Requirement> requirementsList) {
        this.requirementsList = requirementsList;
    }

    public List<Belt> getBeltList() {
        return beltList;
    }

    public List<Conjunction> getConjunctionList() {
        return conjunctionList;
    }

    public List<Range> getRangeList() {
        return rangeList;
    }

    public List<Terminal> getTerminalList() {
        return terminalList;
    }

    public void setBeltList(List<Belt> beltList) {
        this.beltList = beltList;
    }

    public void setConjunctionList(List<Conjunction> conjunctionList) {
        this.conjunctionList = conjunctionList;
    }

    public void setRangeList(List<Range> rangeList) {
        this.rangeList = rangeList;
    }

    public void setTerminalList(List<Terminal> terminalList) {
        this.terminalList = terminalList;
    }

    public void setScore(BendableScore score) {
        this.score = score;
    }

    public BendableScore getScore() {
        return score;
    }
}
