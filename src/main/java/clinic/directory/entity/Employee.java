package clinic.directory.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId; 
	
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;
	
	 // Many-to-one relationship between Employee and Clinic entities.
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "clinic_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Clinic clinic;
}
