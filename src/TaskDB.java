import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDB {
    private static final String URL = "jdbc:mysql://localhost:3306/TaskManager";
    private static final String USER = "root";
    private static final String PASSWORD = "NotAPassword";

    protected static void addToDB(Task task) throws SQLException {
        //Writes in SQL, the ? are placeholders
        String sql = "INSERT INTO tasks (priority, content, user, dateStart, dateEnd, status, taskID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        //tries to establish connection with the database using the authentication methods
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

             //prepares the object with the placeholders to be sent later
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //set the placeholders
            statement.setShort(1, task.getPriority());
            statement.setString(2, task.getContent());
            statement.setString(3, task.getUser());
            statement.setDate(4, java.sql.Date.valueOf(task.getDateStart()));
            statement.setDate(5, java.sql.Date.valueOf(task.getDateEnd()));
            statement.setString(6, task.getStatus().name());
            statement.setInt(7, task.getTaskID());

            //execute the command
            statement.executeUpdate();
            System.out.println("Task added successfully!");
        }
    }

    protected static void deleteDB(int TaskID) throws SQLException {
        //Writes in SQL, the ? are placeholder
        String sql = "DELETE FROM Tasks WHERE TaskID = ?";
        //tries to establish connection with the database using the authentication methods
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

             //prepares the object with the placeholder to be sent later
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //set the placeholder
            statement.setInt(1, TaskID);

            //execute the command
            statement.executeUpdate();
            System.out.println("Task removed successfully!");
        }
    }

    protected static void completeTask(int TaskID){
        //Writes in SQL, the ? are placeholders
        String updateSql = "UPDATE tasks SET status = ? WHERE TaskID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(updateSql)) {

            // Substitutes the placeholders
            statement.setString(1, "COMPLETE");
            statement.setInt(2, TaskID);

            // Execute the update
            int affectedRows = statement.executeUpdate();

            // Checks if the task was updated and prints the result
            if (affectedRows > 0) {
                System.out.println("Task status updated successfully.");
            } else {
                System.out.println("Task not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static List<String[]> getTasks(String selectSql, String... params) {
        List<String[]> resultList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {

            // Set parameters if any
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }

            try (ResultSet result = pstmt.executeQuery()) {

                // Get column names
                ResultSetMetaData metaData = result.getMetaData();
                int columnsNumber = metaData.getColumnCount();
                String[] columnNames = new String[columnsNumber];

                for (int i = 1; i <= columnsNumber; i++) {
                    columnNames[i - 1] = metaData.getColumnName(i);
                }
                resultList.add(columnNames);

                // Get rows
                while (result.next()) {
                    String[] rowData = new String[columnsNumber];
                    for (int i = 1; i <= columnsNumber; i++) {
                        rowData[i - 1] = result.getString(i);
                    }
                    resultList.add(rowData);
                }

            }
        }
            catch (SQLException e) {
            e.printStackTrace();
            }
        return resultList;
    }
    //I was thinking in a implementation of checking if a task is pending or failed already but it was not worth to spend more time in this project
}


