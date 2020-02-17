package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.domain.AbstractPersistable;
import common.domain.IdGenerator;
import common.domain.Labeled;
import domain.shadow.CounterUpdatingListener;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.CustomShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@XStreamAlias("FlightGroup")
@PlanningEntity
public class FlightGroup extends AbstractPersistable implements Labeled {
    /**
     * @param requirements
     * @return
     */
    public static FlightGroup create(ArrayList<Requirement> requirements) {
        return new FlightGroup(IdGenerator.getId("flightGroup"), "fg", 0, requirements, new HashMap<>());
    }


    /**
     * @param requirements
     * @return
     */
    public static FlightGroup create(ArrayList<Requirement> requirements, HashMap<Zone, Integer> preferences) {
        return new FlightGroup(IdGenerator.getId("flightGroup"), "fg", 0, requirements, preferences);
    }

    /**
     * @param requirements
     * @return
     */
    public static FlightGroup create(int totalPassenger, ArrayList<Requirement> requirements, HashMap<Zone, Integer> preferences) {
        return new FlightGroup(IdGenerator.getId("flightGroup"), "fg", totalPassenger, requirements, preferences);
    }

    private List<Requirement> requirementList;

    private Boolean planned;
    private String name;
    private int totalPassenger;

    private HashMap<Zone, Integer> preferences;

    public FlightGroup() {
        this.preferences = new HashMap<>();
    }

    public FlightGroup(long id, String name, int totalPassenger, ArrayList<Requirement> requirements, HashMap<Zone, Integer> preferences) {
        super(id);
        this.name = name;
        this.preferences = new HashMap<>();
        this.requirementList = requirements;
        this.preferences = preferences;
        this.totalPassenger = totalPassenger;
    }

    @CustomShadowVariable(variableListenerClass = CounterUpdatingListener.class,
            sources = {@PlanningVariableReference(entityClass = Requirement.class, variableName = "counter")})
    public Boolean getPlanned() {
        return planned;
    }

    public void setPlanned(Boolean planned) {
        this.planned = planned;
    }

    public List<Requirement> getRequirementList() {
        return requirementList;
    }

    public void addRequirement(Requirement requirement) {
        this.requirementList.add(requirement);
    }

    public void setRequirementList(List<Requirement> requirementList) {
        this.requirementList = requirementList;
    }

    public HashMap<Zone, Integer> getPreferences() {
        return preferences;
    }

    public void setPreferences(HashMap<Zone, Integer> preferences) {
        this.preferences = preferences;
    }

    public void putPreference(Zone zone, int points) {
        this.preferences.put(zone, points);
    }

    public void removePreference(Zone zone) {
        this.preferences.remove(zone);
    }

    public int getPreference(Zone zone) {
        if (this.preferences != null && this.preferences.containsKey(zone)) {
            return this.preferences.get(zone);
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPassenger() {
        return totalPassenger;
    }

    public void setTotalPassenger(int totalPassenger) {
        this.totalPassenger = totalPassenger;
    }

    @Override
    public String getLabel() {
        return "Flight Group: " + id + ", is planned: " + getPlanned();
    }

    public boolean hasPreferences() {
        return preferences != null && !preferences.isEmpty();
    }

    /**
     * @return the total number of time in millis of counters uses for the current flight group
     */
    public long getTotalCounterUsage() {
        long totalUsage = 0;
        for (Requirement requirement : requirementList) {
            if (requirement.getCounter() != null) {
                totalUsage += requirement.getEndTime().getTime() - requirement.getStartTime().getTime();
            }
        }
        return totalUsage;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
