import java.util.PriorityQueue;

public class TaskScheduler {
    private PriorityQueue<Task> taskQueue = new PriorityQueue<>();

    public void addTask(Task task) {
        taskQueue.offer(task);
    }

    public Task executeNextTask() {
        return taskQueue.poll();
    }

    public Object[][] getAllTasks() {
        Object[][] data = new Object[taskQueue.size()][2];
        int i = 0;
        for (Task t : taskQueue) {
            data[i][0] = t.getName();
            data[i][1] = t.getPriority();
            i++;
        }
        return data;
    }
}