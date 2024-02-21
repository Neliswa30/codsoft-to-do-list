public class Task {
    private String title;
    private boolean isCompleted;
    private boolean isImportant;
    private boolean isPersonal;
    private String dueDate;

    // Constructors, getters, and setters

    public Task(String title, boolean isCompleted, boolean isImportant, boolean isPersonal, String dueDate) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.isImportant = isImportant;
        this.isPersonal = isPersonal;
        this.dueDate = dueDate;
    }
}

