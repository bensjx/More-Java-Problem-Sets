// Copy paste this Java Template and save it as "PatientNames.java"
import java.util.*;
import java.io.*;

class PatientNames {
  TreeMap<String,Integer> hospital;
  

  public PatientNames() {
    hospital = new TreeMap<String,Integer>();
  }

  void AddPatient(String patientName, int gender) {
    hospital.put(patientName,gender);
  }

  void RemovePatient(String patientName) {
    hospital.remove(patientName);
  }

  int Query(String START, String END, int gender) {
    int ans = 0;
    
    SortedMap<String, Integer> temp = hospital.subMap(START, END);
    for (int genders: temp.values()) {
    	if (gender == 0 || genders == gender) {
    		ans++;
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
      else if (command == 2) // RemovePatient
        RemovePatient(st.nextToken());
      else // if (command == 3) // Query
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
