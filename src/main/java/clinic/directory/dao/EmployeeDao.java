package clinic.directory.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import clinic.directory.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
