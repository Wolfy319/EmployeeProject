// Employee class - data values for each employee

public class Employee {
		public String name;			   // Name of the employee
		public double hoursWorked;		// Hours worked in the payroll period
		public double payRate;		   // Hourly pay rate
		public double grossPay;		   // Gross pay based on the number of hours worked
		public double adjustedGrossPay;	// Gross pay less amount for the IRA
		public double netPay;			// Gross pay less taxes
		public double savingsAmount;	// Amount of gross pay for savings
		public double iraAmount;		// Amount of gross pay for the IRA
		public double taxAmount;		// Amount of tax based on gross pay and tax rates
      public double wealth;         // Savings amount + iraAmount

   // *******************************************************************

   /**
      selectionSortArrayOfClass
      Sort an array of the Employee class in ascending order.
      The parameters are:
      
      @param   empl - an array of the Employee class. If the array is empty,
                      the method simply returns with no action taken
      @param   len - the number of possibly partially-filled elements in the 
               Employee array (i.e., it might be < empl.length)
      @param   sortType - a string that indicates the type of sort:
                  "Name"      - sort by name
                  "Gross Pay" - sort by gross pay
      @return  0, sort was completed successfully
               1, the array to sort has no elements
               2, the sortType is invalid
   */
   
   public static int selectionSortArrayOfClass(
               Employee[] empl,
               int len,
               String sortType) {

    final String SORT_BY_NAME  = "NAME";
    final String SORT_BY_GROSS = "GROSS PAY";
    
    Employee tempEmpl = new Employee();   // Holds an entry being swapped
    
    boolean isSortByName;     // True if sort by name
    boolean isSortByGross;    // True if sort by gross pay
    int k, i;		            // Indexes into the array to assist with the sort
    int minIndex;             // Index of the minimum value in the array
    String howSort;           // Upper case of the sort type
    
    // Begin execution. If the employee array has no entries, there is nothing
    // to sort, so simply return
    
    if (empl.length == 0) {
      return 1;
    } // End if
    
    // Set the flag for the entire sort based on whether we are sorting by
    // a name (sortType = "Name") or we are sorting by the gross pay
    // (sortType = "Gross Pay"). If the the sortType is not one of the two
    // possibilities, the method returns with no action taken.
    
    howSort = sortType.toUpperCase().trim();          // Deal only with upper case
    isSortByName  = (howSort.equals(SORT_BY_NAME));   // True means sort by name
    isSortByGross = (howSort.equals(SORT_BY_GROSS));  // True means sort by gross
    
    // If neither of the sort types is true, the user used an unrecognized sort
    // type, so just return
    
    if (!(isSortByName || isSortByGross)) {
      return 2;
    } // End if
    
    // At this point, the empl array has at least one entry, and we are
    // sorting by name or by gross pay.
    // Each pass determines the location, minIndex, of the smallest value
    
    for (k = 0;  k < len - 1;  k++)
    {    
       //  Find the location, minIndex, of the smallest value in row k
       minIndex = k;  // Assume the minimum value is at location k
       
       // We check once for each pass of control variable k to see whether
       // we are sorting by name or gross pay. This approach is in contrast
       // to one inner for loop (control variable i) in which we ask each
       // time through the loop what type of sort we are doing.
       // This approach saves a whole bunch of times we ask what type
       // of sort we're doing at the expense of a slightly larger program.

       if (isSortByName) {
          for (i = k + 1;  i < len;  i++) {
               if (empl[i].name.compareTo(empl[minIndex].name) < 0) minIndex = i;
          } // End for (i = k + 1;  i < len;  i++)
       } // End 'if' sorting by name
       else { // We are sorting by gross pay
          for (i = k + 1;  i < len;  i++) {
               if (empl[i].grossPay < empl[minIndex].grossPay) minIndex = i;
          } // End for (i = k + 1;  i < len;  i++)
       } // End 'else' sorting by gross pay
       
       // Swap elements in the minIndex and k positions of the arrays

       tempEmpl = empl[minIndex];
       empl[minIndex] = empl[k];
       empl[k] = tempEmpl;
     } // End for (k = 0;  k < len - 1;  k++)
     
     return 0;    // Indicate the sort ended OK
   } // End selectionSortArrayOfClass
   
   // **************************************************************************

} // End Employee