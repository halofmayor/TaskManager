
import java.time.LocalDate;


public class Task {
    //All the available Status (The complete state is activated if the task is complete and NOT removed)
    public enum Status {
        PENDING,
        FAILED,
        COMPLETE;
    }
    //Constructor
    public Task(String content, LocalDate dateStart, LocalDate dateEnd, short priority, String User,int taskID) throws Exception{
        //Checks if the Interval of the task Start->End is valid
        if(dateStart.isAfter(dateEnd)){
            throw new Exception("Invalid End Date or Start Date");
        }
        LocalDate now = LocalDate.now();
        //If the task is before today the task is already failed
        if(now.isBefore(dateEnd)) {
            this.status = Status.PENDING;
        } else {
            this.status = Status.FAILED;
        }
        this.content = content;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.priority = priority;
        this.User = User;
        this.TaskID = taskID;

    }
    private int TaskID;
    private Status status;
    private String content;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private short priority;
    private String User;

    public String getContent() {
        return content;
    }

    public short getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public String getUser() {
        return User;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public int getTaskID() {
        return TaskID;
    }
}
