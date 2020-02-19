package app;

import com.google.gson.Gson;
import common.app.FGAllocatorSolver;
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

import static common.business.TimeTools.fromTime;

public class ConsoleStarter {

    public static void main(String[] args) throws IOException {
        FgAllocator solvedFgAllocator = FGAllocatorSolver.solve(generateAllocationProblemBig());
        printSolution(solvedFgAllocator);
        GanttViewer.create(solvedFgAllocator, false).show();
    }

    private static void printSolution(FgAllocator solvedFgAllocator) {
        System.out.println("Flight Groups");
        solvedFgAllocator.getFlightGroupsList().forEach(flightGroup -> System.out.println(flightGroup.getLabel() + ", reason: " + flightGroup.getReason()));
        System.out.println("Counters Distribution");
        for (Requirement requirement : solvedFgAllocator.getRequirementsList()) {
            System.out.println(requirement);
        }
    }

    private static FgAllocator generateAllocationProblemBig(){
        //9 belts 9 counters 9 conjunction point 1 range 1 terminal 1 zone 30 fg 50 req

        FgAllocator fgAllocator1 = new FgAllocator();
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

        Terminal t1 = new Terminal(IdGenerator.getId("T"), "T1");
        Zone z1 = new Zone(IdGenerator.getId("Z"), "Z1", 50, t1);
        Range r1 = new Range(IdGenerator.getId("R"), z1, 1);

        Conjunction c1 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(1), 10);
        conjunctions.add(c1);
        belts.get(0).setConjunction(c1);
        belts.get(1).setConjunction(c1);
        LocalTime start1 = LocalTime.of(0, 0, 0);
        LocalTime end1 = LocalTime.of(0, 0, 1);
        Counter counter1 = new Counter(IdGenerator.getId("CiC"), start1, end1, 1, 4, r1, belts.get(1), 1);
        counters.add(counter1);

        Conjunction c2 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(2), 10);
        conjunctions.add(c2);
        belts.get(0).setConjunction(c2);
        belts.get(2).setConjunction(c2);
        LocalTime start2 = LocalTime.of(0, 0, 0);
        LocalTime end2 = LocalTime.of(0, 0, 1);
        Counter counter2 = new Counter(IdGenerator.getId("CiC"), start2, end2, 2, 4, r1, belts.get(2), 2);
        counters.add(counter2);


        Conjunction c3 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(3), 10);
        conjunctions.add(c3);
        belts.get(0).setConjunction(c3);
        belts.get(3).setConjunction(c3);
        LocalTime start3 = LocalTime.of(0, 0, 0);
        LocalTime end3 = LocalTime.of(0, 0, 1);
        Counter counter3 = new Counter(IdGenerator.getId("CiC"), start3, end3, 3, 4, r1, belts.get(3), 3);
        counters.add(counter3);

        Conjunction c4 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(4), 10);
        conjunctions.add(c4);
        belts.get(0).setConjunction(c4);
        belts.get(4).setConjunction(c4);
        LocalTime start4 = LocalTime.of(0, 0, 0);
        LocalTime end4 = LocalTime.of(0, 0, 1);
        Counter counter4 = new Counter(IdGenerator.getId("CiC"), start4, end4, 4, 5, r1, belts.get(4), 4);
        counters.add(counter4);


        Conjunction c5 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(5), 10);
        conjunctions.add(c5);
        belts.get(0).setConjunction(c5);
        belts.get(5).setConjunction(c5);
        LocalTime start5 = LocalTime.of(0, 0, 0);
        LocalTime end5 = LocalTime.of(0, 0, 0);
        Counter counter5 = new Counter(IdGenerator.getId("CiC"), start5, end5, 5, 4, r1, belts.get(5), 5);
        counters.add(counter5);


        Conjunction c6 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(6), 10);
        conjunctions.add(c6);
        belts.get(0).setConjunction(c6);
        belts.get(6).setConjunction(c6);
        LocalTime start6 = LocalTime.of(0, 0, 0);
        LocalTime end6 = LocalTime.of(0, 0, 1);
        Counter counter6 = new Counter(IdGenerator.getId("CiC"), start6, end6, 6, 4, r1, belts.get(6), 6);
        counters.add(counter6);

        Conjunction c7 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(7), 10);
        conjunctions.add(c7);
        belts.get(0).setConjunction(c7);
        belts.get(7).setConjunction(c7);
        LocalTime start7 = LocalTime.of(0, 0, 0);
        LocalTime end7 = LocalTime.of(0, 0, 1);
        Counter counter7 = new Counter(IdGenerator.getId("CiC"), start7, end7, 1, 4, r1, belts.get(7), 7);
        counters.add(counter7);

        Conjunction c8 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(8), 10);
        conjunctions.add(c8);
        belts.get(0).setConjunction(c8);
        belts.get(8).setConjunction(c8);
        LocalTime start8 = LocalTime.of(0, 0, 0);
        LocalTime end8 = LocalTime.of(0, 0, 1);
        Counter counter8 = new Counter(IdGenerator.getId("CiC"), start8, end8, 8, 4, r1, belts.get(8), 8);
        counters.add(counter8);

        Conjunction c9 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(9), 10);
        conjunctions.add(c9);
        belts.get(0).setConjunction(c9);
        belts.get(9).setConjunction(c9);
        LocalTime start9 = LocalTime.of(0, 0, 0);
        LocalTime end9 = LocalTime.of(0, 0, 1);
        Counter counter9 = new Counter(IdGenerator.getId("CiC"), start9, end9, 9, 4, r1, belts.get(9), 9);
        counters.add(counter9);


        for (int i = 0; i < 20; i++) {
            long id = IdGenerator.getId("F");
            FlightGroup flightGroup = new FlightGroup(id, "fg - " + id, Tools.random(10, 50), new ArrayList<>(), new HashMap<>());
            flightGroups.add(flightGroup);
        }

        flightGroups.get(0).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(0), fromTime("08:00:00"), fromTime("09:00:00"), 10, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(0), fromTime("08:00:00"), fromTime("09:00:00"), 10, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(0), fromTime("10:00:00"), fromTime("11:00:00"), 10, 2));
        }});
        flightGroups.get(0).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 50);
        }});
        requirements.addAll(flightGroups.get(0).getRequirementList());

        flightGroups.get(1).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(1), fromTime("09:30:00"), fromTime("11:00:00"), 5, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(1), fromTime("02:00:00"), fromTime("03:00:00"), 5, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(1), fromTime("05:00:00"), fromTime("08:30:00"), 5, 1));
        }});
        flightGroups.get(1).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 30);
        }});
        requirements.addAll(flightGroups.get(1).getRequirementList());

        flightGroups.get(2).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(2), fromTime("05:15:00"), fromTime("06:00:00"), 7, 1));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(2), fromTime("10:00:00"), fromTime("11:00:00"), 7, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(2), fromTime("04:00:00"), fromTime("06:00:00"), 7, 3));
        }});
        flightGroups.get(2).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(2).getRequirementList());

        flightGroups.get(3).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(3), fromTime("01:00:00"), fromTime("02:00:00"), 7, 1));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(3), fromTime("10:00:00"), fromTime("11:00:00"), 7, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(3), fromTime("10:00:00"), fromTime("12:00:00"), 7, 2));
        }});
        flightGroups.get(3).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(3).getRequirementList());

        flightGroups.get(4).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(4), fromTime("09:00:00"), fromTime("010:00:00"), 7, 1));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(4), fromTime("10:00:00"), fromTime("11:00:00"), 7, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(4), fromTime("10:30:00"), fromTime("12:00:00"), 7, 2));
        }});
        flightGroups.get(4).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(4).getRequirementList());

        flightGroups.get(5).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(5), fromTime("07:00:00"), fromTime("07:30:00"), 7, 1));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(5), fromTime("10:00:00"), fromTime("11:00:00"), 7, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(5), fromTime("05:00:00"), fromTime("06:00:00"), 7, 3));
        }});
        flightGroups.get(5).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(5).getRequirementList());

        flightGroups.get(6).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(6), fromTime("06:00:00"), fromTime("06:30:00"), 7, 1));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(6), fromTime("08:00:00"), fromTime("08:30:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(6), fromTime("10:00:00"), fromTime("12:00:00"), 7, 2));
        }});
        flightGroups.get(6).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(6).getRequirementList());

        flightGroups.get(7).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(7), fromTime("05:00:00"), fromTime("06:00:00"), 7, 1));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(7), fromTime("10:00:00"), fromTime("11:00:00"), 7, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(7), fromTime("10:00:00"), fromTime("12:00:00"), 7, 2));
        }});
        flightGroups.get(7).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(7).getRequirementList());

        flightGroups.get(8).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(8), fromTime("10:00:00"), fromTime("11:00:00"), 7, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(8), fromTime("04:00:00"), fromTime("06:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(8), fromTime("10:00:00"), fromTime("10:15:00"), 7, 2));
        }});
        flightGroups.get(8).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(8).getRequirementList());

        flightGroups.get(9).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(9), fromTime("02:00:00"), fromTime("04:00:00"), 7, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(9), fromTime("04:15:00"), fromTime("05:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(9), fromTime("10:00:00"), fromTime("12:00:00"), 7, 2));
        }});
        flightGroups.get(9).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(9).getRequirementList());

        flightGroups.get(10).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(10), fromTime("03:00:00"), fromTime("04:30:00"), 7, 1));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(10), fromTime("12:00:00"), fromTime("13:00:00"), 7, 2));
        }});
        flightGroups.get(10).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(10).getRequirementList());

        flightGroups.get(11).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(11), fromTime("04:00:00"), fromTime("06:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(11), fromTime("11:30:00"), fromTime("13:00:00"), 7, 2));
        }});
        flightGroups.get(11).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(11).getRequirementList());

        flightGroups.get(12).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(12), fromTime("05:30:00"), fromTime("06:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(12), fromTime("14:00:00"), fromTime("15:00:00"), 7, 2));
        }});
        flightGroups.get(12).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(12).getRequirementList());

        flightGroups.get(13).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(13), fromTime("17:00:00"), fromTime("18:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(13), fromTime("19:00:00"), fromTime("19:30:00"), 7, 2));
        }});
        flightGroups.get(13).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(13).getRequirementList());

        flightGroups.get(14).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(14), fromTime("20:00:00"), fromTime("21:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(14), fromTime("10:00:00"), fromTime("10:30:00"), 7, 2));
        }});
        flightGroups.get(14).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(14).getRequirementList());

        flightGroups.get(15).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(15), fromTime("14:00:00"), fromTime("16:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(15), fromTime("12:15:00"), fromTime("13:00:00"), 7, 2));
        }});
        flightGroups.get(15).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(15).getRequirementList());

        flightGroups.get(16).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(16), fromTime("14:00:00"), fromTime("15:30:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(16), fromTime("10:45"), fromTime("11:45:00"), 7, 2));
        }});
        flightGroups.get(16).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(16).getRequirementList());

        flightGroups.get(17).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(17), fromTime("04:30:00"), fromTime("05:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(17), fromTime("10:15:00"), fromTime("12:30:00"), 7, 2));
        }});
        flightGroups.get(17).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(17).getRequirementList());

        flightGroups.get(18).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(18), fromTime("21:00:00"), fromTime("23:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(18), fromTime("18:00:00"), fromTime("19:30:00"), 7, 2));
        }});
        flightGroups.get(18).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(18).getRequirementList());

        flightGroups.get(19).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(19), fromTime("19:00:00"), fromTime("23:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(19), fromTime("22:00:00"), fromTime("22:30:00"), 7, 2));
        }});
        flightGroups.get(19).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(19).getRequirementList());

//        flightGroups.get(20).setRequirementList(new ArrayList<Requirement>() {{
//            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(20), fromTime("17:00:00"), fromTime("18:00:00"), 7, 2));
//        }});
//        flightGroups.get(20).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(20).getRequirementList());
//
//        flightGroups.get(21).setRequirementList(new ArrayList<Requirement>() {{
//            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(21), fromTime("10:00:00"), fromTime("10:30:00"), 7, 2));
//        }});
//        flightGroups.get(21).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(21).getRequirementList());
//
//        flightGroups.get(22).setRequirementList(new ArrayList<Requirement>() {{
//            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(22), fromTime("20:00:00"), fromTime("20:30:00"), 7, 2));
//        }});
//        flightGroups.get(22).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(22).getRequirementList());
//
//        flightGroups.get(23).setRequirementList(new ArrayList<Requirement>() {{
//            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(23), fromTime("10:00:00"), fromTime("12:00:00"), 7, 2));
//        }});
//        flightGroups.get(23).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(23).getRequirementList());
//
//        flightGroups.get(24).setRequirementList(new ArrayList<Requirement>() {{
//            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(24), fromTime("13:00:00"), fromTime("14:00:00"), 7, 2));
//        }});
//        flightGroups.get(24).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(24).getRequirementList());
//
//        flightGroups.get(25).setRequirementList(new ArrayList<Requirement>() {{
//           add(new Requirement(IdGenerator.getId("R"), flightGroups.get(25), fromTime("15:00:00"), fromTime("15:30:00"), 7, 2));
//        }});
//        flightGroups.get(25).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(25).getRequirementList());
//
//        flightGroups.get(26).setRequirementList(new ArrayList<Requirement>() {{
//            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(26), fromTime("20:00:00"), fromTime("21:30:00"), 7, 2));
//        }});
//        flightGroups.get(26).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(26).getRequirementList());
//
//        flightGroups.get(27).setRequirementList(new ArrayList<Requirement>() {{
//            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(27), fromTime("13:00:00"), fromTime("15:00:00"), 7, 2));
//        }});
//        flightGroups.get(27).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(27).getRequirementList());
//
//        flightGroups.get(28).setRequirementList(new ArrayList<Requirement>() {{
//            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(28), fromTime("17:15:00"), fromTime("19:00:00"), 7, 2));
//        }});
//        flightGroups.get(28).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(28).getRequirementList());
//
//        flightGroups.get(29).setRequirementList(new ArrayList<Requirement>() {{
//             add(new Requirement(IdGenerator.getId("R"), flightGroups.get(29), fromTime("13:00:00"), fromTime("15:30:00"), 7, 2));
//        }});
//        flightGroups.get(29).setPreferences(new HashMap<Zone, Integer>() {{
//            put(z1, 40);
//        }});
//        requirements.addAll(flightGroups.get(29).getRequirementList());


        fgAllocator1.setConjunctionList(conjunctions);
        fgAllocator1.setBeltList(belts);
        fgAllocator1.setRangeList(ranges);
        fgAllocator1.setTerminalList(terminals);
        fgAllocator1.setFlightGroupsList(flightGroups);
        fgAllocator1.setZoneList(zones);
        fgAllocator1.setCountersList(counters);
        fgAllocator1.setRequirementsList(requirements);

        return fgAllocator1;

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

        for (int i = 0; i < 7; i++) {
            long id = IdGenerator.getId("B");
            Belt belt = new Belt(id, Tools.random(0, 10), "Belt - " + id);
            belts.add(belt);
        }

        Terminal t1 = new Terminal(IdGenerator.getId("T"), "T1");
        Zone z1 = new Zone(IdGenerator.getId("Z"), "Z1", 50, t1);
        Range r1 = new Range(IdGenerator.getId("R"), z1, 1);

        Conjunction c1 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(1), 10);
        conjunctions.add(c1);
        belts.get(0).setConjunction(c1);
        belts.get(1).setConjunction(c1);
        LocalTime start1 = LocalTime.of(0, 0, 0);
        LocalTime end1 = LocalTime.of(0, 0, 1);
        Counter counter1 = new Counter(IdGenerator.getId("CiC"), start1, end1, 1, 4, r1, belts.get(1), 1);
        counters.add(counter1);

        Conjunction c2 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(2), 10);
        conjunctions.add(c2);
        belts.get(0).setConjunction(c2);
        belts.get(2).setConjunction(c2);
        LocalTime start2 = LocalTime.of(0, 0, 0);
        LocalTime end2 = LocalTime.of(0, 0, 1);
        Counter counter2 = new Counter(IdGenerator.getId("CiC"), start2, end2, 2, 4, r1, belts.get(2), 2);
        counters.add(counter2);


        Conjunction c3 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(3), 10);
        conjunctions.add(c3);
        belts.get(0).setConjunction(c3);
        belts.get(3).setConjunction(c3);
        LocalTime start3 = LocalTime.of(0, 0, 0);
        LocalTime end3 = LocalTime.of(0, 0, 1);
        Counter counter3 = new Counter(IdGenerator.getId("CiC"), start3, end3, 3, 4, r1, belts.get(3), 3);
        counters.add(counter3);

        Conjunction c4 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(4), 10);
        conjunctions.add(c4);
        belts.get(0).setConjunction(c4);
        belts.get(4).setConjunction(c4);
        LocalTime start4 = LocalTime.of(0, 0, 0);
        LocalTime end4 = LocalTime.of(0, 0, 1);
        Counter counter4 = new Counter(IdGenerator.getId("CiC"), start4, end4, 4, 5, r1, belts.get(4), 4);
        counters.add(counter4);


        Conjunction c5 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(5), 10);
        conjunctions.add(c5);
        belts.get(0).setConjunction(c5);
        belts.get(5).setConjunction(c5);
        LocalTime start5 = LocalTime.of(0, 0, 0);
        LocalTime end5 = LocalTime.of(0, 0, 0);
        Counter counter5 = new Counter(IdGenerator.getId("CiC"), start5, end5, 5, 4, r1, belts.get(5), 5);
        counters.add(counter5);


        Conjunction c6 = new Conjunction(IdGenerator.getId("VG"), belts.get(0), belts.get(6), 10);
        conjunctions.add(c6);
        belts.get(0).setConjunction(c6);
        belts.get(6).setConjunction(c6);
        LocalTime start6 = LocalTime.of(0, 0, 0);
        LocalTime end6 = LocalTime.of(0, 0, 1);
        Counter counter6 = new Counter(IdGenerator.getId("CiC"), start6, end6, 6, 4, r1, belts.get(6), 6);
        counters.add(counter6);


        for (int i = 0; i < 3; i++) {
            long id = IdGenerator.getId("F");
            FlightGroup flightGroup = new FlightGroup(id, "fg - " + id, Tools.random(10, 50), new ArrayList<>(), new HashMap<>());
            flightGroups.add(flightGroup);
        }

        flightGroups.get(0).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(0), fromTime("08:00:00"), fromTime("09:00:00"), 10, 1));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(0), fromTime("08:00:00"), fromTime("09:00:00"), 10, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(0), fromTime("08:00:00"), fromTime("09:00:00"), 10, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(0), fromTime("10:00:00"), fromTime("11:00:00"), 10, 2));
        }});
        flightGroups.get(0).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 50);
        }});
        requirements.addAll(flightGroups.get(0).getRequirementList());

        flightGroups.get(1).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(1), fromTime("09:30:00"), fromTime("11:00:00"), 5, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(1), fromTime("02:00:00"), fromTime("03:00:00"), 5, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(1), fromTime("05:00:00"), fromTime("08:30:00"), 5, 1));
        }});
        flightGroups.get(1).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 30);
        }});
        requirements.addAll(flightGroups.get(1).getRequirementList());


        flightGroups.get(2).setRequirementList(new ArrayList<Requirement>() {{
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(2), fromTime("05:00:00"), fromTime("06:00:00"), 7, 1));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(2), fromTime("10:00:00"), fromTime("11:00:00"), 7, 2));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(2), fromTime("04:00:00"), fromTime("06:00:00"), 7, 3));
            add(new Requirement(IdGenerator.getId("R"), flightGroups.get(2), fromTime("10:00:00"), fromTime("12:00:00"), 7, 2));
        }});
        flightGroups.get(2).setPreferences(new HashMap<Zone, Integer>() {{
            put(z1, 40);
        }});
        requirements.addAll(flightGroups.get(2).getRequirementList());


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
