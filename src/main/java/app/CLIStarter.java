package app;

import com.google.gson.Gson;
import common.app.FGAllocatorSolver;
import common.gui.GanttDataBuilder;
import domain.*;
import domain.json.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static common.app.Tools.find;
import static common.app.Tools.findP;
import static common.business.TimeTools.stringToDate;
import static common.business.TimeTools.stringToLocalTime;

public class CLIStarter {
    private static final String SAVE_PATH = "C:\\wamp64\\www\\optaweb\\web";

    public static FGAllocatorSolver SOLVER;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Must specify the file location");
            System.exit(0);
        }
        try {
            File input = new File(args[0]);
            Scanner scanner = new Scanner(input);
            StringBuilder fileContent = new StringBuilder("");
            scanner.forEachRemaining(s -> fileContent.append(s).append(" "));
            JSAllocator complied = parse(fileContent.toString());
            FgAllocator allocator = loadProject(complied);
            solveProblem(allocator, complied.includeUnplanned == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSAllocator parse(String fileContent) {
        Gson gson = new Gson();
        return gson.fromJson(fileContent, JSAllocator.class);
    }

    /**
     * lfekra kella innu l objects lli bel json gher l object lli henne bel projects
     * lahek 3mltellon classes khsouse abel kel class JS_
     * kermel ma dayye3 fion
     * w 3meltu metel ma henne mawjoudin bel file kerem Gson ya3mellon auto parse
     * halla2 sara ma3e yehon classes java bass ba3ed mush kefe
     * l opta planner 3indu classes khsouse fi
     * lahek halla2 3am 7awlon men JS_Classes lal l classes lli mesta3mlinon bel opta planner
     *
     * @param jsAllocator
     * @return
     */
    private static FgAllocator loadProject(JSAllocator jsAllocator) throws ParseException {
        ArrayList<Terminal> terminals = new ArrayList<>();
        for (JSTerminal jsTerminal : jsAllocator.terminals) {
            terminals.add(new Terminal(jsTerminal.id, jsTerminal.name));
        }

        ArrayList<Zone> zones = new ArrayList<>();
        for (JSZone jsZone : jsAllocator.zones) {
            Terminal terminal = find(terminals, jsZone.terminal_id);
            Zone zone = new Zone(jsZone.id, jsZone.name, jsZone.max_passenger, terminal);
            zones.add(zone);
            terminal.addZone(zone);
        }

        ArrayList<Range> ranges = new ArrayList<>();
        for (JSRange jsRange : jsAllocator.ranges) {
            findP(zones, jsRange.zone_id).ifPresent(zone -> {
                Range range = new Range(jsRange.id, zone, jsRange.position_in_zone);
                ranges.add(range);
                zone.addRange(range);
            });
        }

        ArrayList<Belt> belts = new ArrayList<>();
        for (JSBelt jsBelt : jsAllocator.belts) {
            belts.add(new Belt(jsBelt.id, jsBelt.ratio_bag_per_timeunit, jsBelt.name));
        }

        ArrayList<Conjunction> conjunctions = new ArrayList<>();
        for (JSConjunction jsConjunction : jsAllocator.conjunctions) {
            Belt parent = find(belts, jsConjunction.belt_id_parent);
            Belt child = find(belts, jsConjunction.belt_id_child);
            if (parent != null && child != null) {
                Conjunction conjunction = new Conjunction(jsConjunction.id, parent, child, jsConjunction.max_capacity);
                conjunctions.add(conjunction);
                parent.setConjunction(conjunction);
                child.setConjunction(conjunction);
            }
        }

        ArrayList<Counter> counters = new ArrayList<>();
        for (JSCounter jsCounter : jsAllocator.counters) {
            Range range = find(ranges, jsCounter.range_id);
            Belt belt = find(belts, jsCounter.belt_id);
            if (range != null && belt != null) {
                Counter counter = new Counter(jsCounter.id,
                        stringToLocalTime(jsCounter.getUnavailabilityPeriodStartTime()),
                        stringToLocalTime(jsCounter.getUnavailabilityPeriodEndTime()),
                        jsCounter.proximity, jsCounter.ratio_passenger_per_timeunit, range, belt, jsCounter.position_in_range);
                counters.add(counter);
                range.addCounter(counter);
            }
        }

        ArrayList<FlightGroup> flightGroups = new ArrayList<>();
        for (JSFlightGroup jsFlightGroup : jsAllocator.flightGroups) {
            FlightGroup flightGroup = new FlightGroup(jsFlightGroup.id, jsFlightGroup.name, jsFlightGroup.total_passengers, new ArrayList<>(), new HashMap<>());
            flightGroups.add(flightGroup);
        }

        ArrayList<Requirement> requirements = new ArrayList<>();
        for (JSRequirement jsRequirement : jsAllocator.requirements) {
            FlightGroup flightGroup = find(flightGroups, jsRequirement.flight_group_id);
            if (flightGroup != null) {
                Requirement requirement = new Requirement();
                requirement.setId(jsRequirement.id);
                requirement.setBufferTime(jsRequirement.buffer_time);
                requirement.setClassType(jsRequirement.class_type);
                requirement.setStartTime(stringToDate(jsRequirement.date_start));
                requirement.setEndTime(stringToDate(jsRequirement.date_end));
                requirement.setFlightGroup(flightGroup);
                flightGroup.addRequirement(requirement);
                requirements.add(requirement);
            }
        }

        for (JSPreference jsPreference : jsAllocator.preferences) {
            flightGroups.stream().filter(fg -> fg.getId() == jsPreference.flight_group_id).findFirst()
                    .ifPresent(flightGroup -> zones.stream().filter(z -> z.getId() == jsPreference.zone_id)
                            .findFirst().ifPresent(zone -> flightGroup.putPreference(zone, jsPreference.points)));
        }

        flightGroups.removeAll(flightGroups.stream().filter(fg -> fg.getRequirementList().isEmpty()).collect(Collectors.toList()));
        FgAllocator fgAllocator = new FgAllocator(1);
        fgAllocator.setZoneList(zones);
        fgAllocator.setCountersList(counters);
        fgAllocator.setRequirementsList(requirements);
        fgAllocator.setFlightGroupsList(flightGroups);
        fgAllocator.setTerminalList(terminals);
        fgAllocator.setRangeList(ranges);
        fgAllocator.setBeltList(belts);
        fgAllocator.setConjunctionList(conjunctions);

        return fgAllocator;
    }

    private static void solveProblem(FgAllocator unsolved, boolean includeUnplanned) throws IOException {
        CLIStarter.SOLVER = FGAllocatorSolver.getInstance(unsolved);
        FgAllocator solved = SOLVER.solve();
        List<JSRequirement> requirements = solved.getRequirementsList().stream().map(JSRequirement::from).collect(Collectors.toList());
        List<JSFlightGroup> flightGroups = solved.getFlightGroupsList().stream().filter(fg -> fg.getPlanned() != null && fg.getPlanned()).map(JSFlightGroup::from).collect(Collectors.toList());
        JSAllocator jsAllocator = new JSAllocator();
        jsAllocator.flightGroups = flightGroups;
        jsAllocator.requirements = requirements;
        jsAllocator.score = initScore(solved);
        jsAllocator.data = GanttDataBuilder.build(solved, includeUnplanned);
        solved.getFlightGroupsList().stream().filter(fg -> !fg.getPlanned() && !fg.getRequirementList().isEmpty()).forEach(fg -> System.out.println("<b>Flight Group - " + fg.getId() + "</b> is not planned <b>" + fg.getReason() + "</b>"));
        System.out.println(new Gson().toJson(jsAllocator));
    }


    private static JSSCore initScore(FgAllocator solved) {
        JSSCore jssCore = new JSSCore();
        jssCore.hard1 = solved.getScore().getHardScore(0);
        jssCore.soft1 = solved.getScore().getSoftScore(0);
        jssCore.soft2 = solved.getScore().getSoftScore(1);
        jssCore.soft3 = solved.getScore().getSoftScore(2);
        jssCore.soft4 = solved.getScore().getSoftScore(3);
        jssCore.time = SOLVER.getTimeSpent();
        int count = (int) solved.getFlightGroupsList().stream().filter(FlightGroup::getPlanned).count();
        jssCore.planned = count;
        jssCore.unplanned = Math.abs(solved.getFlightGroupsList().size() - count);
        return jssCore;
    }


}
