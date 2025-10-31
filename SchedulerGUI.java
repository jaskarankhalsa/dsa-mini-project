import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class SchedulerGUI extends JFrame {
    private TaskScheduler scheduler = new TaskScheduler();
    private DefaultTableModel model = new DefaultTableModel(new String[]{"Name", "Priority"}, 0);

    public SchedulerGUI() {
        super("Priority Queue Job Scheduler");

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JTextField nameField = new JTextField(10);
        JTextField priorityField = new JTextField(5);
        JButton addButton = new JButton("Add Task");
        JButton executeButton = new JButton("Execute Next Task");
        JLabel statusLabel = new JLabel(" ");

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            int priority;
            try {
                priority = Integer.parseInt(priorityField.getText().trim());
            } catch (NumberFormatException ex) {
                statusLabel.setText("Priority must be an integer.");
                return;
            }
            scheduler.addTask(new Task(name, priority));
            updateTable();
            nameField.setText("");
            priorityField.setText("");
        });

        executeButton.addActionListener(e -> {
            Task task = scheduler.executeNextTask();
            if (task != null) {
                statusLabel.setText("Executed: " + task.getName() + " (" + task.getPriority() + ")");
            } else {
                statusLabel.setText("No tasks to execute.");
            }
            updateTable();
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Task Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityField);
        inputPanel.add(addButton);
        inputPanel.add(executeButton);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTable() {
        Object[][] tasks = scheduler.getAllTasks();
        model.setRowCount(0);
        for (Object[] row : tasks) {
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SchedulerGUI::new);
    }
}