package common.gui;

import domain.FgAllocator;
import domain.FlightGroup;
import domain.Requirement;

import java.util.ArrayList;

public class GanttDataBuilder {

    public static TaskSeriesCollection build(FgAllocator solved, boolean showAll) {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        for (FlightGroup flightGroup : solved.getFlightGroupsList()) {
            boolean show = showAll || flightGroup.getPlanned() != null && flightGroup.getPlanned();
            if (show) {
                TaskSeries flightGroupTasks = new TaskSeries(flightGroup.getLabel());
                for (Requirement requirement : flightGroup.getRequirementList()) {
                    flightGroupTasks.addTask(new Task(requirement.getCounter().getLabel(), requirement.getStartTime().getTime(), requirement.getEndTime().getTime()));
                }
                dataset.addTaskSeries(flightGroupTasks);
            }
        }
        return dataset;
    }

    public static class TaskSeriesCollection {
        ArrayList<TaskSeries> taskSeriesCollection;

        public TaskSeriesCollection() {
            taskSeriesCollection = new ArrayList<>();
        }

        public TaskSeriesCollection(ArrayList<TaskSeries> taskSeriesCollection) {
            this.taskSeriesCollection = taskSeriesCollection;
        }

        public void addTaskSeries(TaskSeries taskSeries) {
            this.taskSeriesCollection.add(taskSeries);
        }
    }

    public static class TaskSeries {
        private ArrayList<Task> tasks;
        private String title;

        private TaskSeries(String title) {
            this.tasks = new ArrayList<>();
            this.title = title;
        }

        private TaskSeries(String title, ArrayList<Task> tasks) {
            this.tasks = tasks;
            this.title = title;
        }

        public void addTask(Task task) {
            this.tasks.add(task);
        }
    }

    public static class Task {
        private final long start;
        private final long end;
        private final String title;

        public Task(String title, long start, long end) {
            this.start = start;
            this.end = end;
            this.title = title;
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }

        public String getTitle() {
            return title;
        }
    }
}
