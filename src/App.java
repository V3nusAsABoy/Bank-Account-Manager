import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class App {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException {
        String path = "Accounts.csv"; // Accounts csv file path string
        String path2 = "Transactions.csv"; // Transactions csv file path string
        Accounts accList = Accounts.loadAccountsFromFile(path); // Loads contents of Accounts.csv to Accounts object
        Transactions transactionList = Transactions.loadTransactionsFromFile(path2); // Loads contents of Transactions.csv to Transactions object

        JFrame accounts = new JFrame("Accounts manager"); // JFrame of main program
        accounts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ends program when frame is closed
        accounts.setBounds(200, 100, 100, 50); // sets bounds of frame
        accounts.setLayout(new BorderLayout()); // sets borderlayout of frame
        accounts.setMinimumSize(new Dimension(500, 440)); // sets minimum size of frame to 500x440

        JButton create = new JButton(); // button for creating account
        create.setForeground(Color.red); // sets color of text to red
        create.setPreferredSize(new Dimension(500,75)); // sets preferred size to 500x75
        create.setLabel("Create account"); // sets label to "Create account"
        create.addActionListener(new ActionListener() { // sets action of button
            public void actionPerformed(ActionEvent e){
                String Name = JOptionPane.showInputDialog("What is the account holder's name?"); // asks for name of account holder
                String initialAmt = JOptionPane.showInputDialog("What is the initial amount you want to deposit?"); // asks for initial deposit amount
                String[] options = new String[] {"Savings", "Checkings"}; // options for savings or checkings to be loaded into Type JOptionPane
                int Type = JOptionPane.showOptionDialog(null, "Is this a Checkings or Savings account?", "Title",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]); // gives user 2 buttons. One for checkings, one for savings
                try (FileWriter fw = new FileWriter(path, true)) { // appends contents of new account to Accounts.csv
                    int ID = randomID(accList);
                    fw.append("\n" + String.valueOf(ID));
                    fw.append(", ");
                    fw.append(Name);
                    fw.append(", ");
                    if(Type == 0)
                        fw.append("Savings");
                    else
                        fw.append("Checkings");
                    fw.append(", ");
                    fw.append(initialAmt);
                    fw.flush();
                    fw.close();

                    accList.AddAccount(new BankAccount(String.valueOf(ID), Name, options[Type], 0.0)); // adds contents of new account to accList

                } catch (IOException e1) {
                    e1.printStackTrace(); // prints stack trace in case of IOException
                }
            }
        });

        Button deposit = new Button(); // new button for depositing money into accounts
        deposit.setPreferredSize(new Dimension(500,75)); // sets dimensions of new button to 500x75
        deposit.setLabel("Deposit"); // sets text of button to "Deposit"
        deposit.setForeground(Color.green.darker()); // sets color of button to green
        deposit.addActionListener(new ActionListener() { // adds action to deposit button
            public void actionPerformed(ActionEvent e){
                String accNum = JOptionPane.showInputDialog("Type the number of the account you want to deposit into"); // asks user for account number to deposit into
                int depositAmt = Integer.parseInt(JOptionPane.showInputDialog("What is the amount you would like to deposit?")); // asks user for amount of money to deposit
                try {
                    depowithdraw(path, path2, accNum, depositAmt, false, accList, transactionList); // method for depositing money into account
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace(); // prints stack trace in case of IOException
                }
            }
        });

        Button withdraw = new Button(); // creats new button for withdrawing money from account
        withdraw.setPreferredSize(new Dimension(500,75)); // sets size of button to 500x75
        withdraw.setLabel("Withdraw"); // sets text of button to "Withdraw"
        withdraw.setForeground(Color.blue); // sets color of button text to blue
        withdraw.addActionListener(new ActionListener() { // gives action to withdraw button
            public void actionPerformed(ActionEvent e){
                String accNum = JOptionPane.showInputDialog("Type the number of the account you want to withdraw from"); // asks user for number of account to withdraw from
                int depositAmt = Integer.parseInt(JOptionPane.showInputDialog("What is the amount you would like to withdraw?")); // asks user for amount to withdraw
                try {
                    depowithdraw(path, path2, accNum, depositAmt, true, accList, transactionList); // method for withdrawing money from account
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace(); // prints stack trace in case of IOException
                }
            }
        });

        Button inquiry = new Button(); // Button for inquiring balance of account
        inquiry.setForeground(Color.ORANGE); // sets text color to orange
        inquiry.addActionListener(new ActionListener(){ // adds action to inquiry account
            public void actionPerformed(ActionEvent e){
                Boolean found = false; // creates boolean of whether or not account is found
                while(found == false){ // traverses through accList until account is found
                    String accNum = JOptionPane.showInputDialog("Type the number of the account you would like to see the balance of"); // asks use for account number to inquire
                    for(int i = 0; i < accList.getSize(); i++){
                        if(accList.getValue(i).getNum().equals(accNum)){
                            JOptionPane.showMessageDialog(null, "Your balance is " + accList.getValue(i).getBalance()); // shows balance of account
                            found = true; // shows account is found, ending loop
                        }
                    }
                    if(found == false)
                        JOptionPane.showMessageDialog(null, "Account not found."); // displays message that account is not found
                }
            }
        });
        inquiry.setPreferredSize(new Dimension(500,75)); // sets dimensions of inquiry to 500x75
        inquiry.setLabel("Inquiry"); // sets text of inquiry to "Inquiry"

        Button history = new Button(); // creates button to see history of account
        history.setForeground(Color.pink); // sets color of text of button to pink
        history.setPreferredSize(new Dimension(500,75)); // sets dimensions of button to 500x75
        history.setLabel("History"); // sets text of button to "History"
        JTextPane transactionHistory = new JTextPane(); // creates text pane containing transaction history
        JFrame historyFrame = new JFrame(); // creates frame to contain text pane
        historyFrame.setLayout(new BorderLayout()); // sets layout of history frame
        historyFrame.add(transactionHistory, BorderLayout.CENTER); // adds text pane to history frame
        historyFrame.setMinimumSize(new Dimension(300, 300)); // sets size of history frame to 300x300
        history.addActionListener(new ActionListener() { // adds action to history button
            public void actionPerformed(ActionEvent e){
                Boolean found = false; // boolean indicating if account has been found
                String accNum = JOptionPane.showInputDialog(null, "What's the number of the account you want to see the history of?"); // asks user for account number to see history of
                transactionHistory.setText(""); // sets default value of text for pane
                String transactionsByNumber = ""; // sets default value for text for pane that will be set later
                for(int i = 0; i < transactionList.getSize(); i++){ // traverses transaction list to find every transaction for the account number, adding it to transactionsByNumber string
                    if(transactionList.getValue(i).getNum().equals(accNum)){
                        transactionsByNumber = transactionsByNumber + "\n" + transactionList.getValue(i).getNum() + " " + transactionList.getValue(i).getAction() + " " + transactionList.getValue(i).getBalance() + " " + transactionList.getValue(i).getDate();
                        found = true; // shows account has been found
                    }
                }
                transactionHistory.setText(transactionsByNumber); // seets text of pane to transactionsByNumber
                if(found == false)
                    JOptionPane.showMessageDialog(null, "Account not found."); // shows account hasn't been found if found == false
                else
                    historyFrame.setVisible(true); // if account has been found, it sets the history frame to be visible
            }
        });

        JPanel panel = new JPanel(); // panel containing all the buttons

        // adds all buttons to panel
        panel.add(create);
        panel.add(deposit);
        panel.add(withdraw);
        panel.add(inquiry);
        panel.add(history);
        accounts.add(panel);

        accounts.setVisible(true); // makes panel visible
    }
    
    // method of depositing into or withdrawing money from account
    public static void depowithdraw(String path, String path2, String accNum, int amt, Boolean withdraw, Accounts a, Transactions ts) throws IOException{
        Boolean found = false; // booleaen indicating if account has been found
        for(int i = 0; i < a.getSize(); i++){ // traverses through account list to find the account
            if(a.getValue(i).getNum().equals(accNum)){
                if(withdraw == true){
                    a.getValue(i).withdraw(amt); // withdraws money from account if withdraw == true
                    Transaction t = new Transaction(a.getValue(i), "Withdraw"); // creates transaction object t showing the transaction
                    if(amt < a.getValue(i).getBalance() + 1)
                        updateTransaction(t, ts, path2); // adds the transaction to transactions object and Transactions.csv
                    found = true; // shows the account has been found
                }
                else{
                    a.getValue(i).deposit(amt); // deposits money into account if withdraw == true
                    Transaction t = new Transaction(a.getValue(i), "Deposit"); // creates transaction object t showing the transaction
                    if(amt < 1000001)
                        updateTransaction(t, ts, path2); // adds the transaction to transactions object and Transactions.csv
                    found = true; // shows the account has been found
                }
            }
        }
        if(found == false) // if account hasn't been found, shows error message
            JOptionPane.showMessageDialog(null, "Account not found");
        else{ // if account has been found, overwrites content of Accounts.csv with new data
            FileWriter fw2  = new FileWriter(path, false);
            fw2.append("ID");
            fw2.append(", ");
            fw2.append("Name");
            fw2.append(", ");
            fw2.append("Type");
            fw2.append(", ");
            fw2.append("Balance");
            for(int i = 0; i < a.getSize(); i++){
                fw2.append("\n" + a.getValue(i).getNum());
                fw2.append(", ");
                fw2.append(a.getValue(i).getHolderName());
                fw2.append(", ");
                fw2.append(a.getValue(i).getType());
                fw2.append(", ");
                fw2.append(String.valueOf(a.getValue(i).getBalance()));
            }
            fw2.flush();
            fw2.close();
        }
    }

    // for adding new transaction to transactions object and appending it to Transactions.csv
    public static void updateTransaction(Transaction t, Transactions ts, String path) throws IOException{
        ts.addTransaction(t); // adds transaction to Transactions object

        // appends contents of transaction to Transactions.csv
        FileWriter fw = new FileWriter(path, true);
        fw.append("\n" + t.getNum());
        fw.append(", ");
        fw.append(t.getAction());
        fw.append(", ");
        fw.append(String.valueOf(t.getBalance()));
        fw.append(", ");
        fw.append(t.getDate());
        fw.flush();
        fw.close();
    }

    // generates random 6 digit number for randomly generated account ID
    public static int randomID(Accounts e){
        int ID = new Random().nextInt(900000) + 100000; // generates ID
        for(int i = 0; i < e.getSize(); i++){ // increments ID if the ID is the same as another
            if(ID == Integer.parseInt(e.getValue(i).getNum()))
                ID++;
        }
        JOptionPane.showMessageDialog(null, "Successfully added! Your account ID is " + ID); // shows success message
        return ID; // returns ID
    }
}
