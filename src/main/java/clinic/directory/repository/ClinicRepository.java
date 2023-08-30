package clinic.directory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import clinic.directory.entity.Clinic;

//This interface is a repository for the Clinic entity.
//It extends JpaRepository, providing common CRUD operations.

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

}
