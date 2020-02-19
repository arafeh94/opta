package common.gui;

import domain.Counter;
import domain.FgAllocator;
import domain.FlightGroup;
import domain.Requirement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GanttViewer {

    public static GanttViewer create(FgAllocator allocator, boolean includeUnplanned) {
        return new GanttViewer(allocator, includeUnplanned);
    }

    public static GanttViewer create(FgAllocator allocator) {
        return new GanttViewer(allocator, false);
    }

    private FgAllocator solution;
    private boolean drawn = false;
    private JFrame frame;
    private IntervalCategoryDataset dataset;
    private ChartPanel chartPanel;
    private JFreeChart chart;
    private boolean showAll = false;

    private GanttViewer(FgAllocator solution, boolean includeUnplanned) {
        this.showAll = includeUnplanned;
        this.solution = solution;
    }

    private void init() {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        for (FlightGroup flightGroup : solution.getFlightGroupsList()) {
            boolean show = showAll || flightGroup.getPlanned() != null && flightGroup.getPlanned();
            if (show) {
                TaskSeries flightGroupTasks = new TaskSeries(flightGroup.getLabel());
                for (Requirement requirement : flightGroup.getRequirementList()) {
                    flightGroupTasks.add(new Task(requirement.getCounter().getLabel(), requirement.getStartTime(), requirement.getEndTime()));
                }
                dataset.add(flightGroupTasks);
            }
        }

        this.dataset = dataset;
    }

    private void draw() {
        chart = ChartFactory.createGanttChart("Flight Group Allocation", "Counter", "Time", dataset, true, true, true);
        chartPanel = new ChartPanel(chart);
        frame = new JFrame("Counter Distribution");
        frame.setContentPane(chartPanel);
    }

    public void show() {
        invalidate();
        if (frame == null) {
            JOptionPane.showMessageDialog(null, "Error while running example", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SwingUtilities.invokeLater(() -> {
                frame.setSize(800, 400);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });
        }
    }

    private void invalidate() {
        if (!drawn) {
            drawn = true;
            init(); //convert fg allocator data to grantt data set
            draw(); //generate graph and put it into frame
        }
    }

    public void exit() {

    }

    public String save(String parent) throws IOException {
        invalidate();
        String path = parent + File.separator + "gantt.png";
        OutputStream out = new FileOutputStream(path);
        ChartUtilities.writeChartAsPNG(out, chart, 900, 500);
        return path;
    }
}
