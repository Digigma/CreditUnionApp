package CreditUnion;
/*
Programmer: Robert Kacso
Student ID: B00123508
Note: Software Development 2, Assignment 2 - Credit Union program
Due date: 27/04/2020
*/

import java.io.*;

// main class to create random access file
public class CreateRandomFile
{
    // constructor
    public CreateRandomFile()
    {
        Record blankRec = new Record(); //instance of variable "blankRec"

        try
        {
            RandomAccessFile randAccFile = new RandomAccessFile("C:\\Users\\digig\\eclipse-workspace\\softDev2019\\src\\CreditUnion\\creditUnion.dat", "rw"); // instance of the randAccFile variable
            randAccFile.seek(0); // start seeking for empty file in the record at position 0

            for (int i=0; i<100; i++) // "for" loop to create 100 empty files onto the record
                blankRec.write(randAccFile);
        } // end try

        catch(IOException e)
        {
            System.err.println("File not opened properly\n" + e.toString() );
            System.exit( 1 );
        } // end catch
    }

    // main method that runs constructor to create a random access file of 100 empty files
    public static void main( String[] args )
    {
        new CreateRandomFile();
    }
}
