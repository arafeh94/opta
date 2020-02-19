package domain.shadow;

import common.domain.AbstractPersistable;
import domain.Counter;
import domain.FgAllocator;
import domain.FlightGroup;
import domain.Requirement;
import org.optaplanner.core.impl.domain.variable.listener.VariableListener;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.ArrayList;
import java.util.List;

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
     * @param requirement
     */
    @Override
    public void afterVariableChanged(ScoreDirector scoreDirector, Requirement requirement) {
        FlightGroup flightGroup = requirement.getFlightGroup();
        List<Requirement> flightGroupRequirements = flightGroup.getRequirementList();
        List<Counter> counters = new ArrayList<>();
        if (flightGroupRequirements.size() == 1) {
            flightGroup.setPlanned(true, "");
            return;
        }
        for (Requirement i : flightGroupRequirements) {
            if (i.getCounter() != null) {
                counters.add(i.getCounter());
                for (Requirement j : flightGroupRequirements) {
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
        int errors = 0;
        for (Requirement req : flightGroupRequirements) {
            if (req.getCounter() != null && counters.indexOf(req.getCounter().next()) == -1) {
                errors += 1;
            }
        }
        String msg = "because didn't have sequential counters";
        changePlanningStatus(scoreDirector, flightGroup, errors <= 1, msg);
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
