package domain.shadow;

import common.business.Interval;
import common.domain.AbstractPersistable;
import domain.Counter;
import domain.FgAllocator;
import domain.FlightGroup;
import domain.Requirement;
import org.optaplanner.core.impl.domain.variable.listener.VariableListener;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.*;

public class CounterUpdatingListener implements VariableListener<Requirement> {

    @Override
    public void beforeEntityAdded(ScoreDirector scoreDirector, Requirement requirement) {
    }

    @Override
    public void afterEntityAdded(ScoreDirector scoreDirector, Requirement requirement) {

    }

    /**
     * !important
     *
     * @param scoreDirector
     * @param requirement
     */
    @Override
    public void beforeVariableChanged(ScoreDirector scoreDirector, Requirement requirement) {

    }

    /**
     * !important
     * <p>
     * ne7na elna innu lamma ykun fi fg 1 men l counters b3ad 3an ba3od ya3ne hiye unplanned
     * ok ? asdak req la nafs l fg b3ad 3an ba3ed aw , la7za
     * azde counters ana
     * aw fekra a7la
     * iza 3indi counters bhal tari2a xyx ya3ne l x wel y henne unplanned
     * x unplanned y iza ma 3ndo ella hal req biykoun planned aw iza 3ndo 2 req bas rekbin 3a nafs l counters
     * cz wa2toun mekhtelif kamen btkoun l y planned mch unplanned
     * ah
     * yi
     * :(
     * sa7
     * "[tfeh -_-
     * hahahaha leik fik tkabir l font sizegr,e at tamem
     * hahahah kif kabarto ctrl mouse middle scroll , bass baddek ta3mlila enable abel
     * ah aslan ana besta3mil mouse l lap, 7aram ma 3indek middle button
     * wazza3tile albe :p
     * ahahhaahah
     * he osta smike
     * fiyia ktir conditions
     * ah ya 3ein
     * la kel counters
     * iza l counters lli ba3du mannu la nafes l fg
     * leish 7esses inna 3 for each for ba3ed -_-
     *
     * @param scoreDirector
     * @param gottenReq
     */
    @Override
    public void afterVariableChanged(ScoreDirector scoreDirector, Requirement gottenReq) {
        FlightGroup flightGroup = gottenReq.getFlightGroup();
        List<Requirement> requirements = flightGroup.getRequirementList();
        if (requirements.size() == 1) {
            flightGroup.setPlanned(true, "");
            return;
        }
        HashMap<Interval, ArrayList<Counter>> overlappedCounters = new HashMap<>();

        for (Requirement i : requirements) {
            if (i.getCounter() != null) {
                Optional<Interval> interval = overlappedCounters.keySet().stream()
                        .filter(t -> t.overlapped(i.getInterval())).findAny();
                if (interval.isPresent()) {
                    overlappedCounters.get(interval.get()).add(i.getCounter());
                    interval.get().expand(i.getInterval());
                } else {
                    ArrayList<Counter> clist = new ArrayList<>();
                    clist.add(i.getCounter());
                    overlappedCounters.put(i.getInterval(), new ArrayList<>(clist));
                }
                for (Requirement j : requirements) {
                    if (j.getCounter() != null) {
                        if (i == j) continue;
                        if (i.getCounter() == j.getCounter() && i.isOverlappedWith(j)) {
                            changePlanningStatus(scoreDirector, flightGroup, false, "because overlapping");
                            return;
                        } else {
                            changePlanningStatus(scoreDirector, flightGroup, true, "");
                        }
                    }
                }
            }
        }


        //if the next counter of the current one inside the list of all counter
        //that means good
        //if not
        //this is an error
        //if there is more than 1 error
        //flightgroup is unplanned
        for (Interval interval : overlappedCounters.keySet()) {
            ArrayList<Counter> overlappedInTimestamp = overlappedCounters.get(interval);
            int errors = 0;
            for (Counter counter : overlappedInTimestamp) {
                if (!overlappedInTimestamp.contains(counter.next())) {
                    errors += 1;
                }
            }
            if (errors > 1) {
                changePlanningStatus(scoreDirector, flightGroup, false, "because counters aren't in sequence inside the same flight group interval @check CounterUpdatingListener");
            }
        }
    }

    private void changePlanningStatus(ScoreDirector scoreDirector, FlightGroup flightGroup, boolean isPlanned, String msg) {
        scoreDirector.beforeVariableChanged(flightGroup, "planned");
        flightGroup.setPlanned(isPlanned, msg);
        scoreDirector.afterVariableChanged(flightGroup, "planned");
    }


    @Override
    public void beforeEntityRemoved(ScoreDirector scoreDirector, Requirement requirement) {

    }

    @Override
    public void afterEntityRemoved(ScoreDirector scoreDirector, Requirement requirement) {

    }


}
