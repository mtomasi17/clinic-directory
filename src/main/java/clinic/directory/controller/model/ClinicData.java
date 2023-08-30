package clinic.directory.controller.model;

import java.util.HashSet;
import java.util.Set;

import clinic.directory.entity.Clinic;
import clinic.directory.entity.Employee;
import clinic.directory.entity.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data model representing clinic information, including its patients and employees.
 */

@Data
@NoArgsConstructor
public class ClinicData {


	private Long clinicId;	
	private String clinicName;
	private String clinicAddress;
	private String clinicCity;
	private String clinicState;
	private String clinicZip;
	private String clinicPhone;
	
	
	private Set<ClinicPatient> patients = new HashSet<>();	
	private Set<ClinicEmployee> employees = new HashSet<>();
	
	// Constructor that initializes the ClinicData instance based on a Clinic entity.
	
	public ClinicData(Clinic clinic) {
		clinicId = clinic.getClinicId();
		clinicName = clinic.getClinicName();
		clinicAddress = clinic.getClinicAddress();
		clinicCity = clinic.getClinicCity();
		clinicState = clinic.getClinicState();
		clinicZip = clinic.getClinicZip();
		clinicPhone = clinic.getClinicPhone();
		
		// Mapping Patients from Clinic entity to ClinicPatient objects.
		
		for(Patient patient : clinic.getPatients()) {
			patients.add(new ClinicPatient(patient));
		}
		
		 // Mapping Employees from Clinic entity to ClinicEmployee objects.
		
		for(Employee employee : clinic.getEmployees()){
			employees.add(new ClinicEmployee(employee));
		}
	}
	// Inner class representing patient information within ClinicData.
	
	@Data
	@NoArgsConstructor
	public static class ClinicPatient {
		private Long patientId; 
		private String patientFirstName;
		private String patientLastName;
		private String patientEmail;
		
		// Constructor that initializes ClinicPatient based on a Patient entity.
		
		public ClinicPatient(Patient patient) {
			patientId = patient.getPatientId();
			patientFirstName = patient.getPatientFirstName();
			patientLastName = patient.getPatientLastName();
			patientEmail = patient.getPatientEmail();
		}
	}
	
	// Inner class representing employee information within ClinicData.
	
	@Data
	@NoArgsConstructor
	public static class ClinicEmployee {
		private Long employeeId; 
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitle;
		
		// Constructor that initializes ClinicEmployee based on an Employee entity.
		
		public ClinicEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeeJobTitle = employee.getEmployeeJobTitle();
		}
	}
}

