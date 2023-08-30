package clinic.directory.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clinic.directory.controller.model.ClinicData;
import clinic.directory.controller.model.ClinicData.ClinicEmployee;
import clinic.directory.dao.ClinicDao;
import clinic.directory.dao.EmployeeDao;
import clinic.directory.entity.Clinic;
import clinic.directory.entity.Employee;
import clinic.directory.entity.Patient;
import clinic.directory.repository.ClinicRepository;
import clinic.directory.repository.PatientRepository;

@Service
public class ClinicService {
	
	@Autowired
	private ClinicDao clinicDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private ClinicRepository clinicRepository;
	
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    public ClinicService(ClinicDao clinicDao) {
        this.clinicDao = clinicDao;
    }
    
    // Method to save or update clinic data
    
    @Transactional
    public ClinicData saveClinic(ClinicData clinicData) {
        Long clinicId = clinicData.getClinicId();
        Clinic clinic = findOrCreateClinic(clinicId);

        copyClinicFields(clinic, clinicData);

        return new ClinicData(clinicDao.save(clinic));
    }

    // Helper method to find or create a clinic
    
    private Clinic findOrCreateClinic(Long clinicId) {
        if (Objects.isNull(clinicId)) {
            return new Clinic();
        } else {
            return findClinicById(clinicId);
        }
    }

    // Helper method to find a clinic by ID
    
    private Clinic findClinicById(Long clinicId) {
        return clinicDao.findById(clinicId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Clinic with ID=" + clinicId + " was not found."));
    }
    
    // Method to copy relevant clinic fields from ClinicData

    private void copyClinicFields(Clinic clinic, ClinicData clinicData) {
        clinic.setClinicName(clinicData.getClinicName());
        clinic.setClinicAddress(clinicData.getClinicAddress());
        clinic.setClinicCity(clinicData.getClinicCity());
        clinic.setClinicState(clinicData.getClinicState());
        clinic.setClinicZip(clinicData.getClinicZip());
        clinic.setClinicPhone(clinicData.getClinicPhone());

	}
    
    // Method to save an employee and associate with a clinic
    
    @Transactional(readOnly = false)
    public ClinicEmployee saveEmployee(Long clinicId, ClinicEmployee clinicEmployee) {
        Clinic clinic = findClinicById(clinicId);
        Employee employee = findOrCreateEmployee(clinicId, clinicEmployee.getEmployeeId());

        copyEmployeeFields(employee, clinicEmployee);

        employee.setClinic(clinic);
        clinic.getEmployees().add(employee);

        Employee savedEmployee = employeeDao.save(employee);

        return new ClinicEmployee(savedEmployee);
    }
    
    // Helper method to find an employee by ID within a clinic
    
    private Employee findEmployeeById(Long clinicId, Long employeeId) {
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + employeeId));

        if (!employee.getClinic().getClinicId().equals(clinicId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId +
                    " does not belong to clinic with ID " + clinicId);
        }

        return employee;
    }
    
    // Helper method to find or create an employee
    
    private Employee findOrCreateEmployee(Long clinicId, Long employeeId) {
        if (employeeId == null) {
            return new Employee();
        } else {
            return findEmployeeById(clinicId, employeeId);
        }
    }
    
    // Method to copy employee fields from ClinicEmployee
    
    private void copyEmployeeFields(Employee employee, ClinicEmployee clinicEmployee) {
        employee.setEmployeeFirstName(clinicEmployee.getEmployeeFirstName());
        employee.setEmployeeLastName(clinicEmployee.getEmployeeLastName());
        employee.setEmployeePhone(clinicEmployee.getEmployeePhone());
        employee.setEmployeeJobTitle(clinicEmployee.getEmployeeJobTitle());
    }

    // Method to retrieve all clinics
    
    @Transactional
    public List<ClinicData> retrieveAllClinics() {
        return clinicDao.findAll().stream()
                .map(this::convertToClinicData)
                .collect(Collectors.toList());
    }
    
    // Method to convert a Clinic to ClinicData

    private ClinicData convertToClinicData(Clinic clinic) {
        ClinicData clinicData = new ClinicData();
        clinicData.setClinicId(clinic.getClinicId());
        clinicData.setClinicName(clinic.getClinicName());
        clinicData.setClinicAddress(clinic.getClinicAddress());
        clinicData.setClinicCity(clinic.getClinicCity());
        clinicData.setClinicState(clinic.getClinicState());
        clinicData.setClinicZip(clinic.getClinicZip());
        clinicData.setClinicPhone(clinic.getClinicPhone());
        return clinicData;
    }
    
    // Method to get clinic data by ID
    
    @Transactional
    public ClinicData getClinicById(Long clinicId) {
        Clinic clinic = findClinicById(clinicId);
        return convertToClinicData(clinic);
    }
    
    // Method to delete a clinic by ID
    
    @Transactional
    public void deleteClinicById(Long clinicId) {
        Clinic clinic = findClinicById(clinicId);
        
        for (Employee employee : clinic.getEmployees()) {
            employeeDao.delete(employee);
        }
        
        clinic.getPatients().clear(); 
        
        clinicDao.delete(clinic);
    }
    
    // Method to retrieve all employees of a clinic
    
    @Transactional(readOnly = true)
    public List<ClinicData.ClinicEmployee> getAllEmployees(Long clinicId) {
        Clinic clinic = findClinicById(clinicId);

        return clinic.getEmployees().stream()
                .map(this::convertToClinicEmployee)
                .collect(Collectors.toList());
    }

    
    // Method to convert an Employee to ClinicEmployee
    private ClinicData.ClinicEmployee convertToClinicEmployee(Employee employee) {
        return new ClinicData.ClinicEmployee(employee);
    }
    
    // Method to delete an employee from a clinic
    
    @Transactional
    public void deleteEmployee(Long clinicId, Long employeeId) {
        Clinic clinic = findClinicById(clinicId);
        Employee employee = findEmployeeById(clinicId, employeeId);
        
        clinic.getEmployees().remove(employee);
        employeeDao.delete(employee);
    }

    // Updates employee information within a specific clinic.
    
    public ClinicEmployee updateEmployee(Long clinicId, Long employeeId, ClinicData.ClinicEmployee updatedEmployee) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new NoSuchElementException("Clinic not found with id: " + clinicId));

        Set<Employee> employees = clinic.getEmployees();
        Optional<Employee> existingEmployee = employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst();

        if (existingEmployee.isPresent()) {
            Employee employeeToUpdate = existingEmployee.get();
            employeeToUpdate.setEmployeeFirstName(updatedEmployee.getEmployeeFirstName());
            employeeToUpdate.setEmployeeLastName(updatedEmployee.getEmployeeLastName());
            employeeToUpdate.setEmployeePhone(updatedEmployee.getEmployeePhone());
            employeeToUpdate.setEmployeeJobTitle(updatedEmployee.getEmployeeJobTitle());

            // Save the clinic entity to update the employee's information
            clinicRepository.save(clinic); // Use the clinic repository

            return new ClinicData.ClinicEmployee(employeeToUpdate); // Return the updated ClinicEmployee
        } else {
            throw new NoSuchElementException("Employee not found in the clinic.");
        }
    }
    
    // Retrieves a list of patients associated with a specific clinic.
    
    @Transactional(readOnly = true)
    public List<ClinicData.ClinicPatient> getAllPatients(Long clinicId) {
        Clinic clinic = findClinicById(clinicId);

        return clinic.getPatients().stream()
                .map(this::convertToClinicPatient)
                .collect(Collectors.toList());
    }

    // Convert a Patient to ClinicPatient
    
    private ClinicData.ClinicPatient convertToClinicPatient(Patient patient) {
        return new ClinicData.ClinicPatient(patient);
    }
    
    // Saves a new patient's information for a given clinic.
    
    @Transactional(readOnly = false)
    public ClinicData.ClinicPatient savePatient(Long clinicId, ClinicData.ClinicPatient clinicPatient) {
        Clinic clinic = findClinicById(clinicId);
        Patient patient = new Patient();

        copyPatientFields(patient, clinicPatient);

        clinic.getPatients().add(patient);
        patient.getClinic().add(clinic);

        Patient savedPatient = patientRepository.save(patient); // Call the instance method

        return new ClinicData.ClinicPatient(savedPatient);
    }

    // Method to copy patient fields from ClinicPatient
    private void copyPatientFields(Patient patient, ClinicData.ClinicPatient clinicPatient) {
        patient.setPatientFirstName(clinicPatient.getPatientFirstName());
        patient.setPatientLastName(clinicPatient.getPatientLastName());
        patient.setPatientEmail(clinicPatient.getPatientEmail());
    }
    
    // Updates patient information for a given clinic and patient ID.
    
    @Transactional
    public ClinicData.ClinicPatient updatePatient(Long clinicId, Long patientId, ClinicData.ClinicPatient updatedPatient) {
        Clinic clinic = findClinicById(clinicId);
        Patient patientToUpdate = findPatientInClinic(clinic, patientId);

        copyPatientFields(patientToUpdate, updatedPatient);

        // Save the clinic entity to update the patient's information
        clinicRepository.save(clinic);

        return new ClinicData.ClinicPatient(patientToUpdate);
    }
    
    // Helper method to find a patient within a clinic by patient ID.
    
    private Patient findPatientInClinic(Clinic clinic, Long patientId) {
        return clinic.getPatients().stream()
                .filter(patient -> patient.getPatientId().equals(patientId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Patient not found with ID: " + patientId));
    }

    // Deletes a patient from a clinic by clinic ID and patient ID.
    
    @Transactional
    public void deletePatient(Long clinicId, Long patientId) {
        Clinic clinic = findClinicById(clinicId);
        Patient patientToDelete = findPatientInClinic(clinic, patientId);
        
        clinic.getPatients().remove(patientToDelete);
        patientRepository.delete(patientToDelete);
    }
    
    // Retrieves a list of all patients for a specific clinic by clinic ID.
    
    @Transactional(readOnly = true)
    public List<ClinicData.ClinicPatient> getAllPatientsForClinic(Long clinicId) {
        Clinic clinic = findClinicById(clinicId);

        return clinic.getPatients().stream()
                .map(this::convertToClinicPatient)
                .collect(Collectors.toList());
    }
}



