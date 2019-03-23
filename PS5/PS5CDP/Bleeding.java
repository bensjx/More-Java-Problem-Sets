// Copy paste this Java Template and save it as "Bleeding.java"
import java.util.*;
import java.io.*;

class Bleeding {
	private int V; // number of vertices in the graph (number of junctions in Singapore map)
	private int Q; // number of queries
	private ArrayList < ArrayList < IntegerPair > > AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge
	private int[][] dist;
	private int maxweight = 10000000;
	
	public Bleeding() {

	}

	void PreProcess() {

	}
	
	void initialise(int t,int k) {
		dist = new int[V][k]; 			// 2D array, dist[destination][number of hops LEFT]
		
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < k; j++) {
				if (i == t) { 			// Dist from same spot to same spot is 0
					dist[i][j] = 0;
				} else if (j == 0) { 	// No more hops left, means it's unreachable
					dist[i][j] = maxweight;
				} else { 				// Every other entries
					dist[i][j] = -1;
				}
			}
		}
	}
	
	int sssp(int vtx,int hopsLeft) {
		int ans = maxweight;	// If unreachable
		
		if(hopsLeft == 0) {			// No more hops left
			return dist[vtx][hopsLeft];
		}

		if(dist[vtx][hopsLeft] != -1) {	// If not equal -1, means it has been processed. Therefore can return result in dist[][]
			return dist[vtx][hopsLeft];
		}
		
		// Else if equals -1, meaning not yet processed
		// Process by: Finding the CLOSEST neighbour
		ArrayList<IntegerPair> neighbours = AdjList.get(vtx); 		// Get all the neighbours of this vertex
		for(int i = 0; i < neighbours.size(); i++) {				// Loop through all the neighbours
			int neigh = neighbours.get(i).first();
			int weight = neighbours.get(i).second();
			int w = sssp(neigh,hopsLeft-1) + weight;				// The distance from subpath of neighbour + weight of this edge leading to the neighbour
			ans = Math.min(ans,w);									// Find the minimum of(current shortest path + weight) for all the neighbours
		}

		dist[vtx][hopsLeft] = ans;									// Update our dist[][] after it is processed
		return ans;
	}
	
	int Query(int s, int t, int k) {
		int ans = -1;
		
		initialise(t,k);		// Array stores all the distance to reach destination t with the given number of hops
		
		ans = sssp(s,k-1); // k-1 because source takes the first hop. Given the array of destination, find the shortest dist from source
		
		if (ans == maxweight) {
			ans = -1;
		}
		
		return ans;
	}

	void run() throws Exception {
		// you can alter this method if you need to do so
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();

			// clear the graph and read in a new graph as Adjacency List
			AdjList = new ArrayList < ArrayList < IntegerPair > >();
			for (int i = 0; i < V; i++) {
				AdjList.add(new ArrayList < IntegerPair >());

				int k = sc.nextInt();
				while (k-- > 0) {
					int j = sc.nextInt(), w = sc.nextInt();
					AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
				}
			}

			PreProcess();

			Q = sc.nextInt();
			while (Q-- > 0)
				pr.println(Query(sc.nextInt(), sc.nextInt(), sc.nextInt()));

			if (TC > 0)
				pr.println();
		}

		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		Bleeding ps5 = new Bleeding();
		ps5.run();
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
