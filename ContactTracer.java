/**
Imports the required Java util Package needed for the program
*/
import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.util.*;


/**
This ContactTracker class uses different array lists and lists to implement the breadth first search for required solution.
*/
public class ContactTracer { 
   Set<String> infected = new HashSet<>();
	
   Graph gr = new Graph();
/**
This constructor reads data from a text file, where each line contains names of poeple who have been in contact with with one another, separeted by / symbols in the format LastName, Name.
@param string the file whose data are processed to identify the infected people and people who have contacted infected people.
*/
   public ContactTracer(String string) {
   
      try{
         Scanner scan = new Scanner(new File(string));
         while(scan.hasNext())
         {
            Scanner scan2 = new Scanner(scan.nextLine());
            ArrayList<String> people = new ArrayList<>();
         
            scan2.useDelimiter("/");
            while(scan2.hasNext())
            {
              people.add(scan2.next());
            }

            
            for (String v1: people) {
              gr.addVertex(v1);
              for (String v2: people) {
                gr.addVertex(v2);
                if (!v1.equals(v2)) {
                  gr.addEdge(v1, v2);
                }
              }
            }
        }
         
      }      
      catch(IOException e)
      {}
   }

/**
This public method returns the number of people followed by the names who have been in immediate contact with the infected person who is passed as argument.
@param person is the infected person for which the people in immediate contact is to be found.
@return Returns the number and the names of people who are in immediate contact with the infected person.
*/
  public String infectionConfirmed(String person) {
    Iterator<String> adjs =  gr.getAdjacent(person);

    int i = 0;
    while(adjs.hasNext()) {
      adjs.next();
      i++;
    }

    infected.add(person);
  	
    return i + " CONTACTS: " + outputStr(gr.getAdjacent(person)); 
  }
  
/**
This public method returns the number of edges in the shortest path from the target argument to an individual confiremd as infected. It returns -1 if no such path is found.
@param target the person for whom the shorest path is to be found from the infected individual.
@return Returns the number of edges in the shortest path.  
*/
  public int infectionPathLength(String target) {
    return infectionPath_(target).size() - 1;
  }

/**
This public method returns the names of individuals in the shortest path from the target to the confirmed infected person with the names between infected person and the target separaed by | sign. It returns a message if no such path is found.
@param Target the person for whom the names in the shortest path leading to the target is to be found.
@return Returns the names of the individual in the shortest path from the target to an infected person.
*/
  public String infectionPath(String target) {
   List<String> s = infectionPath_(target);
   if(gr.validVertex(target)){
      if (s.size() <= 0) {
        return "No known infection path to " + target;
      }
   }
   return "[" + outputStr(s) + "]";
  }


/**
Returns the names of individuals in the shortest path from the target to an individual confirmed as infected. Returns a list so that it could be reused in both int infectionPathLength(String target) and String infectionPath(String target).First item in the list is infected person and last item in the list is the target.
@param target, shortest path to a infected person starting from this node
@return Returns the list of names.
*/
  private List<String> infectionPath_(String target) {
  
    Queue<Node> q = new LinkedList<>();
    q.add(new Node(target));

    Set<String> visited = new HashSet<>();
    visited.add(target);

    while (q.peek() != null) {
      Node n = q.remove();
      if (infected.contains(n.data)) {
        return pathFromNode(n);
      }
        
      Iterator<String> adjs = gr.getAdjacent(n.data);
      while(adjs.hasNext()) {
      String s = adjs.next();
      if (!visited.contains(s)) {
        
        //new Node(s, n) keeps track of parent
        q.add(new Node(s, n));        
        
        visited.add(s);
        }
      }
    }
            
    return new ArrayList<>();
   }

/**
This public method retuns the string representing the infected risk for an individual as measured by the number of infected individuals separated by no more than given distance edges from the target.
@param target the person for which the infection risk is to be found.
@param distance the edges from which the target and the infected person is separated.
@return Retuns the a string of infected risk for an individual.
*/
  public String infectionRisk(String target, int distance) {
    List<List<String>> infectionPaths = infectionPaths(target, distance);
    List<String> infecteds = new ArrayList<>();
    
    for(List<String> path: infectionPaths) {
      infecteds.add(path.get(0));
    }

    return infecteds.size() +  ": [" + outputStr(infecteds) + "]";
  }



/**
This method functions same as List<String> infectionPath_(String target) but instead of quitting on shortest infection path, it first finds all infection  paths, then return only those path that satisfy 'distance' parameter
@param target the person for which the shortest path to the infected person is to be found.
@param distance the the numbers of edges the target and the infected person is separated from.
@return Returns the distance. 
*/
  public List<List<String>> infectionPaths(String target, int distance) {
      Queue<Node> q = new LinkedList<>();
      q.add(new Node(target));

      Set<String> visited = new HashSet<>();
      visited.add(target);

      List<List<String>> infectedPaths = new ArrayList<>();

      //First finds all infection  paths.
      while (q.peek() != null) {
        Node n = q.remove();
        if (infected.contains(n.data)) {
          infectedPaths.add(pathFromNode(n));
        }
        
        Iterator<String> adjs = gr.getAdjacent(n.data);
        while(adjs.hasNext()) {
         String s = adjs.next();
          if (!visited.contains(s)) {
            q.add(new Node(s, n));
            visited.add(s);
          }
        }
      }

      //Retuns only those path that satisfy 'distance' parameter
      Iterator<List<String>> it = infectedPaths.iterator();
      while(it.hasNext()) {
        List<String> path = it.next();
        if (path.size() - 1 > distance) it.remove();
      }

      return infectedPaths;
  }

/**
Extracts names from the node structure
*/
   private List<String> pathFromNode(Node n) {
    
    List<String> path = new ArrayList<>();
    while(n != null) {
      path.add(n.data);
      n = n.parent;
    }

    return path;
   }

/**
Prints in the format reuquired by the program
*/
  private String outputStr(List<String> list) {
    return outputStr(list.iterator());
  }

/**
Prints in the format reuquired by the program
*/
  private String outputStr(Iterator<String> it) {
    String q = "";
    while(it.hasNext()) {
      q = q + it.next() + " | ";
    }
    return q.substring(0, q.length()-3);
  }

/**
These structure hold a reference to parent node, so in a way a chain is created like linked list. used to keep track of path in BFS traversal.
*/
  private class Node {
    String data;
    Node parent;

    private Node(String s) {
      this.data = s;
      this.parent = null;
    }

    private Node(String v, Node n) {
      this.data = v;
      this.parent = n;
    }
  }
}
