package CreditUnion;

/*
Programmer: Robert Kacso
Student ID: B00123508
Note: Software Development 2, Assignment 2 - Credit Union program
Due date: 27/04/2020
*/

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;

// main class extending JFrame for GUI and implementing ActionListener for the actionPerformed method
@SuppressWarnings("serial")
public class ReadWrite extends JFrame implements ActionListener
{
    //==============================================================================================
    // declaring variables
    private RandomAccessFile randAccFile; // variable randAccFile for all inputs & outputs
    private Record data; // declare instance variable "data" of class Ass2Record

    // create GUI components
    private JMenuItem create,select,lodge,withdraw,overdraftReq, delete; // items in the menu bar
    private JTextField accountNoField, firstNameField, lastNameField, balanceField, overdraftField; // text fields for input
    private JButton clear, done; // buttons in the frame to clear the text fields and to close/end the program

    // instance of the decimal format to show only 2 digits after the decimal in the balance and overdraft fields
    DecimalFormat df2 = new DecimalFormat( "0.00" );

    //==============================================================================================
    // constructor - initialises the Frame
    public ReadWrite()
    {
        super("Credit Union"); // title of the frame

        // open file
        data = new Record(); //instance of variable "data"

        // access record
        try
        {
            randAccFile = new RandomAccessFile("creditUnion.dat", "rw"); // instance of variable randAccFile
            System.out.println("In first try block");
        } // end try

        catch ( IOException e)
        {
            System.err.println( e.toString() );
            System.exit(1);
        } // end catch

        //==============================================================================================
        // dropdown menu learned it from https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
        JFrame f = new JFrame("Credit Union"); // declare and initialize JFrame "f"
        f.getContentPane().setLayout(new GridLayout(9,2)); // set layout of the frame

        JMenuBar mb = new JMenuBar(); // declare and initialize JMenubar
        f.setJMenuBar(mb); // set the menu bar to the frame

        //----------------------------------------
        // menu and its submenus (new account and existing account)
        JMenu menu = new JMenu("Menu"); // declare and initialize JMenu
        mb.add(menu); // add menu to the menu bar

        create=new JMenuItem("Create New Account"); // declare and initialize JMenuItem
        create.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_DOWN_MASK)); // key short cut alt+N
        create.addActionListener(this); // add action listener to "create"
        menu.add(create); // add create to menu

        select=new JMenuItem("Select an Existing Account"); // declare and initialize JMenuItem
        select.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_DOWN_MASK)); // key short cut alt+E
        select.addActionListener(this); // add action listener to "view"
        menu.add(select); // add view to menu

        //----------------------------------------
        // lodge/withdraw and its submenus (lodge and withdraw)
        JMenu transactions = new JMenu("Account Transactions"); // declare and initialize JMenu
        mb.add(transactions); // add transactions to the menu bar

        lodge=new JMenuItem("Lodgement"); // declare and initialize JMenuItem
        lodge.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_DOWN_MASK)); // key short cut alt+L
        lodge.addActionListener(this); // add action listener to "lodge"
        transactions.add(lodge); // add lodge to transactions

        withdraw=new JMenuItem("Withdrawal"); // declare and initialize JMenuItem
        withdraw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.ALT_DOWN_MASK)); // key short cut alt+W
        withdraw.addActionListener(this); // add action listener to "withdraw"
        transactions.add(withdraw); // add withdraw to transactions

        //----------------------------------------
        // overdraftReq/delete and its submenus (overdraft request and delete account)
        JMenu reqDel = new JMenu("Account requests/deletion"); // declare and initialize JMenu
        mb.add(reqDel); // add reqDel to the menu bar

        overdraftReq=new JMenuItem("Overdraft Adjustment"); // declare and initialize JMenuItem
        overdraftReq.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK)); // key short cut alt+O
        overdraftReq.addActionListener(this); // add action listener to "overdraftReq"
        reqDel.add(overdraftReq); // add overdraftReq to reqDel

        delete=new JMenuItem("Delete Account"); // declare and initialize JMenuItem
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.ALT_DOWN_MASK)); // key short cut alt+BACK_SPACE
        delete.addActionListener(this); // add action listener to "delete"
        reqDel.add(delete); // add delete to reqDel

        //----------------------------------------
        // labels that include image icons added to the frame
        // all images created by me freely online at https://logomakr.com/
        f.getContentPane().add( new JLabel(new ImageIcon("C:\\Users\\digig\\eclipse-workspace\\softDev2019\\src\\CreditUnion\\creditUnionWelcome.png") ));
        f.getContentPane().add(new JLabel(new ImageIcon("C:\\Users\\digig\\eclipse-workspace\\softDev2019\\src\\CreditUnion\\creditUnionHereToHelp.png") ));
        f.getContentPane().add( new JLabel(new ImageIcon("C:\\Users\\digig\\eclipse-workspace\\softDev2019\\src\\CreditUnion\\creditUnionMenu.png") ));
        f.getContentPane().add(new JLabel(new ImageIcon("C:\\Users\\digig\\eclipse-workspace\\softDev2019\\src\\CreditUnion\\creditUnionExit.png") ));

        //----------------------------------------
        // text fields and its corresponding labels added to the JFrame "f"
        f.getContentPane().add( new JLabel()); // empty JLabel for spacing
        f.getContentPane().add( new JLabel()); // empty JLabel for spacing

        f.getContentPane().add( new JLabel("Account Number") );
        accountNoField = new JTextField();
        accountNoField.setEditable(false);
        f.getContentPane().add( accountNoField );

        f.getContentPane().add( new JLabel("First Name") );
        firstNameField = new JTextField();
        firstNameField.setEditable(false);
        f.getContentPane().add( firstNameField );

        f.getContentPane().add( new JLabel("Last Name") );
        lastNameField = new JTextField();
        lastNameField.setEditable(false);
        f.getContentPane().add( lastNameField );

        f.getContentPane().add( new JLabel("Account Balance") );
        balanceField = new JTextField();
        balanceField.setEditable(false);
        f.getContentPane().add( balanceField );

        f.getContentPane().add( new JLabel("Overdraft Balance") );
        overdraftField = new JTextField();
        overdraftField.setEditable(false);
        f.getContentPane().add( overdraftField );

        //----------------------------------------
        // buttons at the bottom of the frame to clear the text fields and to close/terminate the program
        clear = new JButton ("Clear Textfield");
        clear.addActionListener(this);
        f.getContentPane().add (clear);

        done = new JButton("Exit Program");
        done.addActionListener(this);
        f.getContentPane().add (done);

        //----------------------------------------
        f.setSize(450,500); // frame size
        f.setVisible(true); // frame set to be visible
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close the program by "X"-ing out the frame window
    } // end constructor

    //==============================================================================================
    // take input from JOptionPane
    public double paneInput (String tmpInput)
    {
        double input = Double.parseDouble(JOptionPane.showInputDialog(this, tmpInput, "Input", JOptionPane.QUESTION_MESSAGE));

        try
        {
            // "if" statement to give an error message if a negative number is inputted
            if (input < 0)
            {
                JOptionPane.showMessageDialog(this, "Invalid negative input. Enter a number higher than 0", "Error message", JOptionPane.ERROR_MESSAGE);
            }
        } // end try

        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Invalid negative input. Enter a number higher than 0", "Error message", JOptionPane.ERROR_MESSAGE);
        } // end catch

        return input;
    } // end paneInput method

    //==============================================================================================
    // method to read in the account details from the text fields and make the text fields uneditable
    public void accountDetails(DecimalFormat df2)
    {
        accountNoField.setText(String.valueOf(data.getAccountNo()));
        accountNoField.setEditable(false);
        
        firstNameField.setText(data.getFirstName());
        firstNameField.setEditable(false);
        
        lastNameField.setText(data.getLastName());
        lastNameField.setEditable(false);
        
        balanceField.setText(String.valueOf(df2.format(data.getBalance())));
        balanceField.setEditable(false);
        
        overdraftField.setText(String.valueOf(df2.format(data.getOverdraft())));
        overdraftField.setEditable(false);
    } // end accountDetails method

    //==============================================================================================
    // method to update account details and write new data to file
    public void updatedAccount()
    {
        // declaring and initializing temporary variables which then could be seeked and modified
        int tmpAccount = Integer.parseInt( accountNoField.getText() );
        double tmpBalance = Double.parseDouble( balanceField.getText() );
        double tmpOverdraft = Double.parseDouble( overdraftField.getText() );

        try
        {
            // call in method randomAccessFile from the record class and initialize it to be equal to variable randAccFile
            randAccFile = data.randomAccessFile();
            //data.read( randAccFile ); // can be used too, just wanted to practice extra methods

            // writing out the new inputted data to the text fields
            data.setAccountNo( tmpAccount );
            data.setFirstName( firstNameField.getText() );
            data.setLastName( lastNameField.getText() );
            data.setBalance( tmpBalance );
            data.setOverdraft( tmpOverdraft );

            // seek for the requested file in the record
            randAccFile.seek( (long) ( tmpAccount-1 ) * Record.size() );
            // write data to the seeked file
            data.write( randAccFile );
        } // end try

        catch (IOException e ) // input/output exception catch
        {
            System.err.println("Error writing record\n " + e.toString() );
            System.exit( 1 );
        } // end catch

    } // end updateAccount method

    //==============================================================================================
    // create method for adding details to record files
    public void addRecord()
    {
        editTextFields();
        accountNoField.getText();
        {
            try
            {
                int tmpAccount = Integer.parseInt( accountNoField.getText() );

                System.out.println("In second try block");

                if (tmpAccount < 1 || tmpAccount > 100)  //validate account number is in range
                {
                    JOptionPane.showMessageDialog(this, "Account number must be between 1 & 100", "Error message", JOptionPane.ERROR_MESSAGE);
                }

                else
                {
                    //read file to check if account number already exists.
                    randAccFile.seek((long) (tmpAccount - 1) * Record.size());
                    data.read(randAccFile);

                    if (data.getAccountNo() == tmpAccount)  //if account exists, display dialog box to user
                    {
                        JOptionPane.showMessageDialog(this,"Account Number already exists! Please try a different Account Number");
                        accountNoField.setText("");   // clear account textfield
                    }
                    else   //once conditions are met, data is written to file.
                    {
                        updatedAccount(); // method called in to update the account with the new input
                        JOptionPane.showMessageDialog(this, "Account created and details saved");
                    }
                }

                resetTextFields(); // method called in to reset/clear textfield

            } // end try

            catch (NumberFormatException nfe ) // number format exception catch
            {
                JOptionPane.showMessageDialog(this,"Please input the details of the new account and click \"Create New Account\" again. " +
                        "\nIf necessary, reset the text fields by clicking on \"Clear Textfield\".");
            } // end catch

            catch (IOException io) // input/output exception catch
            {
                System.err.println("error during write to file\n" + io.toString() );
            } // end catch

        }//end initial if statement
    } // end addRecord method

    //==============================================================================================
    // create method to read the record by inputting the account number used in delete, lodge, withdraw and overdraft methods
    public void readRecordByInput()
    {
        try
        {
            int accountNumber = (int) paneInput("ENTER ACCOUNT NUMBER");

            if (accountNumber < 1 || accountNumber > 100)
            {
                JOptionPane.showMessageDialog(this, "WRONG INPUT!!! Input a number between 1 and 100.");
            }

            else
                {
                    // seek for the requested file in the record
                    randAccFile.seek((accountNumber - 1) * Record.size());
                    // read data from the seeked file
                    data.read(randAccFile);

                    if (data.getAccountNo() == 0) // "if" statement to reset/clear accountNoFiled if account doesn't exist
                    {
                        JOptionPane.showMessageDialog(this, "Account does not exist");
                        resetTextFields();
                    }
                    else
                    {
                        accountDetails(df2); // if account exists output the account details
                    }
                }

        }//end try

        catch (EOFException eof )
        {
            JOptionPane.showMessageDialog(this, "Account does not exist");
        } // end catch

        catch (IOException e )
        {
            System.err.println("Error during read from file\n " + e.toString() );
            System.exit( 1 );
        } // end catch
    } // end readRecordByInput method method

    //==============================================================================================
    // method to lodge money
    public void lodgement()
    {
        double lodgeAmount, oldBalance, newBalance;

        // variable oldBalance initialized to be equal to the current text from the balanceField
        // If the lodgement field is empty, the "catch" from if(e.getSource () == lodge) of the actionPerformed method will come in effect.
        oldBalance = Double.parseDouble(balanceField.getText());

        // prompting user to input new lodgement
        lodgeAmount = paneInput("Enter amount you want to lodge: ");

        // "if" statement to don't let the balance update with a negative number
        if (lodgeAmount > 0)
        {
            // arithmetic calculation of the new balance
            newBalance = lodgeAmount + oldBalance;
            // set balanceField to show the new balance
            balanceField.setText(String.valueOf(df2.format(newBalance)));
            JOptionPane.showMessageDialog(this, "Balance updated");
        }

        updatedAccount(); // method called in to update the account with the new input
    } // end lodgement method

    //==============================================================================================
    // method for withdrawal()
    public void withdrawal()
    {
        int accept;
        double withdrawAmount, oldBalance, oldOverdraft;

        // variable oldBalance initialized to be equal to the current text from the balanceField
        // If the balance field is empty, the "catch" from if(e.getSource () == withdraw) of the actionPerformed method will come in effect.
        oldBalance = Double.parseDouble(balanceField.getText());

        // variable oldOverdraft initialized to be equal to the current text from the overdraftField
        oldOverdraft = Double.parseDouble(overdraftField.getText());

        // prompting user to input amount to withdraw
        withdrawAmount = paneInput("Enter amount you want to withdraw: ");

        // if/else statements for various scenarios...
        if (withdrawAmount < 0) // ... for inputting negative number
        {
            return;
        }
        else
        {
            if ((oldBalance - withdrawAmount) >= 0) // ... for withdrawal of an amount less then or equal to the current balance
            {
                balanceField.setText(String.valueOf(oldBalance - withdrawAmount));
                JOptionPane.showMessageDialog(this, "Balance updated");
            }
            else if ((oldBalance - withdrawAmount) >= (0 - oldOverdraft)) // ... for withdrawal of an amount that puts account holder into overdraft
            {
                accept = JOptionPane.showConfirmDialog(this, "You accept going into overdraft?", "Warning Message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (accept == 0) // if user accepts to go into overdraft, the balance is updated
                {
                    balanceField.setText(String.valueOf(oldBalance - withdrawAmount));
                    JOptionPane.showMessageDialog(this, "Balance updated");
                }
            }
            else if ((oldBalance - withdrawAmount) < (0 - oldOverdraft)) // ... for withdrawal of an amount greater then the max overdraft allowed
            {
                JOptionPane.showMessageDialog(this, "Sorry, not enough funds. Try a lesser amount.", "Error message", JOptionPane.ERROR_MESSAGE);
            }
        }

        updatedAccount(); // method called in to update the account with the new input
    } // end withdrawal method

    //==============================================================================================
    // method to request change for overdraft
    public void reqOverdraft()
    {
        double newOverdraft;

        // Needed here EVEN if it's result is ignored by the program.
        // If the overdraft field is empty, the "catch" from if(e.getSource () == overdraftReq) of the actionPerformed method will come in effect.
        // Without Double.parseDouble(overdraftField.getText()); from bellow, the method would ask to input a new overdraft even if the account field is empty, rendering the process useless.
        Double.parseDouble(overdraftField.getText());

        // prompting user to input amount of new overdraft
        newOverdraft = paneInput("Enter New Overdraft Amount: ");

        if ( newOverdraft >=0 )
        {
            // set overdraftField to show the new overdraft
            overdraftField.setText(String.valueOf(df2.format(newOverdraft)));
            JOptionPane.showMessageDialog(this, "Overdraft amount updated");
        }


        updatedAccount(); // method called in to update the account with the new input
    } // end reqOverdraft method

    //==============================================================================================
    // delete method to delete account info and set balances to 0
    public void delete()
    {
        try
        {
            // variable accountNumber, accountBalance and accountOverdraft initialized to be equal to the text from their respective fields
            // If the any of the fields are empty, the "catch" from if(e.getSource () == delete) of the actionPerformed method will come in effect.
            int accountNumber = Integer.parseInt( accountNoField.getText() );
            double accountBalance = Double.parseDouble( balanceField.getText() );
            double accountOverdraft = Double.parseDouble( overdraftField.getText() );

            if (accountBalance < 0 && accountBalance > 0)
            {
                JOptionPane.showMessageDialog(this, "Account can't be deleted. Please adjust balance and/or overdraft to be 0 and try again.");
            }

            else if (accountOverdraft > 0)
            {
                JOptionPane.showMessageDialog(this, "Account can't be deleted. Please adjust balance and/or overdraft to be 0 and try again.");
            }

            else
            {
                // message prompting user to confirm deletion of the account
                int accept = JOptionPane.showConfirmDialog(this, "ARE YOU SURE YOU WANT TO DELETE THIS ACCOUNT?", "Warning Message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                // if option is accepted, reset all data in the file to null
                if (accept == 0)
                {
                    data.setAccountNo( 0 );
                    data.setFirstName( null );
                    data.setLastName( null);
                    data.setBalance( 0) ;
                    data.setOverdraft( 0 );

                    // seek for the requested file in the record
                    randAccFile.seek( (long) ( accountNumber-1 ) * Record.size() );
                    // write new data to the seeked file
                    data.write( randAccFile );

                    JOptionPane.showMessageDialog(this, "All funds have been withdrawn and account has been deleted");

                    resetTextFields(); // method called in to reset/clear text fields
                }
            }
        }//end try

        catch (IOException io)
        {
            System.err.println("error during write to file\n" + io.toString() );
        } // end catch

        updatedAccount(); // method called in to update the account with the new input
    } // end delete method

    //==============================================================================================
    // method to set text fields to be editable
    public void editTextFields()
    {
        accountNoField.setEditable(true);
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        balanceField.setEditable(true);
        overdraftField.setEditable(true);
    } // end resetTextFields method

    //==============================================================================================
    // method to reset/empty/clear text fields
    public void resetTextFields()
    {
        accountNoField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        balanceField.setText("");
        overdraftField.setText("");
    } // end resetTextFields method

    //==============================================================================================
    // method to close file/end program
    private void closeFile()
    {
        try
        {
            // message to user when program is terminated
            JOptionPane.showMessageDialog(this,"Thank you for using CREDIT UNION! " +
                    "\n                  GOODBYE!");
            randAccFile.close();
            System.exit( 0 );
        } // end try

        catch (IOException io)
        {
            System.err.println( "File not closed properly\n" + io.toString() );
        } // end catch

        setVisible(false);
    } // end closeFile method

    //==============================================================================================
    // add actionPerformed for the buttons
    public void actionPerformed ( ActionEvent e )
    {
        //----------------------------------------
        // create account
        if (e.getSource() == create) // when "create" JMenuItem is pressed
        {
            addRecord(); // method called in
        }

        //----------------------------------------
        // read account details when account number entered
        if (e.getSource() == select) // when "view" JMenuItem is pressed
        {
            try
            {
                readRecordByInput(); // method called in
            } // end try

            catch ( NumberFormatException nfe ) // number format exception catch
            {
                JOptionPane.showMessageDialog(this,"Invalid input. Please enter an integer number between 1 and 100.");
            } // end catch

        }

        //----------------------------------------
        //update balance by making a lodgement
        if (e.getSource() == lodge) // when "lodge" JMenuItem is pressed
        {
            try
            {
                lodgement(); // method called in
            } // end try

            catch ( NumberFormatException nfe ) // number format exception catch
            {
                JOptionPane.showMessageDialog(this,"Go to \"Menu\" -> \"Select Existing Account\" and input an account number. " +
                        "\nWhen the account details are displayed, click \"Lodgement\".");
            } // end catch
        }

        //----------------------------------------
        // method to withdraw money from the account
        if (e.getSource() == withdraw) // when "withdraw" JMenuItem is pressed
        {
            try
            {
                withdrawal(); // method called in
            } // end try

            catch ( NumberFormatException nfe ) // number format exception catch
            {
                JOptionPane.showMessageDialog(this,"Go to \"Menu\" -> \"Select Existing Account\" and input an account number. " +
                        "\nWhen the account details are displayed, click \"Withdrawal\".");
            } // end catch
        }

        //----------------------------------------
        //method to request an overdraft adjustment
        if (e.getSource() == overdraftReq) // when "overdraftReq" JMenuItem is pressed
        {
            try
            {
                reqOverdraft(); // method called in
            } // end try

            catch ( NumberFormatException nfe ) // number format exception catch
            {
                JOptionPane.showMessageDialog(this,"Go to \"Menu\" -> \"Select Existing Account\" and input an account number. " +
                        "\nWhen the account details are displayed, click \"Overdraft Adjustment\".");
            } // end catch
        }

        //----------------------------------------
        //read account details when account number entered and delete account
        if (e.getSource() == delete) // when "delete" JMenuItem is pressed
        {
            try
            {
                delete(); // method called in
            } // end try

            catch ( NumberFormatException nfe ) // number format exception catch
            {
                JOptionPane.showMessageDialog(this,"Go to \"Menu\" -> \"Select Existing Account\" and input an account number. " +
                        "\nWhen the account details are displayed, click \"Delete Account\".");
            } // end catch

        }

        //----------------------------------------
        // clear textfield
        if (e.getSource() == clear) // when "clear" JButton is pressed
        {
            resetTextFields(); // method called in
        }

        //----------------------------------------
        // save last input and close program
        if (e.getSource() == done) // when "done" JButton is pressed
        {
            closeFile(); // method called in
        }
    }

    //==============================================================================================
    //Instantiate a Ass2ReadWrite object and start the program by calling in the main method
    public static void main(String[] args )
    {
        ReadWrite app = new ReadWrite(); // calling in the constructor
        app.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e) {System.exit(0);}}); // assist with closing the window and terminate the program
    } // end main method
}

