import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainRunner {
	final static private String NL = "\r\n"; // New line for println
	   
	   public static void main(String[] args) throws IOException {
	      int numRead;                     // Number of employees read
	      int sortResult;                  // Used to sort employee array
	      double [] total = new double[6]; // Stores all totals
	      
	      String INPUT_FILE = "src/Input.txt";  // Name of input file
	      String OUTPUT_FILE = "Output.txt";// Name of output file
	      
	      // Test to see if input text file is in directory

	      try{
	         File inputDataFile = new File(INPUT_FILE);
	         Scanner inputFile  = new Scanner(inputDataFile);
	      }
	      catch(IOException e){
	         System.out.print("Input file \"" + INPUT_FILE + "\" not found, terminating program");
	         System.exit(0);
	      }
	      
	      // Initialize input Scanner and output PrintWriter

	      File inputDataFile = new File(INPUT_FILE);
	      Scanner inputFile  = new Scanner(inputDataFile); 

	      FileWriter outputDataFile = new FileWriter(OUTPUT_FILE);
	      PrintWriter outputFile = new PrintWriter(outputDataFile);   

	      // Acces EmployeeParameters class

	      EmployeeParameters param = new EmployeeParameters();
	      param.getEmployeeParameters();
	      
	      // Create array of Employees
	      
	      Employee[] empl = new Employee[param.maxEmployees];

	      // Begin detailed processing
	      
	      numRead = readData(inputFile, empl); 
	      calculateGrossPay(empl, numRead);    
	      calculateDeductions(empl, numRead, param);
	      calculateTotals(empl, numRead, total);
	      
	      // Output formatted employee information
	      
	      createReport(outputFile, empl, numRead, total, "Input");
	      sortResult = Employee.selectionSortArrayOfClass(empl, numRead, "NAME");
	      createReport(outputFile, empl, numRead, total, "Alphabetical");
	      sortResult = Employee.selectionSortArrayOfClass(empl, numRead, "GROSS PAY");
	      createReport(outputFile, empl, numRead, total, "Gross Pay");
	      
	      // Close files and exit
	      
	      outputFile.close(); 
	      System.exit(0);

	   } // End main

	   

	/*********************************************************************************/

	   public static int readData(Scanner input, Employee[] empl) {
	      int numRead = 0; // Number of lines read
	      
	      // Read input file and assign employee variables
	      
	      try {
	         while(input.hasNext()) { 
	            empl[numRead] = new Employee();
	            empl[numRead].hoursWorked = input.nextDouble();
	            empl[numRead].payRate = input.nextDouble();
	            empl[numRead].name = input.nextLine().trim();
	            numRead++;
	         } // End while
	      }
	      catch(ArrayIndexOutOfBoundsException e) {
	         System.out.print("Number of employees in input file exceeds " 
	         + "number of maximum employees. Terminating program");
	         System.exit(0);
	      }

	      return numRead;
	   } // End readData

	/*********************************************************************************/

	   public static void calculateGrossPay(Employee[] empl, int numRead) {
	      double numOver;    // Tracks the number of hours over hour limit
	      
	      // Calculate and assign employee gross pay 
	      
	      for (int i = 0; i < numRead; ++i){
	         if  (empl[i].hoursWorked <= 40){                            
	            empl[i].grossPay =  empl[i].hoursWorked *  empl[i].payRate;
	         } // End if
	         else if (empl[i].hoursWorked < 50){
	            numOver = empl[i].hoursWorked - 40;
	            empl[i].grossPay = empl[i].payRate * (40 + (numOver * 1.5));
	         } // End else if
	         else{
	            numOver = empl[i].hoursWorked - 50;
	            empl[i].grossPay = empl[i].payRate * (40 + (10 * 1.5) + (numOver * 2));  
	         } // End else
	      } // End for
	   } // End calculateGrossPay

	/*********************************************************************************/

	   public static void calculateDeductions(Employee[] empl, int numRead, EmployeeParameters param) {
	      // Calculate IRA amount, adjusted gross pay, taxes, net pay, savings amount and wealth
	      
	      for (int i = 0; i < numRead; ++i){
	         empl[i].iraAmount = empl[i].grossPay * param.iraRate / 100;
	         empl[i].adjustedGrossPay = empl[i].grossPay - empl[i].iraAmount;
	         empl[i].taxAmount = empl[i].adjustedGrossPay * 
	         ((param.federalWithholdingRate + param.stateWithholdingRate)  / 100);
	         empl[i].netPay = empl[i].adjustedGrossPay - empl[i].taxAmount;
	         empl[i].savingsAmount = empl[i].netPay * (param.savingsRate / 100);  
	         empl[i].wealth = empl[i].savingsAmount + empl[i].iraAmount;    
	      } // End for
	   } // End iraAmount

	   /*********************************************************************************/
	   
	   public static double[] calculateTotals(Employee[] empl, int numRead, double[] totals) {
	      double payRateSum = 0;           // Sum of all payrates
	        
	      for (int i = 0; i< numRead; ++i){
	         totals[0] += empl[i].grossPay;   // Total gross pay
	         totals[1] += empl[i].netPay;     // Total net pay
	         totals[2] += empl[i].wealth;     // Total wealth
	         totals[3] += empl[i].taxAmount;  // Total tax deductions
	         totals[4] += empl[i].hoursWorked;// Total hours worked
	         payRateSum += empl[i].payRate;
	         } // End for
	         totals[5] = payRateSum / numRead;// Average pay rate
	         return totals;
	   } // End calculateTotals
	   
	   /*********************************************************************************/
	  
	   public static void createReport(PrintWriter output, Employee[] empl, int numRead, 
	                                   double[] total, String type) {
	      // Create payroll report and echoes to console
	      
	      output.println("In " + type + " order" + NL);
	      System.out.println("In " + type + " order" + NL);
	      printHeading(output);
	      printDetails(output, empl, numRead);
	      printTotals(output, empl, numRead, total);
	   } // End createReport
	   
	   /*********************************************************************************/

	   public static void printHeading(PrintWriter output){
	      // Format heading and table, echo to console
	      
	      output.println(
	         Toolkit.padString("Mobile Apps Galore, Inc. - Payroll Report", 57, " ", "") + NL
	         + NL + "Name" + Toolkit.padString("Gross Pay", 25, " ", "")       
	         + Toolkit.padString("Net Pay", 10, " ", "") + Toolkit.padString("Wealth", 9, " ", "")
	         + Toolkit.padString("Taxes", 9, " ", "") + Toolkit.padString("Hours", 9, " ", "")
	         + Toolkit.padString("Pay Rate", 11, " ", "") + NL 
	         + Toolkit.repeat("-", 19) + " " + Toolkit.repeat("-", 9) + "   " 
	         + Toolkit.repeat("-", 7) + "   " + Toolkit.repeat("-", 6) + "    " 
	         + Toolkit.repeat("-", 5) + "    " + Toolkit.repeat("-", 5) + "   " 
	         + Toolkit.repeat("-", 8));
	            
	      System.out.println(
	         Toolkit.padString("Mobile Apps Galore, Inc. - Payroll Report", 57, " ", "") + NL
	         + NL + "Name" + Toolkit.padString("Gross Pay", 25, " ", "")       
	         + Toolkit.padString("Net Pay", 10, " ", "") + Toolkit.padString("Wealth", 9, " ", "")
	         + Toolkit.padString("Taxes", 9, " ", "") + Toolkit.padString("Hours", 9, " ", "")
	         + Toolkit.padString("Pay Rate", 11, " ", "") + NL 
	         + Toolkit.repeat("-", 19) + " " + Toolkit.repeat("-", 9) + "   " 
	         + Toolkit.repeat("-", 7) + "   " + Toolkit.repeat("-", 6) + "    " 
	         + Toolkit.repeat("-", 5) + "    " + Toolkit.repeat("-", 5) + "   " 
	         + Toolkit.repeat("-", 8));
	  
	   } // End printHeading

	   /*********************************************************************************/
	   
	   public static void printDetails(PrintWriter output, Employee[] empl, int numRead) {
	      // Print all spec'd details in tabular form, echo to console
	      
	      for(int i = 0; i < numRead; i++) {
	         output.println(Toolkit.padString(empl[i].name, 17) 
	            + Toolkit.leftPad(empl[i].grossPay, 12, "#,##0.00", " ") 
	            + Toolkit.leftPad(empl[i].netPay, 10, "#,##0.00", " ")
	            + Toolkit.leftPad(empl[i].wealth, 9, "#,##0.00", " ")
	            + Toolkit.leftPad(empl[i].taxAmount, 10, "#,##0.00", " ")
	            + Toolkit.leftPad(empl[i].hoursWorked, 9, "#,##0.00", " ")
	            + Toolkit.leftPad(empl[i].payRate, 9, "#,##0.00", " "));
	            
	         System.out.println(Toolkit.padString(empl[i].name, 17) 
	            + Toolkit.leftPad(empl[i].grossPay, 12, "#,##0.00", " ") 
	            + Toolkit.leftPad(empl[i].netPay, 10, "#,##0.00", " ")
	            + Toolkit.leftPad(empl[i].wealth, 9, "#,##0.00", " ")
	            + Toolkit.leftPad(empl[i].taxAmount, 10, "#,##0.00", " ")
	            + Toolkit.leftPad(empl[i].hoursWorked, 9, "#,##0.00", " ")
	            + Toolkit.leftPad(empl[i].payRate, 9, "#,##0.00", " "));
	      } // End for
	      output.print(NL);
	      System.out.print(NL);
	   } // End printDetails

	   /*********************************************************************************/

	   public static void printTotals(PrintWriter output, Employee[] empl, 
	                                  int numRead, double[] total) {
	                                  
	      // Output total gross pay, net pay, wealth, tax deductions, hours worked, and 
	      // average pay rate for all employees, echo all to console
	      
	      output.println(Toolkit.padString("Totals:", 18, " ", "") 
	          + Toolkit.leftPad(total[0], 11, "#,##0.00", " ")
	          + Toolkit.leftPad(total[1], 10, "#,##0.00", " ")
	          + Toolkit.leftPad(total[2], 9, "#,##0.00", " ")
	          + Toolkit.leftPad(total[3], 10, "#,##0.00", " ")
	          + Toolkit.leftPad(total[4], 9, "#,##0.00", " ")
	          + NL + Toolkit.padString("Average:", 69, " ", "")
	          + Toolkit.leftPad(total[5], 7, "#0.00"," ") 
	          + NL + "Number of records read: " + numRead + NL);
	          
	      System.out.println(Toolkit.padString("Totals:", 18, " ", "") 
	          + Toolkit.leftPad(total[0], 11, "#,##0.00", " ")
	          + Toolkit.leftPad(total[1], 10, "#,##0.00", " ")
	          + Toolkit.leftPad(total[2], 9, "#,##0.00", " ")
	          + Toolkit.leftPad(total[3], 10, "#,##0.00", " ")
	          + Toolkit.leftPad(total[4], 9, "#,##0.00", " ")
	          + NL + Toolkit.padString("Average:", 69, " ", "")
	          + Toolkit.leftPad(total[5], 7, "#0.00"," ") 
	          + NL + "Number of records read: " + numRead + NL);
	   } // End printTotals

}
