package clinic.directory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import clinic.directory.entity.Patient;

//This interface is a repository for the Patient entity.
//It extends JpaRepository, providing common CRUD operations.

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
