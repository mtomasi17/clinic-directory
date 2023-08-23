package clinic.directory.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class Patient {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patientId; 
	private String patientFirstName;
	private String patientLastName;
	private String patientEmail;
	
	@ManyToMany(mappedBy = "patients", cascade = CascadeType.PERSIST)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Clinic> clinic = new HashSet<>();

}

