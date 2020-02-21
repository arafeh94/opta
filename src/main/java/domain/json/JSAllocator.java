package domain.json;

import com.sun.org.apache.bcel.internal.generic.JSR;
import common.gui.GanttDataBuilder;
import domain.Belt;
import domain.Range;
import domain.Terminal;
import org.optaplanner.core.api.score.Score;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JSAllocator {
    public List<JSZone> zones;
    public List<JSCounter> counters;
    public List<JSPreference> preferences;
    public List<JSRequirement> requirements;
    public List<JSFlightGroup> flightGroups;
    public List<JSTerminal> terminals;
    public List<JSRange> ranges;
    public List<JSBelt> belts;
    public List<JSConjunction> conjunctions;
    public JSSCore score;
    public int includeUnplanned;
    public GanttDataBuilder.TaskSeriesCollection data;
}
