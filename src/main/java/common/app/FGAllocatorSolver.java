package common.app;

import common.gui.GanttViewer;
import domain.FgAllocator;
import domain.FlightGroup;
import domain.Requirement;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;

import java.util.List;
import java.util.stream.Collectors;

public class FGAllocatorSolver {
    public static long TIME_SPENT = 0;

    public static FgAllocator solve(FgAllocator allocator) {
        SolverFactory<FgAllocator> solverFactory = SolverFactory.createFromXmlResource("config.xml");
        Solver<FgAllocator> solver = solverFactory.buildSolver();
        FgAllocator solved = kickOverlapping(solver.solve(allocator));
        updateClassInfo(solver);
        return solved;
    }

    private static void updateClassInfo(Solver<FgAllocator> solver) {
        TIME_SPENT = solver.getTimeMillisSpent();
    }

    private static FgAllocator kickOverlapping(FgAllocator solve) {
        List<Requirement> requirements = solve.getFlightGroupsList().stream().filter(FlightGroup::getPlanned).flatMap(flightGroup -> flightGroup.getRequirementList().stream()).collect(Collectors.toList());
        for (Requirement i : requirements) {
            for (Requirement j : requirements) {
                if (i != j) {
                    if (i.getCounter().equals(j.getCounter()) && i.isOverlappedWith(j)) {
                        i.getFlightGroup().setPlanned(false, "because global overlapping");
                    }
                }
            }
        }
        return solve;
    }


}
