package clinic.directory.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import clinic.directory.entity.Clinic;

/**
 * This interface defines a data access object (DAO) for the Clinic entity.
 * It extends the JpaRepository interface to provide basic CRUD operations for Clinic entities.
 */

public interface ClinicDao extends JpaRepository<Clinic, Long> {

}
