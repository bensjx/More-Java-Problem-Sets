// Copy paste this Java Template and save it as "GettingFromHereToThere.java"
import java.util.*;
import java.io.*;


class GettingFromHereToThere {
	private int V, count; // number of vertices in the graph (number of rooms in the building)
	private Vector < Vector < IntegerTriple > > AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too
	private static ArrayList<Boolean> taken;
	private static PriorityQueue <IntegerTriple> pq;
	private ArrayList<ArrayList<IntegerTriple>> mst;
	private int[][] results;


	public GettingFromHereToThere() {

	}

	void PreProcess() {
		prim();
		for (int i = 0; i < Math.min(V,10); i++) {
			Queue<IntegerTriple> maxWeights = new LinkedList<IntegerTriple>();
			Boolean[] visited = new Boolean[V];

			for (int k = 0; k < V; k++) {
				visited[k] = false;
			}

			for (int j = 0; j < mst.get(i).size(); j++) {
				visited[mst.get(i).get(j).first()] = true;
				maxWeights.offer(mst.get(i).get(j));
			}

			while (!maxWeights.isEmpty()) {
				IntegerTriple top = maxWeights.poll();
				int dest, weight, src;
				dest = top.first();
				weight = top.second();
				src = top.third();

				results[i][dest] = Math.max(weight, results[i][src]);
				for (int j = 0; j < mst.get(dest).size(); j++) {
					if (!visited[mst.get(dest).get(j).first()]) {
						visited[mst.get(dest).get(j).first()] = true;
						maxWeights.offer(mst.get(dest).get(j));
					}
				}
			}
		}
	}

	int Query(int source, int destination) {
		return results[source][destination];
	}

	void process(int vtx) {
		taken.set(vtx, true);
		count++;
		for (int j = 0; j < AdjList.get(vtx).size(); j++) {
			IntegerTriple v = AdjList.get(vtx).get(j);
			if (!taken.get(v.first())) {
				pq.offer(new IntegerTriple(v.second(), v.first(), vtx));  // we sort by weight then by adjacent vertex
			}
		}
	}

	void prim() {
		taken = new ArrayList<Boolean>();
		taken.addAll(Collections.nCopies(V, false));
		pq = new PriorityQueue <IntegerTriple> ();
		// take any vertex of the graph, for simplicity, vertex 0, to be included in the MST
		process(0);

		while (count != V) { // we will do this until all V vertices are taken (or E = V-1 edges are taken)
			IntegerTriple front = pq.poll();
			int src = front.third();
			int weight = front.first();
			int dest = front.second();

			if (!taken.get(front.second())) { // we have not connected this vertex yet
				mst.get(src).add(new IntegerTriple(dest,weight,src));
				mst.get(dest).add(new IntegerTriple(src,weight,dest));
				process(dest);
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
			count = 0;
			results = new int[10][V];

			// clear the graph and read in a new graph as Adjacency List
			AdjList = new Vector < Vector < IntegerTriple > >();
			mst = new ArrayList<ArrayList<IntegerTriple>>();
			for (int i = 0; i < V; i++) {
				AdjList.add(new Vector < IntegerTriple >());
				mst.add(new ArrayList<IntegerTriple>());

				int k = sc.nextInt();
				while (k-- > 0) {
					int j = sc.nextInt(), w = sc.nextInt();
					AdjList.get(i).add(new IntegerTriple(j, w, i)); // edge (corridor) weight (effort rating) is stored here
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
