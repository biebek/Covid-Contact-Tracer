public class ContactTracerTest
 //
 //   Sample program to test the basic functionality of the ContactTracer class
{
   public static void main (String[] args)
   {
     
      ContactTracer covid19 = new ContactTracer("10movies.txt");
      
      System.out.println("\n" + covid19.infectionConfirmed("Evans, John"));  // EXPECTED: 23 CONTACTS: contact1 | contact2 | ...
      System.out.println(       covid19.infectionConfirmed("Reed, Albert")); // EXPECTED: 50 CONTACTS: contact1 | contact2 | ...
      covid19.infectionConfirmed("Close, Glenn");
      covid19.infectionConfirmed("Ford, Harrison");
      covid19.infectionConfirmed("Bacon, Kevin");
   
      String target = "Doyle, Richard";
      System.out.println("\n" + target + " is " + covid19.infectionPathLength(target) + " contact(s) away from an infected host."); // EXPECTED: 1
      System.out.println(covid19.infectionPath(target));                      // EXPECTED: [Ford, Harrison | Doyle, Richard]
      
      System.out.println("\nInfected hosts within 1 contact of " + target + " --> " + covid19.infectionRisk(target, 1));
                                                                              // EXPECTED: 2: [Close, Glenn | Ford, Harrison]
      target = "Daniels, Jeff";
      System.out.println("\nInfected hosts within 5 contacts of " + target + " --> " + covid19.infectionRisk(target, 5));
   }                                                                          // EXPECTED: 3: [Close, Glenn | Evans, John | Ford, Harrison]
}