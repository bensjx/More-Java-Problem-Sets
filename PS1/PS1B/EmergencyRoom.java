// Copy paste this Java Template and save it as "EmergencyRoom.java"
import java.util.*;
import java.io.*;
import java.lang.*;


public class EmergencyRoom {
	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	private PriorityQueue<Patient> hospital;
	private int hospitalSize;

	private void EmergencyRoom() {
		// Write necessary code during construction
		//
		// write your answer here
		hospital = new PriorityQueue<Patient>();
	}

	private void ArriveAtHospital(String patientName, int emergencyLvl) {
		// You have to insert the information (patientName, emergencyLvl)
		// into your chosen data structure
		//
		// write your answer here
		hospitalSize++;
		Patient patient = new Patient(patientName, emergencyLvl, hospitalSize); //construct a patient
		hospital.offer(patient);
	}

	private void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
		// not required in subtask B
	}

	private void Treat(String patientName) {
		// The method Treat(patientName) is always called for the patient currently
		// under the doctor's highest priority (you can view this as Treat(Query()))=
		hospital.poll();
		hospitalSize--;
	}
	
	private String Query() {
		String ans = "The emergency suite is empty";

		// You have to report the name of the patient that the doctor
		// has to give the most attention to currently. If there is no more patient to
		// be taken care of, return a String "The emergency suite is empty"
		//
		// write your answer here
		if (hospital.size() >= 1) {
			return hospital.peek().getName();
		} else {
			return ans;
		}
	}

	private void run() throws Exception {
		EmergencyRoom();
		// do not alter this method
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
		while (numCMD-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			switch (command) {
			case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
			//case 1: UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken())); break;
			case 2: Treat(st.nextToken()); break;
			case 3: pr.println(Query()); break;
			}
		}
		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		EmergencyRoom ps1 = new EmergencyRoom();
		ps1.run();
	}
}

class Patient implements Comparable<Patient>{
	private String _name;
	private int _eLevel;
	private int _arrivalTime;

	public Patient(String name, int eLevel,int arrivalTime) {
		_name = name;
		_eLevel = eLevel;
		_arrivalTime = arrivalTime;
	}
	
	public int compareTo(Patient patient) {
		if (_eLevel > patient.getEmergencyLvl()) {
			return -1;
		} else if (_eLevel == patient.getEmergencyLvl()
				&& _arrivalTime < patient.getArrivalTime()) {
			return -1;
		} else {
			return  1;
		}
	}

	public void increaseEmergencyLvl(int incLvl) {
		_eLevel += incLvl;
	}

	public int getEmergencyLvl() {
		return _eLevel;
	}

	public String getName() {
		return _name;
	}

	public int getArrivalTime() {
		return _arrivalTime;
	}

}
