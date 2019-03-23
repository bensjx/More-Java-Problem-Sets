// Copy paste this Java Template and save it as "EmergencyRoom.java"
import java.util.*;
import java.io.*;


public class EmergencyRoom {

	private PriorityQueue<Patient> hospital; // Queue in hospital
	private int hospitalSize; // Number of patients
	private HashMap<String, Patient> nameToPatient; // Name list

	public void EmergencyRoom() {
		hospital = new PriorityQueue<Patient>();
		hospitalSize = 0;
		nameToPatient = new HashMap<String, Patient>();
	}

	private void ArriveAtHospital(String patientName, int emergencyLvl) {
		hospitalSize++;
		Patient patient = new Patient(patientName, emergencyLvl, hospitalSize); // Construct a patient
		hospital.offer(patient); // Enqueue
		nameToPatient.put(patientName, patient); // Store to name list
		
	}

	private void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
		Patient patient = nameToPatient.get(patientName);
		patient.treat(); // Set as treat so as to 'remove' the old patient
		Patient increasedPatient = new Patient(patientName,
				patient.getEmergencyLvl() + incEmergencyLvl, patient.getArrivalTime()); // Clone the patient
		hospital.offer(increasedPatient); // Enqueue the cloned patient ('untreated')
		nameToPatient.put(patientName, increasedPatient); // Replace the old patient with new patient
	}

	private void Treat(String patientName) {
		Patient patient = nameToPatient.get(patientName); // Get the patient
		patient.treat();
		hospitalSize--; // Reduce number of patients
		nameToPatient.remove(patientName); // Remove from name list
	}

	private String Query() {
		String ans = "The emergency suite is empty";
		if (!nameToPatient.isEmpty()) {
			// Remove all the treated patients
			while (!hospital.isEmpty() && hospital.peek().isTreated()) {
				hospital.poll();
			}
			// Return the top patient that is not treated yet
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

class Patient implements Comparable<Patient>{
	private String _name;
	private int _eLevel;
	private int _arrivalTime;
	private boolean _treat;

	public Patient(String name, int eLevel,int arrivalTime) {
		_name = name;
		_eLevel = eLevel;
		_arrivalTime = arrivalTime;
		_treat = false;
	}
	
	public void treat() {
		_treat = true;
	}
	
	public boolean isTreated() {
		return _treat;
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
