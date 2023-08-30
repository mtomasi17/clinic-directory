/**
 * This is the main application class for the Clinic Directory application.
 * It uses Spring Boot to configure and launch the application.
 */

package clinic.directory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicDirectoryApplication {

	 // The SpringApplication.run() method starts the Clinic Directory application.
	
	public static void main(String[] args) {
		SpringApplication.run(ClinicDirectoryApplication.class, args);

	}

}
