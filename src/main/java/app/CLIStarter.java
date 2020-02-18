package app;

import com.google.gson.Gson;
import common.app.FGAllocatorSolver;
import common.gui.GanttViewer;
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
            FgAllocator allocator = loadProject(fileContent.toString());
            solveProblem(allocator);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * @param fileContent
     * @return
     */
    private static FgAllocator loadProject(String fileContent) throws ParseException {
        Gson gson = new Gson();
        JSAllocator jsAllocator = gson.fromJson(fileContent, JSAllocator.class);
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
                        stringToLocalTime(jsCounter.unavailabilityPeriodStartTime),
                        stringToLocalTime(jsCounter.unavailabilityPeriodEndTime),
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
            FlightGroup flightGroup = flightGroups.stream().filter(fg -> fg.getId() == jsPreference.flight_group_id).findFirst().orElse(null);
            if (flightGroup != null) {
                zones.stream().filter(z -> z.getId() == jsPreference.zone_id).findFirst().ifPresent(zone -> {
                    flightGroup.putPreference(zone, jsPreference.points);
                });
            }
        }


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

    private static void solveProblem(FgAllocator unsolved) throws IOException {
        FgAllocator solved = FGAllocatorSolver.solve(unsolved);
        List<JSRequirement> requirements = solved.getRequirementsList().stream().map(JSRequirement::from).collect(Collectors.toList());
        List<JSFlightGroup> flightGroups = solved.getFlightGroupsList().stream().map(JSFlightGroup::from).collect(Collectors.toList());
        JSAllocator jsAllocator = new JSAllocator();
        jsAllocator.flightGroups = flightGroups;
        jsAllocator.requirements = requirements;
        jsAllocator.image = GanttViewer.create(solved).save(SAVE_PATH);
        jsAllocator.score = new JSSCore(solved.getScore().getHardScore(0), solved.getScore().getSoftScore(0), solved.getScore().getSoftScore(1), solved.getScore().getSoftScore(2), solved.getScore().getSoftScore(3));
        System.out.println(new Gson().toJson(jsAllocator));
    }


}
