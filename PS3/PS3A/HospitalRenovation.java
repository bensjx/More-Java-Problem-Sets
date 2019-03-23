// Copy paste this Java Template and save it as "HospitalRenovation.java"
import java.util.*;
import java.io.*;


class HospitalRenovation {
  private int V; // number of vertices in the graph (number of rooms in the hospital)
  private int[][] AdjMatrix; // the graph (the hospital)
  private int[] RatingScore; // the weight of each vertex (rating score of each room)

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  public HospitalRenovation() {
  }

  int Query() {
    int ans = Integer.MAX_VALUE;
    boolean hasCrit = false; // no crit room
    
    for (int i = 0; i < V; i++){ // loop through all the vertex
    	int neighbours = numNeighbour(i);
    	if (neighbours > 1) {
    		hasCrit = true;
    		ans = Math.min(ans, RatingScore[i]);
    	}
    }
    	
    if (hasCrit) {
    	return ans;
    } else {
    	return -1;
    }
  }
  
  int numNeighbour(int r) {
	  int result = 0;
	  for (int i = 0; i < V; i++) {
		  if (AdjMatrix[r][i] == 1) {
			  result++;
		  }
	  }
	  return result;
  }

  void run() throws Exception {
    // for this PS3, you can alter this method as you see fit

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int TC = Integer.parseInt(br.readLine()); // there will be several test cases
    while (TC-- > 0) {
      br.readLine(); // ignore dummy blank line
      V = Integer.parseInt(br.readLine());

      StringTokenizer st = new StringTokenizer(br.readLine());
      // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
      RatingScore = new int[V];
      for (int i = 0; i < V; i++)
        RatingScore[i] = Integer.parseInt(st.nextToken());

      // clear the graph and read in a new graph as Adjacency Matrix
      AdjMatrix = new int[V][V];
      for (int i = 0; i < V; i++) {
        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        while (k-- > 0) {
          int j = Integer.parseInt(st.nextToken());
          AdjMatrix[i][j] = 1; // edge weight is always 1 (the weight is on vertices now)
        }
      }

      pr.println(Query());
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    HospitalRenovation ps3 = new HospitalRenovation();
    ps3.run();
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
