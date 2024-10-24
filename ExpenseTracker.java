import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class ExpenseTracker {

    private List<Transaction> transactions;
    public ExpenseTracker(){
        transactions= new ArrayList<>();
    }
    public static void main(String[] args){

        
       
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();
        LocalDate date = null;
        int amount = 0;

        while (date ==null){
            try{
                System.out.println("Please enter date (YYYY-MM-DD):");
                date =LocalDate.parse(scanner.nextLine());

            }catch(DateTimeParseException e){
                System.err.println("Invalid date format, Please write date in format (YYYY-MM-DD)");

            }
        }
        while(true){
            try{
                System.out.println("Please enter the amount:");
                amount =Integer.parseInt(scanner.nextLine());
                break;
            }catch(NumberFormatException e){
                System.err.println("Invalid amount, Please enter a valid integer");
            }

        }

       

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
