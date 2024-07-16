import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TaskActions {


    //Function to create the desired task
    static public Task addTask(String content, LocalDate dateStart, LocalDate dateEnd, short priority, String User) throws Exception {
        List<String[]> tasks = TaskDB.getTasks("SELECT * FROM Tasks");
        //If there is not any element it will start at taskID 0
        if(tasks.size() <= 1){
            int TaskID = 0;
            return new Task(content, dateStart, dateEnd, priority, User,TaskID);
        }
            //This assigns to TaskID last element of String[] which is the last String[] of the arraylist
            String TaskID = tasks.get(tasks.size() - 1)[tasks.get(tasks.size() - 1).length - 1];
            return new Task(content, dateStart, dateEnd, priority, User,Integer.parseInt(TaskID) + 1);
    }

    public static void printAllTasks() {
        List<String[]> tasks = TaskDB.getTasks("SELECT * FROM Tasks");
        int i = 0;
        //Print data (cycles in every String[] in the arraylist and in every element of every String[])
        if (!(tasks.size() <= 1)) {
            for (String[] data : tasks) {
                if(i != 0){
                    System.out.print(i + "." + "\t");
                } else {
                    System.out.print("Number\t");
                }
                i++;
                for (String info : data) {
                    System.out.print(info + "\t");

                }
                System.out.println();
            }

        }
    }

    public static void printUserTasks(String user) {
        List<String[]> tasks = TaskDB.getTasks("SELECT * FROM Tasks WHERE User = ?", user);
        // Print data (cycles in every String[] in the arraylist and in every element of every String[])
        if (!tasks.isEmpty()) {
            for (String[] data : tasks) {
                for (String info : data) {
                    System.out.print(info + "\t");
                }
                System.out.println();
            }

        }
    }

    private static int getTaskID(int index,String user){
        List<String[]> tasks = TaskDB.getTasks("SELECT * FROM Tasks WHERE User = ?", user);
        if(index > tasks.size()){
            System.out.println("Option not valid");
        }
        int TaskID = Integer.parseInt(tasks.get(index)[6]);
        return TaskID;
    }

    public static void completeTask(int index,String user){
        TaskDB.completeTask(getTaskID(index, user));
    }

    public static void deleteTask(int index,String user) throws SQLException {
        TaskDB.deleteDB(getTaskID(index, user));
    }
}
