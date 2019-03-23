// Copy paste this Java Template and save it as "HospitalRenovation.java"
import java.util.*;
import java.io.*;

class HospitalRenovation {
	private int V; // number of vertices in the graph (number of rooms in the hospital)
	private ArrayList<ArrayList<Integer>> hospital; // the graph (the hospital)
	private int[] RatingScore; // the weight of each vertex (rating score of each room)

	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	private int[] visited;
	private int[] parent;
	private Queue<Integer> queue;

	public HospitalRenovation() {
	}

	int Query() { // Implement BFS
		int ans = Integer.MAX_VALUE;
		boolean hasCrit = false; // no crit room

		// To check if a vertex is critical, remove it and see if the number of components increases. If no increase, means not critical.
		for (int i = 0; i < V; i++) { // Loop through all vertex O(V)
			// Mark for i to be removed. Do not visit i at all.
			int finalComponent = componentBFS(i);	// Count number of components in this new graph O(V+E)
			if (finalComponent > 1) { 				// More than 1 component means graph is disconnected. Hence this is a critical room
				hasCrit = true;
				ans = Math.min(ans, RatingScore[i]);	// Only look at rating score if this is a critical room
			}
		}

		if (hasCrit) {
			return ans;
		} else {
			return -1;
		}
	}

	int componentBFS(int removed){
		int component = 0;

		// Initialise visited array
		visited = new int[V];
		for (int i = 0; i < V; i ++) {
			visited[i] = 0;
		}

		for (int v = 0; v < V; v ++) { // This is NOT O(V)
			// Do not visit the removed vertex
			if (v != removed && visited[v] == 0) { // If this vertex have not been visited, increase number of components and carry out BFS
				component++;

				// Carry out BFS only if this vertex have not been visited yet
				queue = new LinkedList<Integer>(); // Initialise queue
				queue.offer(v); // Set source to v
				visited[v] = 1; // Set source to v

				while (!queue.isEmpty()) {
					int vertex = queue.poll(); // Get the vertex concerned
					ArrayList<Integer> neighbourVertex = hospital.get(vertex); // A list of all neighbour of vertex concerned
					for (int i = 0; i < neighbourVertex.size(); i++) { // Loop through all neighbours
						int neighbour = neighbourVertex.get(i);
						// Do not visit removed vertexx
						if (neighbour != removed && visited[neighbour] == 0) { 		// If neighbour is not visited yet
							visited[neighbour] = 1; 			// Visit this neighbour
							queue.offer(neighbour); 			// Set this neighbour to the vertex concerned for next step (i.e. visit all neighbours of this neighbour)
						}
					}
				}
			}
		}

		return component;
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

			// clear the graph and read in a new graph as Adjacency List
			hospital = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i < V; i++) {
				st = new StringTokenizer(br.readLine());
				int k = Integer.parseInt(st.nextToken());
				ArrayList<Integer> temp = new ArrayList<Integer>();
				while (k-- > 0) {
					int j = Integer.parseInt(st.nextToken());
					temp.add(j);
				}
				hospital.add(temp);
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
