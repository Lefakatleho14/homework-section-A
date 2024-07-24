package part4;

public class Task {
    private String taskName;
    private String taskDescription;
    private String developerDetails;
    private int taskDuration;
    private String taskStatus;
    private static int taskCounter = 0; // Counter to track task numbers
    private static int totalHours = 0; // Accumulator for total task hours
    private String taskID;

    public Task(String taskName, String taskDescription, String developerDetails, int taskDuration, String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.developerDetails = developerDetails;
        this.taskDuration = taskDuration;
        this.taskStatus = taskStatus;
        this.taskID = createTaskID();
        totalHours += taskDuration;
    }

    public String createTaskID() {
        taskCounter++;
        String taskNamePart = taskName.length() >= 2 ? taskName.substring(0, 2).toUpperCase() : taskName.toUpperCase();
        String developerNamePart = developerDetails.length() >= 3 ? developerDetails.substring(developerDetails.length() - 3).toUpperCase() : developerDetails.toUpperCase();
        return taskNamePart + ":" + taskCounter + ":" + developerNamePart;
    }

    public String printTaskDetails() {
        return String.format(
                "Task Name: %s%nTask Description: %s%nDeveloper Details: %s%nTask Duration: %d hours%nTask Status: %s%nTask ID: %s",
                taskName, taskDescription, developerDetails, taskDuration, taskStatus, taskID
        );
    }

    public static int returnTotalHours() {
        return totalHours;
    }

    // Getters and setters (omitted for brevity)
}
