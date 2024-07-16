
import java.time.LocalDate;
import java.util.Scanner;

//falta dar update periodico a verificar se a tarefa ja ultrapassou o dia fornecido
//falta adicionar a opcao de remocao e de completar tambem
//criar uma "interface" no terminal

public class TaskManager {

    public static int gettingNums(String askedNum, Scanner scanner){
        while (true) {
            try {
                System.out.println(askedNum);
                int num = Integer.parseInt(scanner.nextLine());
                return num;
            } catch (NumberFormatException e) {
                System.out.println("The given number is not a Integer");
            }
        }
    }


    public static void main(String[] args) throws Exception {
        boolean menu = true;
        Scanner scanner = new Scanner(System.in);
        System.out.print("User: ");
        String user = scanner.nextLine().replaceAll("\\s", "").toLowerCase();

        System.out.println("Hello " + user + "!");
        while(menu){
            System.out.println("1. Add task");
            System.out.println("2. Remove a task");
            System.out.println("3. Complete task");
            System.out.println("4. Show my tasks");
            System.out.println("5. Show all tasks");
            System.out.println("6. Exit");
            String option = scanner.nextLine();
            switch(option) {
                case "1":
                    System.out.println("What are you going to need to do in your task?");
                    String content = scanner.nextLine();

                    short priority = (short) gettingNums("Define the priority of this task (numbers 0-X)",scanner);
                    System.out.println("When are you going to start?");

                    int day = gettingNums("Day: ",scanner);
                    int month = gettingNums("Month: ",scanner);
                    int year = gettingNums("Year: ",scanner);
                    LocalDate startDate = LocalDate.of(year,month, day);

                    System.out.println("When are you going to end?");
                    day = gettingNums("Day: ",scanner);
                    month = gettingNums("Month: ",scanner);
                    year = gettingNums("Year: ",scanner);

                    LocalDate endDate = LocalDate.of(year,month, day);

                    TaskDB.addToDB(TaskActions.addTask(content, startDate, endDate, priority, user));

                    break;
                case "2":
                    TaskActions.printUserTasks(user);
                    System.out.println("Choose a task to remove: ");
                    int taskRemove = gettingNums("Choose an option.",scanner);
                    TaskActions.deleteTask(taskRemove,user);
                    break;
                case "3":
                    TaskActions.printUserTasks(user);
                    System.out.println("Choose a task to complete: ");
                    int taskComplete = gettingNums("Choose an option.",scanner);
                    TaskActions.deleteTask(taskComplete,user);
                    break;
                case "4":
                    TaskActions.printUserTasks(user);
                    break;
                case "5":
                    TaskActions.printAllTasks();
                    break;
                case "6":
                    System.out.println("Goodbye");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option not valid.");
            }


        }
    }
}
