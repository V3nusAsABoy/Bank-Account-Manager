import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Accounts{
    private static ArrayList<BankAccount> list = new ArrayList<>(); // list contianing bank accounts

    public void AddAccount(BankAccount b){ // adds account to list
        list.add(b);
    }

    public static int getSize(){ // returns size of the list
        return list.size();
    }

    public BankAccount getValue(int i){ // returns the value at the index point i
        return list.get(i);
    }

    public Boolean exists(int i){ // check if an account exists based on account number
        Boolean exists = false; // initializes boolean exists set to false

        // traverses through array and finds an account that has the account number matching the int inputted
        for(int j = 0; j < list.size(); j++){
            if(Integer.parseInt(list.get(j).getNum()) == i)
                exists = true;
        }
        return exists; // returns exists
    }

     public static Accounts loadAccountsFromFile(String filename) throws IOException{ // loads info from a csv file to an accounts object
        List<List<String>> lines = new ArrayList<>(); // initializes arraylist containing arraylists of strings
        Accounts a = new Accounts(); // initializes new accounts object a
        String line = ""; // empty string to read lines in csv

        try{
            BufferedReader br = new BufferedReader(new FileReader(filename)); // initializes buffered reader to read csv file

            // adds lines in csv file to string array values
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                // adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }
        }catch (FileNotFoundException e) { // prints stack trace if FileNotFoundException is caught
            e.printStackTrace();
        }

        for(int i = 1; i < lines.size(); i++){ // adds each account in csv file to a
            a.AddAccount(new BankAccount((lines.get(i).get(0)), lines.get(i).get(1), lines.get(i).get(2), Double.parseDouble(lines.get(i).get(3))));
        }
        return a; // returrns a
    }

    public void printAll(){ // prints all values in accounts object
        for(BankAccount B: list)
            System.out.println(B.toString());
    }

    public void searchAccount(int NumAcc){ // displays info of account based on input of account number
        for(BankAccount B: list){
            if(Integer.parseInt(B.getNum()) == NumAcc)
                System.out.println(B.toString());
        }
    }
}