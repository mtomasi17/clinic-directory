package clinic.directory.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Clinic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clinicId;
	
	private String clinicName;
	private String clinicAddress;
	private String clinicCity;
	private String clinicState;
	private String clinicZip;
	private String clinicPhone;
	
	// Many-to-many relationship between Clinic and Patient entities.
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "clinic_patient", joinColumns = @JoinColumn(name = "clinic_id"),
		inverseJoinColumns = @JoinColumn(name = "patient_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Patient> patients = new HashSet<>();
	
	// One-to-many relationship between Clinic and Employee entities.
	
	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Employee> employees = new HashSet<>();

}
