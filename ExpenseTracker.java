import java.util.*;
import java.time.LocalDate;


public class ExpenseTracker {

    private List<Transaction> transactions;
    public ExpenseTracker(){
        transactions= new ArrayList<>();
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();

        System.out.println("Please enter date (YYYY-MM-DD):");
        LocalDate date =LocalDate.parse(scanner.nextLine());

        System.out.println("Please enter the amount:");
        int amount =Integer.parseInt(scanner.nextLine());

        System.out.println("Please enter the category");
        String category=scanner.nextLine();

        System.out.println("Please enter the type");
        String type=scanner.nextLine();

        Transaction transaction = new Transaction(date,amount,category,type);
        tracker.transactions.add(transaction);

        System.out.println("Your transaction" + transaction);

        scanner.close();

    }
}
