package CreditUnion;

/*
Programmer: Robert Kacso
Student ID: B00123508
Note: Software Development 2, Assignment 2 - Credit Union program
Due date: 27/04/2020
*/

import java.io.*;

// main class to create a record
public class Record
{
    // declaring variables
    private int accountNo;
    private String lastName;
    private String firstName;
    private double balance, overdraft;
    public RandomAccessFile randAccFile;

    //==============================================================================================
    // method to read a record with a input/output exception
    public void read( RandomAccessFile file ) throws IOException
    {
        accountNo = file.readInt(); // initialize variable accountNo and read it in

        //----------------------------------------
        char[] first = new char[15]; // array for the first name of max 15 characters

        for ( int i=0; i < first.length; i++ ) // "for" loop to read in all characters
            first[ i ] = file.readChar();

        firstName = new String (first); // initialize variable firstName and read it in

        //----------------------------------------
        char[] last = new char[15]; // array for the last name of max 15 characters

        for (int i =0; i<last.length; i++) // "for" loop to read in all characters
            last[i] = file.readChar();

        lastName = new String (last); // initialize variable lastName and read it in

        //----------------------------------------
        balance = file.readDouble(); // initialize variable balance and read it in

        //----------------------------------------
        overdraft = file.readDouble(); // initialize variable overdraft and read it in
    }

    //==============================================================================================
    // method to write to a record with a input/output exception
    public void write( RandomAccessFile file) throws IOException
    {
        StringBuffer buf; // declaring StringBuffer "buf" to append, reverse, replace, concatenate and manipulate Strings or sequence of characters

        file.writeInt( accountNo ); // write account number inputted to file in the record

        //----------------------------------------
        // if/else statement to help append, reverse, replace, concatenate and manipulate variable firstName type Strings of max 15 characters
        if (firstName != null)
            buf = new StringBuffer( firstName);
        else
            buf = new StringBuffer( 15 );

        buf.setLength( 15 ); // buffer length set to 15 characters

        file.writeChars( buf.toString() ); // write firstName inputted to file in the record

        //----------------------------------------
        // if/else statement to help append, reverse, replace, concatenate and manipulate variable lastName type Strings of max 15 characters
        if ( lastName != null )
            buf = new StringBuffer( lastName );
        else
            buf = new StringBuffer( 15);

        buf.setLength( 15 ); // buffer length set to 15 characters

        file.writeChars( buf.toString() ); // write lastName inputted to file in the record

        //----------------------------------------
        file.writeDouble( balance ); // write balance inputted to file in the record

        //----------------------------------------
        file.writeDouble( overdraft ); // write overdraft inputted to file in the record
    }

    //==============================================================================================
    // methods to set and get details from the record
    public void setAccountNo(int a ) { accountNo = a; }
    public int getAccountNo() { return accountNo;}

    public void setFirstName( String f) {firstName = f;}
    public String getFirstName() { return firstName; }

    public void setLastName ( String l) { lastName = l; }
    public String getLastName() { return lastName; }

    public void setBalance( double b) { balance = b; }
    public double getBalance() { return balance; }

    public void setOverdraft( double o) { overdraft = o; }
    public double getOverdraft() { return overdraft; }

    //----------------------------------------
    //determines size (bytes) of each file
    public static int size() { return 80;}

    //==============================================================================================
    // method to bring in data from the random access file created (a second option for "data.read (randAccFile)" method)
    // used for updating the details of the account holder in the updateAccount method situated in Ass2ReadWrite class
    public RandomAccessFile randomAccessFile()
    {
        try
        {
            randAccFile = new RandomAccessFile("creditUnion.dat", "rw"); // instance of the randAccFile variable
        }
        catch (Exception e)
        {
            System.out.println("Error loading record " + e.toString());
            System.exit(1);
        }
        return randAccFile; // return (output) the result of the method
    }
    

}

