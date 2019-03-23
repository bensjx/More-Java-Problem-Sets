// Copy paste this Java Template and save it as "Bleeding.java"
import java.util.*;
import java.io.*;

class Bleeding {
	private int V; // number of vertices in the graph (number of junctions in Singapore map)
	private int Q; // number of queries
	private ArrayList < ArrayList < IntegerPair > > AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge
	private int[][] result;
	private int[] dist;
	private Queue<IntegerPair> pq;

	public Bleeding() {

	}

	void PreProcess() {
		dist = new int[V];
		result = new int[10][V];
		pq = new LinkedList<IntegerPair>();
		
		for (int i = 0; i < Math.min(10,V); i++) {
			dijkstra(i);
			for (int j = 0; j < V; j++) {
				result[i][j] = dist[j];
			}
		}
	}

	int Query(int s, int t, int k) {
		int ans = result[s][t];
		if (ans == Integer.MAX_VALUE) {
			return -1;
		} else {
			return ans;
		}
	}
	
	
	void dijkstra(int start) {
		// initSSSP
		for (int i = 0; i < V; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		dist[start] = 0;
		pq.offer(new IntegerPair(0,start));
		
		while(!pq.isEmpty()) {
			IntegerPair temp = pq.poll();
			int parent = temp.second();
			int d = temp.first();
			if (d == dist[parent]) {
				ArrayList<IntegerPair> neighbours = AdjList.get(parent);
				for (int i = 0; i < neighbours.size(); i++) {
					int v = neighbours.get(i).first();
					int w = neighbours.get(i).second();
					relax(parent,v,w);
				}
			}
		}
		
	}
	
	void relax(int u, int v, int w) {
		if (dist[v] > dist[u] + w) {
			dist[v] = dist[u] + w;
			pq.offer(new IntegerPair(dist[v],v));
		}
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
