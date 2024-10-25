import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class ExpenseTracker {

    private List<Transaction> transactions;
    public ExpenseTracker(){
        transactions= new ArrayList<>();
    }

    public void addIncome(LocalDate date, int amount, String category){
        Transaction income = new Transaction(date,amount,category,"income");
        transactions.add(income);
    }
    public void addExpense(LocalDate date, int amount, String category){
        Transaction expense= new Transaction(date,amount,category,"expense");
        transactions.add(expense);
    }
    public void addCategory(String category){
        //
    }
    public void displayAllTransactions() {
        for (Transaction t : transactions) {
            System.out.println(t);  
        }
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
    

    public void filterTransactionsByCategory(String category) {
        for (Transaction t : transactions) {
            if (t.getCategory().equals(category)) {
                System.out.println(t);  
            }
        }
    }
    

    public void showTotalByCategory(String category) {
        int total = 0;
        for (Transaction t : transactions) {
            if (t.getCategory().equals(category)) {
                total += t.getAmount(); 
        }
        System.out.println("Total for category " + category + ": " + total);
    }
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

        System.out.println("Please enter the type:Income or expense");
        String type=scanner.nextLine();
        if(type.equalsIgnoreCase("income")){
            tracker.addIncome(date,amount,category);
        }else if(type.equalsIgnoreCase("expense")){
            tracker.addExpense(date, amount, category);
        }else{
            System.out.println("Invalid transaction type. Please enter either 'income' or 'expense'.");
        }



        System.out.println("Your transaction is saved!");

        scanner.close();

       
        

    }
}
