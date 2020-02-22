package common.app;

import domain.FgAllocator;
import domain.FlightGroup;
import domain.Requirement;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FGAllocatorSolver {

    public static FGAllocatorSolver getInstance(FgAllocator problem) {
        return new FGAllocatorSolver(problem);
    }


    private FgAllocator problem;
    private FgAllocator solved;
    private Solver<FgAllocator> solver;


    private FGAllocatorSolver(FgAllocator problem) {
        this.problem = problem;
    }

    public FgAllocator solve() {
        SolverFactory<FgAllocator> solverFactory = SolverFactory.createFromXmlResource("config.xml");
        this.solver = solverFactory.buildSolver();
        this.solved = kickOverlapping(solver.solve(this.problem));
        return this.solved;
    }

    public long getTimeSpent() {
        return this.solver.getTimeMillisSpent();
    }

    @SuppressWarnings("unchecked")
    public Score scoreSolution(FgAllocator solution) {
        ScoreDirectorFactory scoreDirectorFactory = solver.getScoreDirectorFactory();
        ScoreDirector guiScoreDirector = scoreDirectorFactory.buildScoreDirector();
        guiScoreDirector.setWorkingSolution(solution);
        return guiScoreDirector.calculateScore();
    }

    public FgAllocator getBestSolution() {
        return solved;
    }

    public Score getBestScore() {
        return solver.getBestScore();
    }


    private FgAllocator kickOverlapping(FgAllocator solve) {
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
