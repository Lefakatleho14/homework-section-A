package part4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class Welcome extends JFrame {
    private String[] developers = new String[100];
    private String[] taskNames = new String[100];
    private String[] taskIDs = new String[100];
    private int[] taskDurations = new int[100];
    private String[] taskStatuses = new String[100];
    private int taskCounter = 0; // Counter to track task numbers
    private Scanner scanner = new Scanner(System.in);

    public Welcome() {
        setTitle("EasyKanban");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Set BorderLayout for panel

        JLabel welcomeLabel = new JLabel("Welcome to EasyKanban");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text horizontally
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and size

        panel.add(welcomeLabel, BorderLayout.NORTH); // Add label to top of panel

        // Create a custom menu panel
        JPanel menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Create and add numeric menu items
        JButton addTasksButton = new JButton("1) Add tasks");
        addTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddTaskDialog();
            }
        });
        menuPanel.add(addTasksButton, gbc);

        JButton showReportButton = new JButton("2) Show report");
        showReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportMenu();
            }
        });
        menuPanel.add(showReportButton, gbc);

        JButton quitButton = new JButton("3) Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Quit the application
                System.exit(0);
            }
        });
        menuPanel.add(quitButton, gbc);

        panel.add(menuPanel, BorderLayout.CENTER); // Add menu panel to center of the main panel

        add(panel);
    }

    private void showAddTaskDialog() {
        System.out.print("Enter the number of tasks: ");
        String input = scanner.nextLine();
        try {
            int numTasks = Integer.parseInt(input);
            if (numTasks > 0) {
                for (int i = 0; i < numTasks; i++) {
                    System.out.println("Adding Task " + (i + 1));
                    System.out.print("Task Name: ");
                    String taskName = scanner.nextLine();
                    System.out.print("Task Description: ");
                    String taskDescription = scanner.nextLine();
                    System.out.print("Developer Details: ");
                    String developerDetails = scanner.nextLine();
                    System.out.print("Task Duration (hours): ");
                    String durationInput = scanner.nextLine();
                    System.out.print("Task Status (To Do, Done, Doing): ");
                    String taskStatus = scanner.nextLine();

                    if (taskName.isEmpty() || taskDescription.isEmpty() || developerDetails.isEmpty() || durationInput.isEmpty() || taskStatus.isEmpty()) {
                        System.out.println("All fields are required.");
                        return;
                    }

                    int taskDuration;
                    try {
                        taskDuration = Integer.parseInt(durationInput);
                    } catch (NumberFormatException ex) {
                        System.out.println("Please enter a valid number for task duration.");
                        return;
                    }

                    Task task = new Task(taskName, taskDescription, developerDetails, taskDuration, taskStatus);
                    developers[taskCounter] = developerDetails;
                    taskNames[taskCounter] = taskName;
                    taskIDs[taskCounter] = task.createTaskID();
                    taskDurations[taskCounter] = taskDuration;
                    taskStatuses[taskCounter] = taskStatus;

                    taskCounter++; // Increment the task counter for the next task

                    System.out.println(task.printTaskDetails());
                    System.out.println("Task successfully captured");
                }
            } else {
                System.out.println("Number of tasks must be greater than zero.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void showReportMenu() {
        while (true) {
            System.out.println("\nReport Menu:");
            System.out.println("1) Show Full Task Report");
            System.out.println("2) Search Task by Name");
            System.out.println("3) Search Tasks by Developer");
            System.out.println("4) Delete Task by Name");
            System.out.println("5) Show Task with Longest Hours");
            System.out.println("6) Back to Main Menu");
            System.out.print("Select an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    showReport();
                    break;
                case "2":
                    System.out.print("Enter the task name to search: ");
                    String taskName = scanner.nextLine();
                    if (!taskName.trim().isEmpty()) {
                        searchTaskByName(taskName.trim());
                    } else {
                        System.out.println("Task name cannot be empty.");
                    }
                    break;
                case "3":
                    System.out.print("Enter the developer name to search: ");
                    String developerName = scanner.nextLine();
                    if (!developerName.trim().isEmpty()) {
                        searchTasksByDeveloper(developerName.trim());
                    } else {
                        System.out.println("Developer name cannot be empty.");
                    }
                    break;
                case "4":
                    System.out.print("Enter the task name to delete: ");
                    String taskToDelete = scanner.nextLine();
                    if (!taskToDelete.trim().isEmpty()) {
                        deleteTaskByName(taskToDelete.trim());
                    } else {
                        System.out.println("Task name cannot be empty.");
                    }
                    break;
                case "5":
                    showLongestTask();
                    break;
                case "6":
                    return; // Exit the report menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showReport() {
        StringBuilder report = new StringBuilder();
        report.append("Task Report:\n");
        for (int i = 0; i < taskCounter; i++) {
            report.append("Task Name: ").append(taskNames[i]).append("\n");
            report.append("Task ID: ").append(taskIDs[i]).append("\n");
            report.append("Developer: ").append(developers[i]).append("\n");
            report.append("Task Duration: ").append(taskDurations[i]).append(" hours\n");
            report.append("Task Status: ").append(taskStatuses[i]).append("\n\n");
        }
        System.out.println(report.toString());
    }

    private void searchTaskByName(String taskName) {
        StringBuilder result = new StringBuilder();
        result.append("Search Results for Task: ").append(taskName).append("\n");
        boolean found = false;
        for (int i = 0; i < taskCounter; i++) {
            if (taskNames[i].equalsIgnoreCase(taskName)) {
                result.append("Task Name: ").append(taskNames[i]).append("\n");
                result.append("Developer: ").append(developers[i]).append("\n");
                result.append("Task Status: ").append(taskStatuses[i]).append("\n\n");
                found = true;
            }
        }
        if (found) {
            System.out.println(result.toString());
        } else {
            System.out.println("Task '" + taskName + "' not found.");
        }
    }

    private void searchTasksByDeveloper(String developerName) {
        StringBuilder result = new StringBuilder();
        result.append("Search Results for Developer: ").append(developerName).append("\n");
        boolean found = false;
        for (int i = 0; i < taskCounter; i++) {
            if (developers[i].equalsIgnoreCase(developerName)) {
                result.append("Task Name: ").append(taskNames[i]).append("\n");
                result.append("Developer: ").append(developers[i]).append("\n");
                result.append("Task Status: ").append(taskStatuses[i]).append("\n\n");
                found = true;
            }
        }
        if (found) {
            System.out.println(result.toString());
        } else {
            System.out.println("No tasks found for developer '" + developerName + "'.");
        }
    }

    private void deleteTaskByName(String taskName) {
        for (int i = 0; i < taskCounter; i++) {
            if (taskNames[i].equalsIgnoreCase(taskName)) {
                for (int j = i; j < taskCounter - 1; j++) {
                    taskNames[j] = taskNames[j + 1];
                    developers[j] = developers[j + 1];
                    taskIDs[j] = taskIDs[j + 1];
                    taskDurations[j] = taskDurations[j + 1];
                    taskStatuses[j] = taskStatuses[j + 1];
                }
                taskCounter--;
                System.out.println("Task '" + taskName + "' deleted successfully.");
                return;
            }
        }
        System.out.println("Task '" + taskName + "' not found.");
    }

    private void showLongestTask() {
        if (taskCounter == 0) {
            System.out.println("No tasks available.");
            return;
        }
        int maxDurationIndex = 0;
        for (int i = 1; i < taskCounter; i++) {
            if (taskDurations[i] > taskDurations[maxDurationIndex]) {
                maxDurationIndex = i;
            }
        }
        StringBuilder longestTask = new StringBuilder();
        longestTask.append("Task with Longest Hours:\n");
        longestTask.append("Task Name: ").append(taskNames[maxDurationIndex]).append("\n");
        longestTask.append("Developer: ").append(developers[maxDurationIndex]).append("\n");
        longestTask.append("Task Duration: ").append(taskDurations[maxDurationIndex]).append(" hours\n");
        longestTask.append("Task Status: ").append(taskStatuses[maxDurationIndex]).append("\n");

        System.out.println(longestTask.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Welcome().setVisible(true);
            }
        });

        Welcome welcomeApp = new Welcome();

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1) Add tasks");
            System.out.println("2) Show report");
            System.out.println("3) Quit");
            System.out.print("Select an option: ");
            String input = welcomeApp.scanner.nextLine();

            switch (input) {
                case "1":
                    welcomeApp.showAddTaskDialog();
                    break;
                case "2":
                    welcomeApp.showReportMenu();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
