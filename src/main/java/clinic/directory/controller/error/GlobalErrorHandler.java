package clinic.directory.controller.error;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * GlobalErrorHandler handles exceptions globally for the controller.
 * It provides error handling logic for specific exceptions.
 */

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
	/**
	 * Handles NoSuchElementException specifically. It returns a custom message and sets
	 * the HTTP response status to NOT_FOUND.
	 * 
	 * @param ex The NoSuchElementException instance.
	 * @return A map containing an error message.
	 */
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
		log.error("Resource not found: {}", ex.toString());
		return Map.of("message", ex.toString());
	}
	

	}


