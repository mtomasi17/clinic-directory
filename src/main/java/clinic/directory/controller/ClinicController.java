package clinic.directory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import clinic.directory.controller.model.ClinicData;
import clinic.directory.controller.model.ClinicData.ClinicEmployee;
import clinic.directory.service.ClinicService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/clinic")
@Slf4j
public class ClinicController {
	
	@Autowired
	private ClinicService clinicService;

	@Autowired
	public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
	}
	
    // POST request to create a new clinic
	
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClinicData insertClinic(@RequestBody ClinicData clinicData) {
        log.info("Received a request to create a clinic");
        return clinicService.saveClinic(clinicData);
    }
    
    // PUT request to update an existing clinic
    
    @PutMapping("/{clinicId}")
    public ClinicData updateClinic(@PathVariable Long clinicId, 
    		@RequestBody ClinicData clinicData) {
        log.info("Updating clinic with ID: {}", clinicId);
        
        clinicData.setClinicId(clinicId); 
        
        return clinicService.saveClinic(clinicData);
    }
    
    // GET request to retrieve all clinics
    
    @GetMapping
    public List<ClinicData> getAllClinics() {
        log.info("Retrieving all clinics");
        return clinicService.retrieveAllClinics();
    }
    
    // GET request to retrieve a clinic by ID
    
    @GetMapping("/{clinicId}")
    public ClinicData getClinicById(@PathVariable Long clinicId) {
        log.info("Retrieving clinic with ID: {}", clinicId);
        return clinicService.getClinicById(clinicId);
    }
    
    
    // DELETE request to delete a clinic
    
    @DeleteMapping("/{clinicId}")
    public Map<String, String> deleteClinic(@PathVariable Long clinicId) {
        log.info("Deleting clinic with ID: {}", clinicId);
        
        clinicService.deleteClinicById(clinicId);
        
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Clinic deleted successfully");
        return response;
    }
    
    // POST request to add an employee to a clinic
    
    @PostMapping("/{clinicId}/employee")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClinicEmployee addEmployeeToClinic(@PathVariable Long clinicId, 
    		@RequestBody ClinicEmployee employee) {
        log.info("Adding employee to clinic with ID: {}", clinicId);
        
        return clinicService.saveEmployee(clinicId, employee);
    }
   
    // PUT request to update an employee in a clinic
    
    @PutMapping("/{clinicId}/employee/{employeeId}")
    public ClinicData.ClinicEmployee updateEmployeeInClinic(@PathVariable Long clinicId,
            @PathVariable Long employeeId,
            @RequestBody ClinicData.ClinicEmployee employee) {
        log.info("Updating employee with ID {} in clinic with ID: {}", employeeId, clinicId);
        
        ClinicData.ClinicEmployee updatedEmployee = clinicService.updateEmployee(clinicId, employeeId, employee);
        return updatedEmployee;
    }
    

   
    // GET request to retrieve all employees of a clinic
    
    @GetMapping("/{clinicId}/employee")
    public List<ClinicData.ClinicEmployee> getAllEmployees(@PathVariable Long clinicId) {
        log.info("Retrieving all employees for clinic with ID: {}", clinicId);
        return clinicService.getAllEmployees(clinicId);
    }
    
    // DELETE request to delete an employee from a clinic
    
    @DeleteMapping("/{clinicId}/employee/{employeeId}")
    public Map<String, String> deleteEmployee(
            @PathVariable Long clinicId,
            @PathVariable Long employeeId) {
        log.info("Deleting employee with ID {} from clinic with ID: {}", employeeId, clinicId);
        clinicService.deleteEmployee(clinicId, employeeId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee deleted successfully");
        return response;
    }

    // POST request to add a patient to a clinic
    
    @PostMapping("/{clinicId}/patient")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClinicData.ClinicPatient addPatientToClinic(@PathVariable Long clinicId, 
            @RequestBody ClinicData.ClinicPatient patient) {
        log.info("Adding patient to clinic with ID: {}", clinicId);

        return clinicService.savePatient(clinicId, patient);
    }
    

    // GET request to retrieve all patients of a clinic
    
    @GetMapping("/{clinicId}/patient")
    public List<ClinicData.ClinicPatient> getAllPatients(@PathVariable Long clinicId) {
        log.info("Retrieving all patients for clinic with ID: {}", clinicId);
        return clinicService.getAllPatients(clinicId);
    }
    
    
    // PUT request to update a patient in a clinic
   
    @PutMapping("/{clinicId}/patient/{patientId}")
    public ClinicData.ClinicPatient updatePatientInClinic(@PathVariable Long clinicId,
            @PathVariable Long patientId,
            @RequestBody ClinicData.ClinicPatient patient) {
        log.info("Updating patient with ID {} in clinic with ID: {}", patientId, clinicId);
        
        ClinicData.ClinicPatient updatedPatient = clinicService.updatePatient(clinicId, patientId, patient);
        return updatedPatient;
    }
    
    // DELETE request to delete a patient from a clinic
    
    @DeleteMapping("/{clinicId}/patient/{patientId}")
    public Map<String, String> deletePatient(
            @PathVariable Long clinicId,
            @PathVariable Long patientId) {
        log.info("Deleting patient with ID {} from clinic with ID: {}", patientId, clinicId);
        clinicService.deletePatient(clinicId, patientId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Patient deleted successfully");
        return response;
    }
    
    // GET request to retrieve all patients for a specific clinic
    
    @GetMapping("/{clinicId}/patients")
    public List<ClinicData.ClinicPatient> getAllPatientsForClinic(@PathVariable Long clinicId) {
        log.info("Retrieving all patients for clinic with ID: {}", clinicId);
        return clinicService.getAllPatientsForClinic(clinicId);
    }
}
