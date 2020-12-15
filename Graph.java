/******************************************************************************
 *
 *  A graph, implemented using a Map of sets.
 *  Self-loops allowed.
 *
 *  The <tt>Graph</tt> class represents an undirected graph of vertices,
 *  represented as String values.
 *  It supports the following operations:
 *  - add a vertex to the graph,
 *  - add an edge to the graph,
 *  - iterate over all of the vertices adjacent to a vertex. 
 *  It also provides methods for returning the number of vertices <em>V</em>,
 *  the number of edges <em>E</em>, the degree of a vertex, and a String
 *  representation of the Graph.
 * 
 */
 
import java.util.*;
 
public class Graph {
   private int V;
   private int E;
   private Map<String, Set<String>> adj;
   
   /**
    * Initializes an empty graph
    */
   public Graph() {
      this.V = 0;
      this.E = 0;
      adj =  new TreeMap<>();
   }

    /**
    * Returns the number of vertices in this graph.
    *
    * @return the number of vertices in this graph
    */
   public int vertices() {
      return V;
   }

   /**
    * Returns the number of edges in this graph.
    *
    * @return the number of edges in this graph
    */
   public int edges() {
      return E;
   }
   
   /**
    * Is this a valid vertex in the graph?
    * @param v vertex to be tested
    * @return true if vertex is valid, false otherwise
   */
   public boolean validVertex(String v)
   {
      return (adj.containsKey(v));
   }

   /**
    * Ensures the argument is a valid vertex in the graph
    *
	 * @param  v one vertex in the graph
    * @throws IllegalArgumentException if v is not a valid vertex
    */
   private void validateVertex(String v) {
      if (!adj.containsKey(v))
         throw new IllegalArgumentException("Invalid Vertex: " + v);
   }

  /**
       * Adds the vertex v to this graph
       *
       * @param  v one vertex in the graph
       * @return true if v was added, false otherwise
   */
   public boolean addVertex(String v) {
      if (adj.containsKey(v))
         return false;
      V++;
      Set<String> neighbors = new HashSet<>();
      adj.put(v, neighbors);
      return true;
   }

   /**
    * Adds the undirected edge v-w to this graph.
    * The arguments must be valid vertices in the graph.
    * @param  v one vertex in the edge
    * @param  w the other vertex in the edge
    * @throws IllegalArgumentException if either vertex does not exist
    */
   public void addEdge(String v, String w) {
      validateVertex(v);
      validateVertex(w);
      E++;
      adj.get(v).add(w);
      adj.get(w).add(v);
   }


   /**
    * Returns the vertices adjacent to vertex <tt>v</tt>.
    *
    * @param  v the vertex
    * @return an Iterator containing the vertices adjacent to vertex <tt>v</tt>
    * @throws IllegalArgumentException if v is not a valid vertex
    */
   public Iterator<String> getAdjacent(String v) {
      validateVertex(v);
      return adj.get(v).iterator();
   }

   /**
    * Returns the degree of vertex <tt>v</tt>.
    *
    * @param  v the vertex
    * @return the degree of vertex <tt>v</tt>
    * @throws IllegalArgumentException if v is not a valid vertex
    */
   public int degree(String v) {
      validateVertex(v);
      return adj.get(v).size();
   }

   /**
    * Returns a string representation of this graph.
    *
    * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
    *         followed by the <em>V</em> adjacency lists
    */
   public String toString() {
      String NEWLINE = System.getProperty("line.separator");
      StringBuilder s = new StringBuilder();
      s.append(V + " vertices, " + E + " edges " + NEWLINE);
      for (String v: adj.keySet()) {
         s.append(v + ": ");
         for (String w : adj.get(v)) {
            s.append(w + " ");
         }
         s.append(NEWLINE);
      }
      return s.toString();
   }
   
   public static void main(String [] args)
   {        
      Graph beatles = new Graph();
      beatles.addVertex("John");
      beatles.addVertex("Paul");
      beatles.addVertex("George");
      beatles.addVertex("Ringo");
      beatles.addEdge("John", "Paul");
      beatles.addEdge("Paul", "George");
      System.out.println(beatles);
   } 
}
