package app;

import com.google.gson.Gson;
import common.app.Tools;
import common.domain.IdGenerator;
import common.gui.GanttViewer;
import domain.*;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ConsoleStarter {

    public static void main(String[] args) throws IOException {
        SolverFactory<FgAllocator> solverFactory = SolverFactory.createFromXmlResource("config.xml");
        Solver<FgAllocator> solver = solverFactory.buildSolver();
        FgAllocator unsolvedFgAllocator = generateAllocationProblem();
        FgAllocator solvedFgAllocator = solver.solve(unsolvedFgAllocator);
        printSolution(solvedFgAllocator);
        GanttViewer.create(solvedFgAllocator).show();
    }

    private static void printSolution(FgAllocator solvedFgAllocator) {
        System.out.println("Flight Groups");
        solvedFgAllocator.getFlightGroupsList().forEach(System.out::println);
        System.out.println("Counters");
        for (Counter counter : solvedFgAllocator.getCountersList()) {
            System.out.println(counter.toString());
        }
        System.out.println("Counters Distribution");
        for (Requirement requirement : solvedFgAllocator.getRequirementsList()) {
            System.out.println(requirement);
        }
    }

    private static FgAllocator generateAllocationProblem() {
        FgAllocator fgAllocator = new FgAllocator();
        ArrayList<Counter> counters = new ArrayList<>();
        ArrayList<Terminal> terminals = new ArrayList<>();
        ArrayList<Range> ranges = new ArrayList<>();
        ArrayList<Zone> zones = new ArrayList<>();
        ArrayList<Conjunction> conjunctions = new ArrayList<>();
        ArrayList<Belt> belts = new ArrayList<>();
        ArrayList<FlightGroup> flightGroups = new ArrayList<>();
        ArrayList<Requirement> requirements = new ArrayList<>();
        ArrayList<Belt> assignedBelts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            long id = IdGenerator.getId("B");
            Belt belt = new Belt(id, Tools.random(0, 10), "Belt - " + id);
            belts.add(belt);
        }

        for (int i = 0; i < belts.size() - 1; i += 2) {
            Conjunction conjunction = new Conjunction(IdGenerator.getId("VG"), belts.get(i), belts.get(i + 1), 10);
            conjunctions.add(conjunction);
            belts.get(i).setConjunction(conjunction);
            belts.get(i + 1).setConjunction(conjunction);
        }
        for (int i = 0; i < 5; i++) {
            Terminal terminal = new Terminal(IdGenerator.getId("T"), "Terminal - " + i);
            terminals.add(terminal);
        }
        for (Terminal terminal : terminals) {
            for (int i = 0; i < 10; i++) {
                Long id = IdGenerator.getId("Z");
                Zone zone = new Zone(id, "Zone - " + id, 10, terminal);
                zones.add(zone);
                terminal.addZone(zone);
            }
        }
        for (Zone zone : zones) {
            for (int i = 0; i < 10; i++) {
                Range range = new Range(IdGenerator.getId("r"), zone, i);
                ranges.add(range);
                zone.addRange(range);
            }
        }


        for (Range range : ranges) {
            for (int i = 0; i < 5; i++) {
                long id = IdGenerator.getId("C");
                int finalI = i;
                belts.stream().filter(b -> !assignedBelts.contains(b)).findAny().ifPresent(b -> {
                    LocalTime start = LocalTime.of(0, 0, 0);
                    LocalTime end = LocalTime.of(0, 0, 1);
                    Counter counter = new Counter(id, start, end, Tools.random(1, 50), Tools.randomf(5, 20), range, b, finalI);
                    counters.add(counter);
                    assignedBelts.add(b);
                });
            }
        }


        for (int i = 0; i < 10; i++) {
            long id = IdGenerator.getId("F");
            FlightGroup flightGroup = new FlightGroup(id, "fg - " + id, Tools.random(10, 50), new ArrayList<>(), new HashMap<>());
            flightGroups.add(flightGroup);
        }

        for (FlightGroup flightGroup : flightGroups) {
            final long fgId = flightGroup.getId();
            switch ((int) fgId) {
                default:
                    flightGroup.setRequirementList(new ArrayList<Requirement>() {{
                        add(new Requirement(IdGenerator.getId("R"), flightGroup, fromTime("08:00:00"), fromTime("09:00:00"), 10, 1));
                        add(new Requirement(IdGenerator.getId("R"), flightGroup, fromTime("08:00:00"), fromTime("09:00:00"), 10, 2));
                        add(new Requirement(IdGenerator.getId("R"), flightGroup, fromTime("08:00:00"), fromTime("09:00:00"), 10, 3));
                        add(new Requirement(IdGenerator.getId("R"), flightGroup, fromTime("10:00:00"), fromTime("11:00:00"), 10, 2));
                    }});
            }
            flightGroup.setPreferences(new HashMap<Zone, Integer>() {{
                put(zones.get(Tools.random(0, zones.size() - 1)), Tools.random(10, 50));
            }});

            requirements.addAll(flightGroup.getRequirementList());
        }

        fgAllocator.setConjunctionList(conjunctions);
        fgAllocator.setBeltList(belts);
        fgAllocator.setRangeList(ranges);
        fgAllocator.setTerminalList(terminals);
        fgAllocator.setFlightGroupsList(flightGroups);
        fgAllocator.setZoneList(zones);
        fgAllocator.setCountersList(counters);
        fgAllocator.setRequirementsList(requirements);

        return fgAllocator;
    }

    /**
     * @param date like 19-10-2020 15:30:58
     * @return id as java date
     */
    public static Date toDate(String date) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    private static Date fromTime(String time) {
        try {
            return new SimpleDateFormat("dd-MM-yyy HH:mm:ss").parse("01-01-2020 " + time + ":00");
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean saveProblem(FgAllocator allocator, String problemName) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(generateAllocationProblem());
            FileWriter f = new FileWriter(new File("./data/" + problemName + ".json"));
            f.write(json);
            f.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static FgAllocator loadProblem(String fileName) {
        try {
            Reader reader = new FileReader(fileName);
            Gson gson = new Gson();
            return gson.fromJson(reader, FgAllocator.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
