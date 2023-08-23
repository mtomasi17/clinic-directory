package clinic.directory.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clinic.directory.controller.model.ClinicData;
import clinic.directory.dao.ClinicDao;
import clinic.directory.entity.Clinic;

@Service
public class ClinicService {
	
	@Autowired
	private ClinicDao clinicDao;

    @Autowired
    public ClinicService(ClinicDao clinicDao) {
        this.clinicDao = clinicDao;
    }
    
    @Transactional
    public ClinicData saveClinic(ClinicData clinicData) {
        Long clinicId = clinicData.getClinicId();
        Clinic clinic = findOrCreateClinic(clinicId);

        copyClinicFields(clinic, clinicData);

        return new ClinicData(clinicDao.save(clinic));
    }

    private Clinic findOrCreateClinic(Long clinicId) {
        if (Objects.isNull(clinicId)) {
            return new Clinic();
        } else {
            return findClinicById(clinicId);
        }
    }

    private Clinic findClinicById(Long clinicId) {
        return clinicDao.findById(clinicId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Clinic with ID=" + clinicId + " was not found."));
    }

    private void copyClinicFields(Clinic clinic, ClinicData clinicData) {
        clinic.setClinicName(clinicData.getClinicName());
        clinic.setClinicAddress(clinicData.getClinicAddress());
        clinic.setClinicCity(clinicData.getClinicCity());
        clinic.setClinicState(clinicData.getClinicState());
        clinic.setClinicZip(clinicData.getClinicZip());
        clinic.setClinicPhone(clinicData.getClinicPhone());
    }
}


