package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.app.Var;
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
import java.util.Objects;

@XStreamAlias("FlightGroup")
@PlanningEntity
public class FlightGroup extends AbstractPersistable implements Labeled {
    private List<Requirement> requirementList;

    private Boolean planned;
    private String reason;

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
        if (planned == null) return false;
        return planned;
    }

    public void setPlanned(Boolean planned, String reason) {
        this.planned = planned;
        this.reason = reason;
    }

    public String getReason() {
        if (reason == null) return "";
        return reason;
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

    /**
     * wa2tiye :p
     *
     * @return
     */
    public int getTotalNumberOfBags() {
        return getTotalPassenger();
    }

    @Override
    public String toString() {
        return getLabel();
    }

    public int conjunctionCongestion() {
        Var<Integer> totalCapacity = Var.of(0);
        ArrayList<Conjunction> calculated = new ArrayList<>();
        requirementList.stream().filter(r -> r.getCounter() != null).forEach(requirement -> {
            Counter counter = requirement.getCounter();
            if (counter != null) {
                Conjunction conjunction = counter.getBelt().getConjunction();
                if (calculated.indexOf(conjunction) == -1 && counter.getBelt().getConjunction() != null) {
                    totalCapacity.set(totalCapacity.get() + counter.getBelt().getConjunction().getMaxCapacity());
                    calculated.add(conjunction);
                }
            }
        });
        return totalCapacity.get() - getTotalNumberOfBags();
    }
}
