package common.app;

import common.gui.GanttViewer;
import domain.FgAllocator;
import domain.Requirement;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

public class FGAllocatorSolver {
    public static FgAllocator solve(FgAllocator allocator) {
        SolverFactory<FgAllocator> solverFactory = SolverFactory.createFromXmlResource("config.xml");
        Solver<FgAllocator> solver = solverFactory.buildSolver();
        return solver.solve(allocator);
    }


}
