// Copy paste this Java Template and save it as "EmergencyRoom.java"
import java.util.*;
import java.io.*;
import java.lang.*;



public class EmergencyRoom {
	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	public HashMap<String,Patient> hospital = new HashMap<String,Patient>();
	public int order = 0;

	public EmergencyRoom() {
		// Write necessary code during construction
		//
		// write your answer here
	}

	void ArriveAtHospital(String patientName, int emergencyLvl) {
		// You have to insert the information (patientName, emergencyLvl)
		// into your chosen data structure
		//
		// write your answer here
		order++;
		hospital.put(patientName, new Patient(patientName,emergencyLvl,order));
	}

	void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
		// You have to update the emergencyLvl of patientName to
		// emergencyLvl += incEmergencyLvl
		// and modify your chosen data structure (if needed)
		//
		// write your answer here
		Patient patient = hospital.get(patientName);
		patient.increaseEmergencyLvl(incEmergencyLvl);
	}

	void Treat(String patientName) {
		// This patientName is treated by the doctor
		// remove him/her from your chosen data structure
		//
		// write your answer here
		hospital.remove(patientName);
		order--;
	}

	String Query() {
		String ans = "The emergency suite is empty";

		// You have to report the name of the patient that the doctor
		// has to give the most attention to currently. If there is no more patient to
		// be taken care of, return a String "The emergency suite is empty"
		//
		// write your answer here
		if (order != 0) {
			int maxEmer = 0;
			int minOrder = Integer.MAX_VALUE;
			String maxName = "";
			for (String name: hospital.keySet()) {
				if (hospital.get(name).getEmergencyLvl() > maxEmer) {
					maxEmer = hospital.get(name).getEmergencyLvl();
					maxName = name;
					minOrder = hospital.get(name).getArrivalTime();
				}
				if (hospital.get(name).getEmergencyLvl() == maxEmer
						&& hospital.get(name).getArrivalTime() < minOrder) {
					maxName = name;
					minOrder = hospital.get(name).getArrivalTime();
				}
			}
			return maxName;
		} else {
			return ans;
		}
	}

	void run() throws Exception {
		// do not alter this method

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
		while (numCMD-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			switch (command) {
			case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
			case 1: UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken())); break;
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

class Patient{
	private String _name;
	private int _eLevel;
	private int _arrivalTime;

	public Patient(String name, int eLevel,int arrivalTime) {
		_name = name;
		_eLevel = eLevel;
		_arrivalTime = arrivalTime;
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
