import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Transaction{
    private String accountNumber; // account number
    private String Action; // deposit or withdraw
    private double Balance; // balance of account after action
    private String Date; // date of transaction

    public Transaction(BankAccount b, String a){ // constructor for transaction taking bankaccount and string
        this.accountNumber = b.getNum(); // sets account number to account number of b
        this.Action = a; // sets action to a
        this.Balance = b.getBalance(); // sets balance to balance of b
        Calendar date = Calendar.getInstance(); // creates date object
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy"); // creates formate of date object
        this.Date = formatter.format(date.getTime()); // sets date to date object in the format
    }

    public Transaction(String a, String b, Double c, String d){ // transaction object taking 3 strings and a double
        this.accountNumber = a; // sets account number to a
        this.Action = b; // sets action to b
        this.Balance = c; // sets balance to c
        this.Date = d; // sets date to d
    }

    public String getNum(){ // returrns account number
        return accountNumber;
    }

    public String getAction(){ // returns action
        return Action;
    }

    public double getBalance(){ // returns balance
        return Balance;
    }

    public String getDate(){ // returns date
        return Date;
    }
}