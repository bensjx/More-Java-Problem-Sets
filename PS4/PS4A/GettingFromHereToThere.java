// Copy paste this Java Template and save it as "GettingFromHereToThere.java"
import java.util.*;
import java.io.*;


class GettingFromHereToThere {
  private int V; // number of vertices in the graph (number of rooms in the building)
  private Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too
  private int[] visited;
  private IntegerPair[] parent;
  
  
  public GettingFromHereToThere() {

  }

  void PreProcess() {

  }

  int Query(int source, int destination) {
    int ans = 0;
    
    visited = new int[V];
    parent = new IntegerPair[V];
    for (int i = 0; i < V; i++) {
    	visited[i] = 0;
    	parent[i] = new IntegerPair(-1,0);
    }
    
    DFS(source);
    
    while (destination != -1) {
    	int par = parent[destination].first();
    	int weight = parent[destination].second();
    	if (weight > ans) {
    		ans = weight;
    	}
    	destination = par;
    }
    
    return ans;
  }
  
  void DFS(int vertex) {
	  visited[vertex] = 1; 												// Mark this vertex as visited
	  Vector<IntegerPair> neighbours = AdjList.get(vertex);				// Get all neighbours of this vertex
	  for (int i = 0; i < neighbours.size(); i++) {						// Visit all neighbours of this vertex
		  if (visited[neighbours.get(i).first()] == 0) {				// If not visited
			  parent[neighbours.get(i).first()] = new IntegerPair(vertex, neighbours.get(i).second());
			  DFS(neighbours.get(i).first()); 							// Recursion
		  }
	  }
  }


  void run() throws Exception {
    // do not alter this method
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new Vector < Vector < IntegerPair > >();
      for (int i = 0; i < V; i++) {
        AdjList.add(new Vector < IntegerPair >());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
        }
      }

      PreProcess(); // you may want to use this function or leave it empty if you do not need it

      int Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt()));
      pr.println(); // separate the answer between two different graphs
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    GettingFromHereToThere ps4 = new GettingFromHereToThere();
    ps4.run();
  }
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
  BufferedInputStream bis;
  IntegerScanner(InputStream is) {
    bis = new BufferedInputStream(is, 1000000);
  }
  
  public int nextInt() {
    int result = 0;
    try {
      int cur = bis.read();
      if (cur == -1)
        return -1;

      while ((cur < 48 || cur > 57) && cur != 45) {
        cur = bis.read();
      }

      boolean negate = false;
      if (cur == 45) {
        negate = true;
        cur = bis.read();
      }

      while (cur >= 48 && cur <= 57) {
        result = result * 10 + (cur - 48);
        cur = bis.read();
      }

      if (negate) {
        return -result;
      }
      return result;
    }
    catch (IOException ioe) {
      return -1;
    }
  }
}



class IntegerPair implements Comparable < IntegerPair > {
  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
}



class IntegerTriple implements Comparable < IntegerTriple > {
  Integer _first, _second, _third;

  public IntegerTriple(Integer f, Integer s, Integer t) {
    _first = f;
    _second = s;
    _third = t;
  }

  public int compareTo(IntegerTriple o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else if (!this.second().equals(o.second()))
      return this.second() - o.second();
    else
      return this.third() - o.third();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
  Integer third() { return _third; }
}
