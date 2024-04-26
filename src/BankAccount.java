import javax.swing.JOptionPane;

public class BankAccount {
    private String accountNumber; // account number
    private String accountHolderName; // name of account holder
    private String accountType; // type of account (saving or checking)
    private double balance; // balance in account
    public BankAccount(String AccNum, String AccHolderName, String AccType, Double Balance){ // constructor taking number, holder, type, and balance
        this.accountNumber = AccNum;
        this.accountHolderName = AccHolderName;
        this.accountType = AccType;
        this.balance = Balance;
    }

    @Override
    public String toString(){ // toString method printing number, holder name, type, and balance
        return String.format("Number: " + accountNumber + " Holder name: " + accountHolderName + " Type: " + accountType + " Balance: " + balance + "$");
    }

    public String getNum(){ // get method for account number
        return accountNumber;
    }

    public String getHolderName(){
        return accountHolderName;
    }

    public String getType(){
        return accountType;
    }

    public double getBalance(){
        return balance;
    }

    public void deposit(double depositAmount){ // deposits amount to account
        if(depositAmount > 1000000)
            JOptionPane.showMessageDialog(null, "Limit exceeded! Must be under 1000000$");
        else{
            balance+=depositAmount;
            JOptionPane.showMessageDialog(null, "Success! Your new balance is: " + balance + "$");
        }
    }

    public void withdraw(double withdrawAmount){ // withdraws money from account
        if((balance - withdrawAmount) < 0) // checks if balance is sufficient
            JOptionPane.showMessageDialog(null, "Insufficient funds.");
        else
        {
            balance-=withdrawAmount;
            JOptionPane.showMessageDialog(null,"Success! your new balance is: " + balance + "$");
        }
    }
}
