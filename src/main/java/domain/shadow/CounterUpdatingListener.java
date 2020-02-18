package domain.shadow;

import common.domain.AbstractPersistable;
import domain.Counter;
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
        //all fg requirements
        List<Requirement> flightGroupRequirements = flightGroup.getRequirementList();
        List<Counter> counters = new ArrayList<>();
        for (Requirement i : flightGroupRequirements) {
            if (i.getCounter() != null) counters.add(i.getCounter());
            for (Requirement j : flightGroupRequirements) {
                flightGroup.setPlanned(i.isOverlappedWith(j) && i.getCounter() == j.getCounter());
            }
        }
        long[] ids = counters.stream().sorted(AbstractPersistable::compareTo)
                .mapToLong(AbstractPersistable::getId)
                .toArray();


        for (int i = 0; i < ids.length - 1; i++) {
            long id1 = ids[i];
            long id2 = ids[i + 1];
            if (id2 - id1 != 1) {
                flightGroup.setPlanned(false);
                break;
            }
        }
    }

    @Override
    public void beforeEntityRemoved(ScoreDirector scoreDirector, Requirement requirement) {

    }

    @Override
    public void afterEntityRemoved(ScoreDirector scoreDirector, Requirement requirement) {

    }


}