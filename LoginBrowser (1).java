// ICS 4U1
// LoginBrowser/StoreInventory
// Written by: Hammad Rehman
// Written for: Mrs. Ganesan
//Due Date: April 12, 2021
//Submission Date: April 11, 2021
//This program first outputs a Login Browser which allows the user to login using their name and given password.
//The user have to establish whether they are a supervisor or an employee. Supervisors have access to Employee Records.
//Both Employees and Supervisors can access the Store Inventory which outputs all the items in a store. The user can
//Edit a given item, delete an item or add another item. This program requires 3 files to run: Inventory.txt, PasswordCollection.txt and EmployeeRecords.txt.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class LoginBrowser extends JFrame implements ActionListener, KeyListener //Login Browser class
{
    JLabel heading = new JLabel ("Login Panel");
    JLabel num1Label = new JLabel ("Enter Your Name         ");
    JLabel num2Label = new JLabel ("Enter Your Password ");
    static JLabel resLabel = new JLabel ("Please click 'Supervisor' or 'Employee' ");
    static JLabel errorLabel = new JLabel ("");

    JTextField namefield = new JTextField (12);
    JPasswordField passfield = new JPasswordField (12);

    JButton login = new JButton ("Login");
    public static JRadioButton superv = new JRadioButton ("Supervisor");
    public static JRadioButton empl = new JRadioButton ("Employee");
    static ButtonGroup group1;

    JPanel headPanel = new JPanel ();
    JPanel namepanel = new JPanel ();
    JPanel passpanel = new JPanel ();
    JPanel resPanel = new JPanel ();
    JPanel butPanel = new JPanel ();

    int num1, num2, sum;
    public static boolean passsearch, namesearch, supervcheck, statuscheck;
    public static int loginsuperv;
    public static Vector passcollection = new Vector ();
    public static PasswordRecords pr;
    public static Vector empcollection = new Vector ();
    public static EmployeeRecords er;
    public static boolean occurance = false;
    public LoginBrowser ()
	throws java.io.IOException
    {
	// declare the object but don't complete it yet

	FileReader fr = new FileReader ("PasswordCollection.txt"); //Reading from file
	BufferedReader bfr = new BufferedReader (fr);
	String input, name, badgenum, dateemployed, password;
	double pay;
	boolean supervisor;
	do //Reading from a premade file
	{
	    name = bfr.readLine ();
	    if (name == null) //In case the file is empty
	    {
		break;
	    }
	    password = bfr.readLine ();
	    if (password == null)
	    {
		break;
	    }
	    input = bfr.readLine ();
	    if (input == null)
	    {
		break;
	    }
	    else if (input.equals ("true"))
	    {
		supervisor = true;
	    }
	    else
	    {
		supervisor = false;
	    }

	    pr = new PasswordRecords (name, password, supervisor); //Sending all information to the Constructor.
	    passcollection.addElement (pr); //Storing the object into the vector.

	}
	while (name != null && password != null);
	fr.close ();

	FileReader fr2 = new FileReader ("EmployeeRecords.txt"); //Employee Records that can be seen by the supervisors
	BufferedReader bfr2 = new BufferedReader (fr2);
	do //Reading from a premade file
	{
	    name = bfr2.readLine ();
	    if (name == null) //In case the file is empty
	    {
		break;
	    }
	    badgenum = bfr2.readLine ();
	    if (badgenum == null)
	    {
		break;
	    }
	    dateemployed = bfr2.readLine ();
	    if (dateemployed == null)
	    {
		break;
	    }
	    input = bfr2.readLine ();
	    if (input == null)
	    {
		break;
	    }
	    else
	    {
		pay = Double.parseDouble (input);
	    }

	    er = new EmployeeRecords (name, badgenum, dateemployed, pay); //Sending all information to the Constructor.
	    empcollection.addElement (er); //Storing the object into the vector.

	}
	while (name != null && badgenum != null && dateemployed != null && input != null);
	fr2.close ();
	setTitle ("Login Panel");
	group1 = new ButtonGroup ();
	group1.add (superv);
	group1.add (empl);
	getContentPane ().setLayout (new FlowLayout ());    // set layout manager for the JFrame

	// Add components to Panels
	headPanel.add (heading); //Adding labels, text/password fields to each panels
	namepanel.add (num1Label);
	namepanel.add (namefield);
	passpanel.add (num2Label);
	passpanel.add (passfield);
	butPanel.add (superv);
	butPanel.add (empl);

	resPanel.add (resLabel);
	// Add Panels to Frame
	getContentPane ().add (heading);
	getContentPane ().add (heading);
	getContentPane ().add (namepanel);
	getContentPane ().add (passpanel);

	getContentPane ().add (butPanel);
	getContentPane ().add (resPanel);
	getContentPane ().add (login);
	getContentPane ().add (errorLabel);
	getContentPane ().setBackground (Color.DARK_GRAY); //Changing the background colour
	login.setActionCommand ("login");
	superv.addKeyListener (this);
	empl.addKeyListener (this);
	login.addActionListener (this);

	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	login.addActionListener (this);
	setResizable (false);
    }


    // The application
    public void actionPerformed (ActionEvent evt)
    {
	loginsuperv = doMyButtonAction ();
	if (loginsuperv == 1) //Supervisor login
	{
	    supervcheck = true;
	}
	else if (loginsuperv == 2) //Employee login
	{
	    supervcheck = false;
	}
	else if (loginsuperv == 3) //Neither employee or Supervisor was chosen
	{
	    errorLabel.setText ("Please choose one of the given options!");
	}
	if (loginsuperv == 1 || loginsuperv == 2) //User is allowed to move forward
	{
	    errorLabel.setText ("");
	    String uni; //user name input
	    uni = namefield.getText ();
	    String upi; //user password input
	    upi = passfield.getText ();
	    namesearch = false;
	    passsearch = false;
	    for (int count = 0 ; count < passcollection.size () ; count++)
	    {
		pr = (PasswordRecords) passcollection.elementAt (count);
		namesearch = pr.LinearSearch (pr.getName (), uni, namesearch); //Searching for the user inputted item
		passsearch = pr.LinearSearch (pr.getPassword (), upi, passsearch);

		if (namesearch == true && passsearch == true) //Both user inputted name and password are correct
		{
		    statuscheck = pr.LinearSearch (pr.getSupervisor (), supervcheck, statuscheck); //Checking if the status is correct
		    if (statuscheck == true)
		    {
			errorLabel.setText ("Correct!");
			statuscheck = false;
			if (pr.getSupervisor () == true)
			{
			    if (occurance == false)
			    {
				SupervisorPanel sp = new SupervisorPanel (); //Opens up the supervisor panel
				sp.setResizable (false);
				namefield.setEditable (false); //The user will be unable to enter further names/passwords
				passfield.setEditable (false);
				occurance = true; //To ensure this only occurs once
			    }

			}
			else
			{
			    try
			    {
				if (occurance == false)
				{
				    StoreInventoryPanel sip = new StoreInventoryPanel (); //Opens up the Store Inventory Panel
				    sip.setLocation (300, 0);
				    sip.setResizable (false);
				    namefield.setEditable (false);
				    passfield.setEditable (false);
				    occurance = true; //To ensure this only occurs once
				}
			    }
			    catch (IOException exc)
			    {
			    }

			}
		    }
		    namesearch = false;
		    passsearch = false;
		}

	    }
	    if (occurance == false)
	    {
		errorLabel.setText ("Incorrect Name, Password or Status! Try Again!");
	    }
	}

    }


    public void keyPressed (KeyEvent e)  //Required to run the program with an implemented KeyListener
    {
    }


    public void keyReleased (KeyEvent e)
    {
    }


    public void keyTyped (KeyEvent e)
    {
    }


    public static int doMyButtonAction ()
    {
	if (superv.isSelected () == true) //Supervisor button was chosen
	{
	    loginsuperv = 1;

	}
	else if (empl.isSelected () == true) //Employee button was chosen
	{
	    loginsuperv = 2;
	}
	else //Neither options were chosen
	{
	    loginsuperv = 3;
	}
	return loginsuperv;


    }


    public static void main (String[] args)
	throws java.io.IOException
    {
	LoginBrowser lb = new LoginBrowser ();
	lb.setSize (300, 300);
	lb.setResizable (false);
	lb.setVisible (true);
    }
}


class SupervisorPanel extends JFrame implements ActionListener //Supervisor Panel class
{
    JButton empllist;  // reference to the button object
    JButton storeinventory;
    JButton exit;
    JPanel butPanel = new JPanel ();
    JPanel exitPanel = new JPanel ();
    JLabel superheading = new JLabel ("Please pick an option:");
    SupervisorPanel ()
    {
	setResizable (false);
	setTitle ("Supervisor Panel");
	setSize (255, 300);
	setLocation (300, 0);
	getContentPane ().setLayout (new BorderLayout ());   // set the layout manager
	getContentPane ().setBackground (Color.DARK_GRAY);

	// construct a Button
	empllist = new JButton ("Access the Employee List");
	storeinventory = new JButton ("Access the Store Inventory");
	exit = new JButton ("Exit");
	empllist.setActionCommand ("empl");
	storeinventory.setActionCommand ("storeinv");
	exit.setActionCommand ("exit");
	butPanel.add (superheading);
	butPanel.add (empllist);
	butPanel.add (storeinventory);
	butPanel.add (exit);
	butPanel.setBackground (Color.DARK_GRAY);

	getContentPane ().add (butPanel, BorderLayout.CENTER);
	//exitPanel.setLayout (new BoxLayout (exitPanel, BoxLayout.Y_AXIS));
	//exitPanel.add (exit);
	//getContentPane ().add (exitPanel);
	empllist.addActionListener (this);
	storeinventory.addActionListener (this);
	exit.addActionListener (this);
	setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
	setResizable (false);
	setVisible (true);

    }


    public void actionPerformed (ActionEvent evt)
    {

	if (evt.getActionCommand ().equals ("exit"))
	{
	    System.exit (0);
	}
	else if (evt.getActionCommand ().equals ("empl"))
	{
	    try
	    {
		EmployeeList el = new EmployeeList (); //Opens up the supervisor panel
		el.setResizable (false);
	    }
	    catch (IOException exc)
	    {
	    }
	}
	else
	{
	    try
	    {
		StoreInventoryPanel sip = new StoreInventoryPanel (); //Opens the Store Inventory Panel
		sip.setResizable (false);
	    }
	    catch (IOException exc)
	    {
	    }
	}
    }
}
class EmployeeList extends JFrame //Employee List Browser class
{

    JPanel contentPanel;
    JTextArea textArea;

    EmployeeList ()
	throws java.io.IOException
    {
	EmployeeRecords er;
	Vector empcollection = new Vector ();
	FileReader fr2 = new FileReader ("EmployeeRecords.txt"); //Employee Records that can be seen by the supervisors
	BufferedReader bfr2 = new BufferedReader (fr2);
	String name, badgenum, dateemployed, input;
	double pay;
	setResizable (false);
	do //Reading from a premade file
	{
	    name = bfr2.readLine ();
	    if (name == null) //In case the file is empty
	    {
		break;
	    }
	    badgenum = bfr2.readLine ();
	    if (badgenum == null)
	    {
		break;
	    }
	    dateemployed = bfr2.readLine ();
	    if (dateemployed == null)
	    {
		break;
	    }
	    input = bfr2.readLine ();
	    if (input == null)
	    {
		break;
	    }
	    else
	    {
		pay = Double.parseDouble (input);
	    }
	    er = new EmployeeRecords (name, badgenum, dateemployed, pay); //Sending all information to the Constructor.
	    empcollection.addElement (er); //Storing the object into the vector.

	}
	while (name != null && badgenum != null && dateemployed != null && input != null);
	fr2.close ();
	JFrame frame = new JFrame ("Employee List Panel");
	frame.setSize (600, 350);
	frame.setLocation (0, 300);
	String employeeinfo = "";
	contentPanel = (JPanel) frame.getContentPane ();
	contentPanel.setLayout (new FlowLayout ());
	textArea = new JTextArea ("", 2, 50); //Using JTextArea to output the employee list
	textArea.setLineWrap (true);
	textArea.setWrapStyleWord (true);
	textArea.setEditable (false);
	String header = "   Name:                           BadgeNum:             Employment Date:            HourlyPay:" + "\n"; //Formatting purposes
	employeeinfo += header;
	for (int count = 0 ; count < empcollection.size () ; count++)
	{
	    er = (EmployeeRecords) empcollection.elementAt (count);
	    employeeinfo += "   " + er.getName () + "           " + er.getBadgeNum () + "            " + er.getDateEmployed () + "                      " + "$" + er.getPay () + "\n";
	}
	textArea.setText (employeeinfo);
	contentPanel.add (textArea);
	contentPanel.setBackground (Color.DARK_GRAY);
	frame.show ();


    }
}
class StoreInventoryPanel extends JFrame implements ActionListener //Store Inventory Panel class
{

    JPanel contentPanel;
    JTextArea textArea;

    JPanel bottomPanel = new JPanel ();
    JButton add = new JButton ("Add an Item");
    JButton delete = new JButton ("Delete an Item");
    JButton edit = new JButton ("Edit an Item");
    JButton exit = new JButton ("Exit");
    StoreInventoryPanel ()
	throws java.io.IOException
    {
	FileReader fr = new FileReader ("Inventory.txt"); //Reading in item info from file
	BufferedReader bfr = new BufferedReader (fr);
	String name, manufacturer, barcode, input, itemoutput = "";
	double retail;
	int stock, backorder;
	boolean status;
	StoreInven si;
	Vector invencollection = new Vector ();
	setResizable (false);
	do //Reading from a premade file
	{
	    status = true;
	    name = bfr.readLine ();
	    if (name == null) //In case the file is empty
	    {
		break;
	    }
	    manufacturer = bfr.readLine ();
	    if (manufacturer == null)
	    {
		break;
	    }
	    barcode = bfr.readLine ();
	    if (barcode == null)
	    {
		break;
	    }
	    input = bfr.readLine ();
	    if (input == null)
	    {
		break;
	    }
	    else
	    {
		retail = Double.parseDouble (input);
	    }
	    input = bfr.readLine ();
	    if (input == null)
	    {
		break;
	    }
	    else
	    {
		stock = Integer.parseInt (input);
	    }
	    input = bfr.readLine ();
	    if (input == null)
	    {
		break;
	    }
	    else
	    {
		backorder = Integer.parseInt (input);
	    }

	    if (stock > 0)
	    {
		status = true;
	    }
	    else if (stock == 0)
	    {
		status = false;
	    }
	    si = new StoreInven (name, manufacturer, barcode, retail, stock, backorder, status); //Sending all information to the Constructor.
	    invencollection.addElement (si); //Storing the object into the vector.

	}
	while (name != null && manufacturer != null && barcode != null && input != null);
	fr.close ();
	add.setActionCommand ("add");
	edit.setActionCommand ("edit");
	delete.setActionCommand ("delete");
	exit.setActionCommand ("exit");

	bottomPanel.add (delete);
	bottomPanel.add (edit);
	bottomPanel.add (add);
	bottomPanel.add (exit);

	JFrame frame = new JFrame ("Store Inventory Panel");
	frame.setSize (700, 400);
	frame.setLocation (550, 0);
	contentPanel = (JPanel) frame.getContentPane ();
	contentPanel.setLayout (new FlowLayout ());
	textArea = new JTextArea (10, 60); //Using JTextArea to output the Item List
	textArea.setLineWrap (true);
	textArea.setWrapStyleWord (true);
	textArea.setEditable (false);
	String header = "ItemName:            Manufacturer:             Barcode:            Price:            Stock:            BackOrder            InStock" + "\n"; //Header
	itemoutput += header;
	for (int count = 0 ; count < invencollection.size () ; count++)
	{
	    si = (StoreInven) invencollection.elementAt (count);
	    itemoutput = si.showInv (itemoutput); //Calling on showInv() method to obtain each item's info and store it in the variable itemoutput
	    itemoutput += "\n"; //Formatting

	}
	contentPanel.add (textArea);
	contentPanel.add (bottomPanel);
	contentPanel.setBackground (Color.DARK_GRAY);
	textArea.setText (itemoutput);
	itemoutput = ""; //To ensure the items don't stack up for the next turn.
	add.addActionListener (this);
	edit.addActionListener (this);
	delete.addActionListener (this);
	exit.addActionListener (this);
	frame.show ();

    }


    public void actionPerformed (ActionEvent evt)
    {
	if (evt.getActionCommand ().equals ("exit")) //User wants to exit
	{
	    System.exit (0);
	}
	else if (evt.getActionCommand ().equals ("add")) //User wants to add an item
	{
	    try
	    {
		AddItem ai = new AddItem (); //AddItem Browser is initialized
		ai.setResizable (false);
		ai.setVisible (true);
	    }
	    catch (IOException exc)
	    {
	    }
	}
	else if (evt.getActionCommand ().equals ("edit")) //User wants to edit an item
	{
	    try
	    {
		EditItem ei = new EditItem (); //Edit Item browser is initialized
		ei.setResizable (false);
		ei.setVisible (true);
	    }
	    catch (IOException exc)
	    {
	    }

	}
	else if (evt.getActionCommand ().equals ("delete")) //User wants to delete an item
	{
	    try
	    {
		DeleteItem di = new DeleteItem (); //Delete Item browser is initialized
		di.setResizable (false);
	    }
	    catch (IOException exc)
	    {
	    }
	}

    }
}
class AddItem extends JFrame implements ActionListener //Add Item browser class
{

    JLabel heading = new JLabel ("Stove Inventory Panel"); //Declaring each JLabels
    JLabel itemname = new JLabel ("Item Name:     ");
    JLabel manufacturer = new JLabel ("Manufacturer:");
    JLabel barcode = new JLabel ("Bar Code:        ");
    JLabel retail = new JLabel ("Retail Price:   ");
    JLabel stock = new JLabel ("Stock:             ");
    JLabel backorder = new JLabel ("BackOrder:    ");
    static JLabel errorLabel1 = new JLabel ("");

    JTextField nametext = new JTextField (20); //Declaring each JTextFields
    JTextField manufactext = new JTextField (20);
    JTextField barcodetext = new JTextField (20);
    JTextField pricetext = new JTextField (20);
    JTextField stocktext = new JTextField (20);
    JTextField backordertext = new JTextField (20);
    JPanel namePanel = new JPanel (); //Declaring each JPanels
    JPanel manufacPanel = new JPanel ();
    JPanel barcodePanel = new JPanel ();
    JPanel pricePanel = new JPanel ();
    JPanel stockPanel = new JPanel ();
    JPanel backPanel = new JPanel ();
    JPanel header = new JPanel ();
    JPanel bottomPanel2 = new JPanel ();

    JButton add = new JButton ("Add"); //Declaring each JButtons
    JButton view2 = new JButton ("View Updated List");

    AddItem ()
	throws java.io.IOException
    {
	errorLabel1.setText ("");
	StoreInven si;
	Vector invencollection = new Vector ();
	FileReader fr = new FileReader ("Inventory.txt"); //Reading from Inventory file
	BufferedReader bfr = new BufferedReader (fr);
	String name1, manufacturer1, barcode1, input1, itemoutput1 = "";
	double retail1;
	int stock1, backorder1;
	boolean status1;
	do //Reading from a premade file
	{
	    status1 = true;
	    name1 = bfr.readLine ();
	    if (name1 == null) //In case the file is empty
	    {
		break;
	    }
	    manufacturer1 = bfr.readLine ();
	    if (manufacturer1 == null)
	    {
		break;
	    }
	    barcode1 = bfr.readLine ();
	    if (barcode1 == null)
	    {
		break;
	    }
	    input1 = bfr.readLine ();
	    if (input1 == null)
	    {
		break;
	    }
	    else
	    {
		retail1 = Double.parseDouble (input1);
	    }
	    input1 = bfr.readLine ();
	    if (input1 == null)
	    {
		break;
	    }
	    else
	    {
		stock1 = Integer.parseInt (input1);
	    }
	    input1 = bfr.readLine ();
	    if (input1 == null)
	    {
		break;
	    }
	    else
	    {
		backorder1 = Integer.parseInt (input1);
	    }

	    if (stock1 > 0)
	    {
		status1 = true;
	    }
	    else if (stock1 == 0)
	    {
		status1 = false;
	    }
	    si = new StoreInven (name1, manufacturer1, barcode1, retail1, stock1, backorder1, status1); //Sending all information to the Constructor.
	    invencollection.addElement (si); //Storing the object into the vector.

	}
	while (name1 != null && manufacturer1 != null && barcode1 != null && input1 != null);
	fr.close ();
	setTitle ("Add Items");
	add.setActionCommand ("add");
	view2.setActionCommand ("view");
	setSize (500, 550);
	setLocation (550, 240);
	getContentPane ().setLayout (new BoxLayout (getContentPane (), BoxLayout.Y_AXIS)); // set the layout manager
	header.add (heading);
	header.setBackground (Color.DARK_GRAY);
	namePanel.setLayout (new FlowLayout ());
	namePanel.setBackground (Color.DARK_GRAY);
	namePanel.add (itemname); //Adding to each JPanels
	namePanel.add (nametext);
	manufacPanel.setLayout (new FlowLayout ());
	manufacPanel.setBackground (Color.DARK_GRAY);
	manufacPanel.add (manufacturer);
	manufacPanel.add (manufactext);
	barcodePanel.setLayout (new FlowLayout ());
	barcodePanel.setBackground (Color.DARK_GRAY);
	barcodePanel.add (barcode);
	barcodePanel.add (barcodetext);
	pricePanel.setLayout (new FlowLayout ());
	pricePanel.setBackground (Color.DARK_GRAY);
	pricePanel.add (retail);
	pricePanel.add (pricetext);
	stockPanel.setLayout (new FlowLayout ());
	stockPanel.setBackground (Color.DARK_GRAY);
	stockPanel.add (stock);
	stockPanel.add (stocktext);
	backPanel.setLayout (new FlowLayout ());
	backPanel.setBackground (Color.DARK_GRAY);
	backPanel.add (backorder);
	backPanel.add (backordertext);
	bottomPanel2.setLayout (new FlowLayout ());
	bottomPanel2.setBackground (Color.DARK_GRAY);
	bottomPanel2.add (view2);
	bottomPanel2.add (add);

	getContentPane ().add (header, BorderLayout.SOUTH);
	getContentPane ().add (namePanel);
	getContentPane ().add (manufacPanel);
	getContentPane ().add (barcodePanel);
	getContentPane ().add (pricePanel);
	getContentPane ().add (stockPanel);
	getContentPane ().add (backPanel);
	getContentPane ().add (bottomPanel2);
	getContentPane ().add (errorLabel1);
	getContentPane ().setBackground (Color.DARK_GRAY);
	add.addActionListener (this);
	view2.addActionListener (this);
    }


    public void actionPerformed (ActionEvent evt)
    {

	if (evt.getActionCommand ().equals ("add")) //User wants to add the info they typed
	{
	    try
	    {
		String username, usermanufac, userbarcode, input;
		double userprice;
		int userstock, userbackord;
		boolean userstatus;
		username = nametext.getText ();
		usermanufac = manufactext.getText ();
		userbarcode = barcodetext.getText ();
		input = pricetext.getText ();
		userprice = Double.parseDouble (input);
		input = stocktext.getText ();
		userstock = Integer.parseInt (input);
		input = backordertext.getText ();
		userbackord = Integer.parseInt (input);

		if (userstock <= 0) //Stock status
		{
		    userstatus = false;
		}
		else
		{
		    userstatus = true;
		}
		if (username == "" || usermanufac == "" || userbarcode == "" || input == "")
		{
		    errorLabel1.setText ("Please fill in each text box");
		}
		else
		{
		    try
		    {
			StoreInven si;
			Vector invencollection = new Vector ();
			FileReader fr = new FileReader ("Inventory.txt"); //Read from file again
			BufferedReader bfr = new BufferedReader (fr);
			String name1, manufacturer1, barcode1, input1, itemoutput1 = "";
			double retail1;
			int stock1, backorder1;
			boolean status1;
			do //Reading from a premade file
			{
			    status1 = true;
			    name1 = bfr.readLine ();
			    if (name1 == null) //In case the file is empty
			    {
				break;
			    }
			    manufacturer1 = bfr.readLine ();
			    if (manufacturer1 == null)
			    {
				break;
			    }
			    barcode1 = bfr.readLine ();
			    if (barcode1 == null)
			    {
				break;
			    }
			    input1 = bfr.readLine ();
			    if (input1 == null)
			    {
				break;
			    }
			    else
			    {
				retail1 = Double.parseDouble (input1);
			    }
			    input1 = bfr.readLine ();
			    if (input1 == null)
			    {
				break;
			    }
			    else
			    {
				stock1 = Integer.parseInt (input1);
			    }
			    input1 = bfr.readLine ();
			    if (input1 == null)
			    {
				break;
			    }
			    else
			    {
				backorder1 = Integer.parseInt (input1);
			    }

			    if (stock1 > 0)
			    {
				status1 = true;
			    }
			    else if (stock1 == 0)
			    {
				status1 = false;
			    }
			    si = new StoreInven (name1, manufacturer1, barcode1, retail1, stock1, backorder1, status1); //Sending all information to the Constructor.
			    invencollection.addElement (si); //Storing the object into the vector.

			}
			while (name1 != null && manufacturer1 != null && barcode1 != null && input1 != null);
			fr.close ();
			si = new StoreInven (username, usermanufac, userbarcode, userprice, userstock, userbackord, userstatus); //Sends the info to the constructor
			invencollection.addElement (si);
			errorLabel1.setText ("Item was successfully Added!");
			FileWriter fw = new FileWriter ("Inventory.txt");
			for (int count = 0 ; count < invencollection.size () ; count++) //To Write the updated information to file
			{
			    si = (StoreInven) invencollection.elementAt (count);
			    si.ToFile (fw); //Calls on ToFile
			}
			fw.close ();
			nametext.setEditable (false); //Makes it so the user can't type again once they are finished
			manufactext.setEditable (false);
			barcodetext.setEditable (false);
			pricetext.setEditable (false);
			stocktext.setEditable (false);
			backordertext.setEditable (false);
		    }
		    catch (IOException exc)
		    {
		    }
		}
	    }
	    catch (NumberFormatException exc)  //User enters letters in place of numbers
	    {
		errorLabel1.setText ("Please enter numbers in the appropriate text box");
	    }
	}
	else if (evt.getActionCommand ().equals ("view")) //User wants to view the Updated item list with their changes
	{
	    try
	    {
		StoreInventoryPanel sip = new StoreInventoryPanel (); //Opens up the Store Inventory Panel browser
		sip.setResizable (false);
	    }
	    catch (IOException exc)
	    {
	    }
	}
	repaint (); // ask the system to paint the screen.
    }
}

class EditItem extends JFrame implements ActionListener //Edit Item browser class
{

    JLabel searchLabel = new JLabel ("Enter the Item Name:    "); //Initializing all labels, textfields, buttons and panels
    JTextField useritem = new JTextField (20);

    JButton editButton = new JButton ("Complete Edit");
    JButton searchButton = new JButton ("Search");
    JButton clear = new JButton ("Clear");

    JButton view = new JButton ("View Updated List");
    public static StoreInven si;
    public static Vector invencollection = new Vector ();
    static JLabel errorLabel1 = new JLabel ("");
    static JLabel bottomErrorLabel = new JLabel ("");
    JPanel topPanel = new JPanel ();

    JLabel itemname = new JLabel ("Item Name:     ");
    JLabel manufacturer = new JLabel ("Manufacturer:");
    JLabel barcode = new JLabel ("Bar Code:        ");
    JLabel retail = new JLabel ("Retail Price:   ");
    JLabel stock = new JLabel ("Stock:             ");
    JLabel backorder = new JLabel ("BackOrder:    ");

    JTextField nametext = new JTextField (20);
    JTextField manufactext = new JTextField (20);
    JTextField barcodetext = new JTextField (20);
    JTextField pricetext = new JTextField (20);
    JTextField stocktext = new JTextField (20);
    JTextField backordertext = new JTextField (20);
    JPanel namePanel = new JPanel ();
    JPanel manufacPanel = new JPanel ();
    JPanel barcodePanel = new JPanel ();
    JPanel pricePanel = new JPanel ();
    JPanel stockPanel = new JPanel ();
    JPanel backPanel = new JPanel ();
    JPanel header = new JPanel ();
    JPanel bottomPanel = new JPanel ();

    public static boolean itemfound = false;

    EditItem ()
	throws java.io.IOException
    {
	bottomErrorLabel.setText ("");
	errorLabel1.setText ("");
	FileReader fr = new FileReader ("Inventory.txt"); //Reading from file
	BufferedReader bfr = new BufferedReader (fr);
	String name1, manufacturer1, barcode1, input1, itemoutput1 = "";
	double retail1;
	int stock1, backorder1;
	boolean status1;
	do //Reading from a premade file
	{
	    status1 = true;
	    name1 = bfr.readLine ();
	    if (name1 == null) //In case the file is empty
	    {
		break;
	    }
	    manufacturer1 = bfr.readLine ();
	    if (manufacturer1 == null)
	    {
		break;
	    }
	    barcode1 = bfr.readLine ();
	    if (barcode1 == null)
	    {
		break;
	    }
	    input1 = bfr.readLine ();
	    if (input1 == null)
	    {
		break;
	    }
	    else
	    {
		retail1 = Double.parseDouble (input1);
	    }
	    input1 = bfr.readLine ();
	    if (input1 == null)
	    {
		break;
	    }
	    else
	    {
		stock1 = Integer.parseInt (input1);
	    }
	    input1 = bfr.readLine ();
	    if (input1 == null)
	    {
		break;
	    }
	    else
	    {
		backorder1 = Integer.parseInt (input1);
	    }

	    if (stock1 > 0)
	    {
		status1 = true;
	    }
	    else if (stock1 == 0)
	    {
		status1 = false;
	    }
	    boolean finder = false;
	    if (invencollection.size () == 0) //This section of the code is to ensure that the same information is not being stored into the constructor twice.
		//Instead its overwritting the previous info
		{
		    si = new StoreInven (name1, manufacturer1, barcode1, retail1, stock1, backorder1, status1); //Sending all information to the Constructor.
		    invencollection.addElement (si);
		}
	    else
	    {
		for (int count = 0 ; count < invencollection.size () ; count++)
		{
		    si = (StoreInven) invencollection.elementAt (count);
		    if (name1.equalsIgnoreCase (si.getName ()))
		    {
			finder = true;
		    }
		}
	    }
	    if (finder == false)
	    {
		si = new StoreInven (name1, manufacturer1, barcode1, retail1, stock1, backorder1, status1); //Sending all information to the Constructor.
		invencollection.addElement (si); //Storing the object into the vector.
	    }
	}
	while (name1 != null && manufacturer1 != null && barcode1 != null && input1 != null);
	fr.close ();


	setTitle ("Edit Items");
	setSize (500, 550);
	setLocation (550, 240);
	searchButton.setActionCommand ("search");
	editButton.setActionCommand ("edit");
	clear.setActionCommand ("clear");
	view.setActionCommand ("view");
	getContentPane ().setLayout (new FlowLayout ());
	getContentPane ().setBackground (Color.DARK_GRAY);
	topPanel.setLayout (new BoxLayout (topPanel, BoxLayout.Y_AXIS));
	topPanel.setBackground (Color.DARK_GRAY);
	topPanel.add (searchLabel); //Adding to each Panel
	topPanel.add (useritem);
	topPanel.add (searchButton);
	topPanel.add (errorLabel1);


	namePanel.setLayout (new FlowLayout ());
	namePanel.setBackground (Color.DARK_GRAY);
	namePanel.add (itemname);
	namePanel.add (nametext);
	manufacPanel.setLayout (new FlowLayout ());
	manufacPanel.setBackground (Color.DARK_GRAY);
	manufacPanel.add (manufacturer);
	manufacPanel.add (manufactext);
	barcodePanel.setLayout (new FlowLayout ());
	barcodePanel.setBackground (Color.DARK_GRAY);
	barcodePanel.add (barcode);
	barcodePanel.add (barcodetext);
	pricePanel.setLayout (new FlowLayout ());
	pricePanel.setBackground (Color.DARK_GRAY);
	pricePanel.add (retail);
	pricePanel.add (pricetext);
	stockPanel.setLayout (new FlowLayout ());
	stockPanel.setBackground (Color.DARK_GRAY);
	stockPanel.add (stock);
	stockPanel.add (stocktext);
	backPanel.setLayout (new FlowLayout ());
	backPanel.setBackground (Color.DARK_GRAY);
	backPanel.add (backorder);
	backPanel.add (backordertext);
	bottomPanel.setLayout (new FlowLayout ());
	bottomPanel.setBackground (Color.DARK_GRAY);
	bottomPanel.add (view);
	bottomPanel.add (clear);
	bottomPanel.add (editButton);


	getContentPane ().add (topPanel);
	getContentPane ().add (namePanel); //Adding each panel to the frame
	getContentPane ().add (manufacPanel);
	getContentPane ().add (barcodePanel);
	getContentPane ().add (pricePanel);
	getContentPane ().add (stockPanel);
	getContentPane ().add (backPanel);
	getContentPane ().add (bottomPanel);
	getContentPane ().add (bottomErrorLabel);

	nametext.setEditable (false); //User can't add info until their designated item is found
	manufactext.setEditable (false);
	barcodetext.setEditable (false);
	pricetext.setEditable (false);
	stocktext.setEditable (false);
	backordertext.setEditable (false);

	searchButton.addActionListener (this);
	editButton.addActionListener (this);
	clear.addActionListener (this);
	view.addActionListener (this);
    }


    public void actionPerformed (ActionEvent evt)
    {
	if (evt.getActionCommand ().equals ("search"))
	{
	    String key;
	    key = useritem.getText ();
	    boolean linsearch = false;
	    boolean occurance = false;
	    for (int count = 0 ; count < invencollection.size () ; count++)
	    {
		si = (StoreInven) invencollection.elementAt (count);
		linsearch = si.LinearSearch (si.getName (), key, linsearch); //Searching for the user inputted item
		if (linsearch == true) //If the title is found in the records
		{
		    occurance = true;
		    linsearch = false;
		}
	    }
	    if (occurance == true)
	    {
		errorLabel1.setText ("Item Found!");
		useritem.setEditable (false); //User can't edit the search panel again
		nametext.setEditable (true);
		manufactext.setEditable (true);
		barcodetext.setEditable (true);
		pricetext.setEditable (true);
		stocktext.setEditable (true);
		backordertext.setEditable (true);
		itemfound = true;
	    }
	    else
	    {
		errorLabel1.setText ("Item not Found, Try Again!");
	    }
	}
	if (itemfound == true) //Can only occur if the user inputted item has been found in the records.
	{
	    if (evt.getActionCommand ().equals ("clear")) //User wants to clear all text fields
	    {
		nametext.setText ("");
		manufactext.setText ("");
		barcodetext.setText ("");
		pricetext.setText ("");
		stocktext.setText ("");
		backordertext.setText ("");

	    }
	    else if (evt.getActionCommand ().equals ("edit")) //User wants to submit the information they have inputted
	    {
		try
		{
		    String username, usermanufac, userbarcode, input;
		    double userprice;
		    int userstock, userbackorder;
		    boolean userstatus;

		    username = nametext.getText (); //Getting info from text fields
		    usermanufac = manufactext.getText ();
		    userbarcode = barcodetext.getText ();
		    input = pricetext.getText ();
		    userprice = Double.parseDouble (input);
		    input = stocktext.getText ();
		    userstock = Integer.parseInt (input);
		    input = backordertext.getText ();
		    userbackorder = Integer.parseInt (input);
		    if (userstock <= 0)
		    {
			userstatus = false;
		    }
		    else
		    {
			userstatus = true;
		    }
		    si.setName (username); //Updating info obtained from each textfields
		    si.setManufacturer (usermanufac);
		    si.setBarcode (userbarcode);
		    si.setRetail (userprice);
		    si.setStock (userstock);
		    si.setBackorder (userbackorder);
		    si.setStatus (userstatus);
		    bottomErrorLabel.setText ("Item Info has been Updated!");
		    try
		    {
			FileWriter fw = new FileWriter ("Inventory.txt");
			for (int count = 0 ; count < invencollection.size () ; count++) //To Write the updated information to file
			{
			    si = (StoreInven) invencollection.elementAt (count);
			    si.ToFile (fw); //Calls on ToFile
			}
			fw.close ();
			nametext.setEditable (false); //User can't edit the textfields once the process is complete
			manufactext.setEditable (false);
			barcodetext.setEditable (false);
			pricetext.setEditable (false);
			stocktext.setEditable (false);
			backordertext.setEditable (false);
		    }
		    catch (IOException exc)
		    {
		    }

		}
		catch (NumberFormatException exc)  //In case letters have been inputted in place of numbers
		{

		    bottomErrorLabel.setText ("Please enter numbers in appropriate text fields!");
		}
	    }
	    else if (evt.getActionCommand ().equals ("view")) //User wants to view the updatting item list
	    {
		try
		{
		    StoreInventoryPanel sip = new StoreInventoryPanel (); //Opens the Store Inventory Panel browser
		    sip.setResizable (false);
		}
		catch (IOException exc)
		{
		}
	    }
	}
    }
}


class DeleteItem extends JFrame implements ActionListener //Delete Item browser class
{
    JLabel searchLabel = new JLabel ("Enter the Item Name:    "); //Declaring labels, text fields and buttons
    JLabel errorLabel1 = new JLabel ("");
    JTextField useritem = new JTextField (20);
    JButton deleteButton = new JButton ("Delete");
    JButton view = new JButton ("View Updated List");

    public static StoreInven si;
    public static Vector invencollection = new Vector ();

    JPanel topPanel = new JPanel ();
    DeleteItem ()
	throws java.io.IOException
    {
	FileReader fr = new FileReader ("Inventory.txt"); //Reading from file
	BufferedReader bfr = new BufferedReader (fr);
	String name1, manufacturer1, barcode1, input1, itemoutput1 = "";
	double retail1;
	int stock1, backorder1;
	boolean status1;
	do //Reading from a premade file
	{
	    status1 = true;
	    name1 = bfr.readLine ();
	    if (name1 == null) //In case the file is empty
	    {
		break;
	    }
	    manufacturer1 = bfr.readLine ();
	    if (manufacturer1 == null)
	    {
		break;
	    }
	    barcode1 = bfr.readLine ();
	    if (barcode1 == null)
	    {
		break;
	    }
	    input1 = bfr.readLine ();
	    if (input1 == null)
	    {
		break;
	    }
	    else
	    {
		retail1 = Double.parseDouble (input1);
	    }
	    input1 = bfr.readLine ();
	    if (input1 == null)
	    {
		break;
	    }
	    else
	    {
		stock1 = Integer.parseInt (input1);
	    }
	    input1 = bfr.readLine ();
	    if (input1 == null)
	    {
		break;
	    }
	    else
	    {
		backorder1 = Integer.parseInt (input1);
	    }

	    if (stock1 > 0)
	    {
		status1 = true;
	    }
	    else if (stock1 == 0)
	    {
		status1 = false;
	    }
	    boolean finder = false; //This section of the code is to ensure that the same information is not being stored into the constructor twice.
	    //Instead its overwritting the previous info.
	    if (invencollection.size () == 0)
	    {
		si = new StoreInven (name1, manufacturer1, barcode1, retail1, stock1, backorder1, status1); //Sending all information to the Constructor.
		invencollection.addElement (si);
	    }
	    else
	    {
		for (int count = 0 ; count < invencollection.size () ; count++)
		{
		    si = (StoreInven) invencollection.elementAt (count);
		    if (name1.equalsIgnoreCase (si.getName ()))
		    {
			finder = true;
		    }
		}
	    }
	    if (finder == false)
	    {
		si = new StoreInven (name1, manufacturer1, barcode1, retail1, stock1, backorder1, status1); //Sending all information to the Constructor.
		invencollection.addElement (si); //Storing the object into the vector.
	    }

	}
	while (name1 != null && manufacturer1 != null && barcode1 != null && input1 != null);
	fr.close ();


	setTitle ("Delete Items");
	setSize (300, 200);
	setLocation (550, 240);
	deleteButton.setActionCommand ("delete");
	view.setActionCommand ("view");
	getContentPane ().setLayout (new FlowLayout ());

	topPanel.setLayout (new BoxLayout (topPanel, BoxLayout.Y_AXIS));
	topPanel.setBackground (Color.DARK_GRAY);
	topPanel.add (searchLabel); //Adding labels/textfields to panels
	topPanel.add (useritem);

	getContentPane ().add (topPanel); //Adding panels to the frame
	getContentPane ().add (view);
	getContentPane ().add (deleteButton);
	getContentPane ().add (errorLabel1);
	getContentPane ().setBackground (Color.DARK_GRAY);

	view.addActionListener (this);
	deleteButton.addActionListener (this);
	setVisible (true);
    }


    public void actionPerformed (ActionEvent evt)
    {
	if (evt.getActionCommand ().equals ("delete")) //User wants to delete the inputted Item
	{
	    String key;
	    key = useritem.getText ();
	    boolean linsearch = false;
	    boolean occurance = false;
	    for (int count = 0 ; count < invencollection.size () ; count++)
	    {
		si = (StoreInven) invencollection.elementAt (count);
		linsearch = si.LinearSearch (si.getName (), key, linsearch); //Searching for the user inputted item
		if (linsearch == true) //If the item is found in the records
		{
		    invencollection.removeElementAt (count); //Removing the item from the records
		    occurance = true;
		    linsearch = false;
		}
	    }
	    if (occurance == true) //Item was found and deleted
	    {
		try
		{
		    errorLabel1.setText ("Item Found and Successfully Deleted!");
		    useritem.setEditable (false);

		    FileWriter fw = new FileWriter ("Inventory.txt");
		    for (int count = 0 ; count < invencollection.size () ; count++) //To Write the updated information to file
		    {
			si = (StoreInven) invencollection.elementAt (count);
			si.ToFile (fw); //Calls on ToFile
		    }
		    fw.close ();
		}
		catch (IOException exc)
		{
		}
	    }
	    else //User inputted item was not found
	    {
		errorLabel1.setText ("Item not Found, Try Again!");
	    }
	}
	else if (evt.getActionCommand ().equals ("view")) //User wants to view the updated item list
	{
	    try
	    {
		StoreInventoryPanel sip = new StoreInventoryPanel (); //Opens the Store Inventory Panel browser
		sip.setResizable (false);
	    }
	    catch (IOException exc)
	    {
	    }
	}
    }
}



class PasswordRecords //Holds the Names and Passwords of both Employees and Supervisors

{
    private String name, password;
    boolean supervisor;
    public PasswordRecords (String n, String p, boolean s)  //Password Records Constructor
	throws java.io.IOException
    {
	this.name = n;
	this.password = p;
	this.supervisor = s;
    }


    public String getName ()  //Holds the employee's/supervisor's name
    {
	return this.name;
    }


    public String getPassword ()  //Holds the employee's/supervisor's password
    {
	return this.password;
    }


    public boolean getSupervisor ()  //Holds if the person is a supervisor or employee
    {
	return this.supervisor;
    }


    boolean LinearSearch (String title, String key, boolean search)  //Personalized LinearSearch
    {
	if (title.equalsIgnoreCase (key))
	{
	    search = true; //Returns true if the key is found
	}
	return search;
    }


    boolean LinearSearch (boolean status, boolean key, boolean search)  //Personalized LinearSearch
    {
	if (key == status)
	{
	    search = true; //Returns true if the key is found
	}
	else
	{
	    search = false;
	}
	return search;
    }
}


class EmployeeRecords //This class is used to hold the information of the Employees only
{
    private String name, badgenum, dateemployed;
    private double pay;
    public EmployeeRecords (String n, String bnum, String de, double p)  //Employee Records Constructor
    {
	this.name = n;
	this.badgenum = bnum;
	this.dateemployed = de;
	this.pay = p;
    }


    public String getName ()
    {
	return this.name;
    }


    public String getBadgeNum ()
    {
	return this.badgenum;
    }


    public String getDateEmployed ()
    {
	return this.dateemployed;
    }


    public double getPay ()
    {
	return this.pay;
    }
}




class StoreInven //Holds information on each item
{
    static Formatting f = new Formatting (); //Formatting Class
    private String name, manufacturer, barcode;

    private double retail;
    private int stock, backorder;
    private boolean status;
    public StoreInven (String n, String m, String b, double r, int s, int back, boolean stat)  //Constructor
    {
	this.name = n;
	this.manufacturer = m;
	this.barcode = b;
	this.retail = r;
	this.stock = s;
	this.backorder = back;
	this.status = stat;
    }


    public String getName ()  //Returns the name of an item when called upon
    {
	return this.name;
    }


    public String getManufacturer ()  //Returns the manufacturer of an item when called upon
    {
	return this.manufacturer;
    }


    public String getBarcode ()  //Returns the barcode of an item when called upon
    {
	return this.barcode;
    }


    public double getRetail ()  //Returns the retail price of an item when called upon
    {
	return this.retail;
    }


    public int getStock ()  //Returns the number of stock of an item when called upon
    {
	return this.stock;
    }


    public int getBackOrder ()  //Returns the backorder number of an item when called upon
    {
	return this.backorder;
    }



    public boolean getStatus ()  //Returns the stock status when called upon
    {
	return this.status;
    }




    public void setName (String newName)  //Updates the item name
    {
	this.name = newName;
    }


    public void setManufacturer (String newManufacturer)  //Updates the manufacturer
    {
	this.manufacturer = newManufacturer;
    }


    public void setBarcode (String newBarcode)  //Updates the barcode
    {
	this.barcode = newBarcode;
    }


    public void setRetail (double newRetail)  //Updates the retail price
    {
	this.retail = newRetail;
    }


    public void setStock (int newStock)  //Updates the stock
    {
	this.stock = newStock;
    }


    public void setBackorder (int newBackorder)  //Updates the back order
    {
	this.backorder = newBackorder;
    }


    public void setStatus (boolean newStatus)  //Updates the status
    {
	this.status = newStatus;
    }



    boolean LinearSearch (String title, String key, boolean search)  //Personalized LinearSearch
    {
	if (title.equalsIgnoreCase (key))
	{
	    search = true; //Returns true if the key is found
	}
	return search;
    }



    public void ToFile (FileWriter fw)  //This method writes the updated information of each item to the file
	throws java.io.IOException
    {
	fw.write (this.name + "\r\n");
	fw.write (this.manufacturer + "\r\n");
	fw.write (this.barcode + "\r\n");
	fw.write (this.retail + "\r\n");
	fw.write (this.stock + "\r\n");
	fw.write (this.backorder + "\r\n");
    }



    public String showInv (String itemoutput)  //Store the item info into a variable in a proper format using MyFormatting class
    {
	String stockstat = "";
	String space = "";
	if (this.status == true)
	{
	    stockstat = "YES";
	}
	if (this.status == false)
	{
	    stockstat = "NO";
	}
	itemoutput += (f.FieldSize (this.name, 25) + f.FieldSize (this.manufacturer, 20) + f.FieldSize (this.barcode, 20) + "$" + this.retail + f.FieldSize (this.stock, 20) + f.FieldSize (this.backorder, 20) + f.FieldSize (space, 20) + stockstat);
	return itemoutput;
    }
}




class Formatting //Formatting Class
{
    String FieldSize (String s, int size)
    {
	String s2 = s;
	for (int count = 1 ; count <= (size - s.length ()) ; count++)
	{
	    s2 += " ";
	}
	return s2;
    }


    String FieldSize (int num, int size)  //Overloaded for ints
    {
	String s = "" + num; // Trick Java into thinking that num is a String
	String s2 = "";
	for (int count = 1 ; count <= (size - s.length ()) ; count++)
	{
	    s2 += " ";
	}
	s2 += s; // add the integer to the end of the String (after the blanks)
	return s2;
    }
}










