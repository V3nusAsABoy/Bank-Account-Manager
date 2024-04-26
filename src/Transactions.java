import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Transactions{
    private static ArrayList<Transaction> list = new ArrayList<>(); // list of transactions

    public void addTransaction(Transaction e){ // adds transaction to list
        list.add(e);
    }

    public int getSize(){ // returns size of list
        return list.size();
    }

    public Transaction getValue(int i){ // gets value from list
        return list.get(i);
    }

    public static Transactions loadTransactionsFromFile(String filename) throws IOException{ // loads info from a csv file to an Transactions object
        List<List<String>> lines = new ArrayList<>(); // initializes arraylist containing arraylists of strings
        Transactions a = new Transactions(); // initializes new Transactions object a
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

        for(int i = 1; i < lines.size(); i++){ // adds each transaction in csv file to a
            a.addTransaction(new Transaction((lines.get(i).get(0)), lines.get(i).get(1), Double.parseDouble(lines.get(i).get(2)), lines.get(i).get(3)));
        }
        return a; // returrns a
    }
}