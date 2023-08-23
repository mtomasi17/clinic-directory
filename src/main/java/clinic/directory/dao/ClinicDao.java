package clinic.directory.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import clinic.directory.entity.Clinic;

public interface ClinicDao extends JpaRepository<Clinic, Long> {

}
