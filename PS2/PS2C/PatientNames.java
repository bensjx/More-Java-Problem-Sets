import java.util.*;
import java.io.*;

class PatientNames {
	AVL male = new AVL(); //tree to store males
	AVL female = new AVL(); //tree to store females

	public PatientNames() {
	}

	void AddPatient(String patientName, int gender) {
		if (gender == 1) {
			male.insert(patientName);
		} else {
			female.insert(patientName);
		}
	}

	int Query(String START, String END, int gender) {
		int ans = 0;

		if (gender != 1) { 													//female and all	
			int tempFemaleUpper = female.getUpperRank(END);
			int tempFemaleLower = female.getLowerRank(START);
			if (tempFemaleUpper == -1 || tempFemaleLower == -1) { 				//out of range
				ans += 0;
			} else {
				ans += tempFemaleUpper - tempFemaleLower + 1;
			}
		}
		if (gender != 2) {												//male and all
			int tempMaleUpper = male.getUpperRank(END);
			int tempMaleLower = male.getLowerRank(START);
			if (tempMaleUpper == -1 || tempMaleLower == -1) { 					//out of range
				ans += 0;
			} else {
				ans += tempMaleUpper - tempMaleLower + 1;
			}
		}
		return ans;
	}

	void run() throws Exception {
		// do not alter this method to avoid unnecessary errors with the automated judging
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		while (true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			if (command == 0) // end of input
				break;
			else if (command == 1) // AddPatient
				AddPatient(st.nextToken(), Integer.parseInt(st.nextToken()));
			//else if (command == 2) // RemovePatient
			//RemovePatient(st.nextToken());
			else //if (command == 3) // Query
				pr.println(Query(st.nextToken(), // START
						st.nextToken(), // END
						Integer.parseInt(st.nextToken()))); // GENDER
		}
		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method to avoid unnecessary errors with the automated judging
		PatientNames ps2 = new PatientNames();
		ps2.run();
	}
}

class AVLNode {
	public String _key;
	public int _height,_weight;
	public AVLNode _parent, _left, _right;

	AVLNode(String key){
		_key = key;
		_parent = _left = _right = null;
		_height = 0;
		_weight = 1;
	}
	
}

class AVL{
	public AVLNode _root;

	public AVL() {
		_root = null;
	}

	public int getHeight(AVLNode node) { 			//returns the height of current node, taking into account if node doesn't exist
		if (node == null) {
			return -1;
		} else {
			return node._height;
		}
	}

	public int getWeight(AVLNode node) { 			//returns the height of current node, taking into account if node doesn't exist
		if (node == null) {
			return 0;
		} else {
			return node._weight;
		}
	}

	public AVLNode rotateLeft(AVLNode node) {
		AVLNode newMid = node._right;
		newMid._parent = node._parent; 				//transfer parent to the new "root"
		node._parent = newMid; 						//node is now the child of the new "root"
		node._right = newMid._left; 				//transfer child of new "root" down to the old "root"
		if (newMid._left != null) {
			newMid._left._parent = node; 			//set parent of the child that is transferred down
		}
		newMid._left = node; 						//update child of the new "root"

		//update heights
		node._height = Math.max(getHeight(node._left), getHeight(node._right)) + 1;
		node._weight = getWeight(node._left) + getWeight(node._right) + 1;
		//update weights
		newMid._height = Math.max(getHeight(newMid._left), getHeight(newMid._right)) + 1;
		newMid._weight = getWeight(newMid._left) + getWeight(newMid._right) + 1;

		return newMid;
	}

	public AVLNode rotateRight(AVLNode node) { 		//mirror of rotateLeft
		AVLNode newMid = node._left;
		newMid._parent = node._parent;
		node._parent = newMid;
		node._left = newMid._right;
		if (newMid._right != null) {
			newMid._right._parent = node;
		}
		newMid._right = node;

		//update heights
		node._height = Math.max(getHeight(node._left), getHeight(node._right)) + 1;
		node._weight = getWeight(node._left) + getWeight(node._right) + 1;
		//update weights
		newMid._height = Math.max(getHeight(newMid._left), getHeight(newMid._right)) + 1;
		newMid._weight = getWeight(newMid._left) + getWeight(newMid._right) + 1;

		return newMid;
	}


	public AVLNode insert(AVLNode node, String patientName) {
		//Inserting the new string
		if (node == null) {
			node = new AVLNode(patientName);
			return node; 										//insert here
		} else if (node._key.compareTo(patientName) < 0 ) { 	//go right
			node._right = insert(node._right,patientName); 		//recurse to the right
			node._right._parent = node; 						//update parent
		} else { 												//go left
			node._left = insert(node._left,patientName); 		//recurse to the left
			node._left._parent = node; 							//update parent
		}

		// Balancing the tree
		node = balanceTree(node);

		// Updating the height and weight
		node._height = Math.max(getHeight(node._left), getHeight(node._right)) + 1;
		node._weight = getWeight(node._left) + getWeight(node._right) + 1;

		return node;
	}

	public void insert(String patientName) { //overloading
		_root = insert(_root, patientName); 
	}

	public AVLNode balanceTree(AVLNode node) {
		int balanceMain = getHeight(node._left) - getHeight(node._right); 					//node._left could be null -> return -1
		if (balanceMain == 2) { 															//go to the left tree
			int balanceSub = getHeight(node._left._left) - getHeight(node._left._right); 	//check if LL or LR
			if (balanceSub == 1) { 															//LL
				node = rotateRight(node);
			}
			else { 																			//LR
				node._left = rotateLeft(node._left); 										//make it LL first
				node = rotateRight(node); 													//rotate right
			}
		}
		else if (balanceMain == -2) { 														//go to the right tree
			int balanceSub = getHeight(node._right._left) - getHeight(node._right._right); 	//check if RR or RL
			if (balanceSub == -1) { 														//RR
				node = rotateLeft(node);
			}
			else { 																			//RL
				node._right = rotateRight(node._right); 									//make it RR first
				node = rotateLeft(node); 													//rotate left
			}
		}
		return node;
	}

	public int getRank(AVLNode node, String patientName) {
		if (node == null) { 														//empty node have no weight
			return 0;
		} else if (node._key.compareTo(patientName) == 0) { 						//found
			if (node._left != null) { 												//edge case
				return node._left._weight + 1; 										// +1 for the current node
			} else {
				return 1; 															//does not have any left tree
			}
		} else if (node._key.compareTo(patientName) > 0) {
			return getRank(node._left, patientName);
		} else { 																	//go to the right tree
			if (node._left != null) {
				return node._left._weight + 1 + getRank(node._right,patientName); 	//parent weight + left tree weight + 1
			} else {
				return 1 + getRank(node._right,patientName);
			}
		}
	}

	public int getRank(String patientName) { 									//overload
		return getRank(_root, patientName);
	}

	public int getUpperRank(AVLNode node, String upperBound, int rank) {
		if (node == null) {
			if (rank == Integer.MIN_VALUE) {
				return -1;														//no nodes entered the range
			} else {
				return rank;
			}
		} else if (node._key.compareTo(upperBound) == 0) {						//bound is right open
			return getUpperRank(node._right,upperBound,rank);
		} else if (node._key.compareTo(upperBound) < 0) {						//entered the range
			int newRank = getRank(node._key);
			if (newRank > rank) {
				return getUpperRank(node._right,upperBound,newRank);			//search right to look for bigger nodes, update ranking
			} else {
				return getUpperRank(node._right,upperBound,rank);
			}
		} else if (node._key.compareTo(upperBound) > 0) {						//search left to enter the bound
			return getUpperRank(node._left,upperBound,rank);					//ranking unchanged
		} else {
			return -1;
		}
	}

	public int getUpperRank(String upperBound) { 								//overload
		return getUpperRank(_root, upperBound, Integer.MIN_VALUE);
	}

	public int getLowerRank(AVLNode node, String lowerBound, int rank) {
		if (node == null) {
			if (rank == Integer.MAX_VALUE) {
				return -1;														//no nodes entered the range
			} else {
				return rank;
			}
		} else if (node._key.compareTo(lowerBound) >= 0) {						//entered the range
			int newRank = getRank(node._key);
			if (newRank < rank) {
				return getLowerRank(node._left,lowerBound,newRank);				//search left to look for smaller nodes, update ranking
			} else {
				return getLowerRank(node._left,lowerBound,rank);
			}
		} else if (node._key.compareTo(lowerBound) < 0) {						//search right to enter the bound
			return getLowerRank(node._right,lowerBound,rank);					//ranking unchanged
		} else {
			return -1;
		}
	}

	public int getLowerRank(String lowerBound) { 								//overload
		return getLowerRank(_root,lowerBound,Integer.MAX_VALUE);
	}
}
