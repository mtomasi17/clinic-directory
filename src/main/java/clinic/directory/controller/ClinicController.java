package clinic.directory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import clinic.directory.controller.model.ClinicData;
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
	
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClinicData insertClinic(@RequestBody ClinicData clinicData) {
        log.info("Received a request to create a clinic");
        return clinicService.saveClinic(clinicData);
    }
    
    @PutMapping("/{clinicId}")
    public ClinicData updateClinic(@PathVariable Long clinicId, 
    		@RequestBody ClinicData clinicData) {
        log.info("Updating clinic with ID: {}", clinicId);
        
        clinicData.setClinicId(clinicId); 
        
        return clinicService.saveClinic(clinicData);
    }
}
