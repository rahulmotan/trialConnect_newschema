package webdev.TrialConnect.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.TrialConnect.models.Doctor;
import webdev.TrialConnect.models.Patient;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
	
	@Query("SELECT p FROM Patient p WHERE p.username=:username")
	Optional<Patient> findUserByUsername(@Param("username") String username);
	
	
	@Query("SELECT p FROM Patient p WHERE p.username=:username AND p.password=:password")
	Optional<Patient> findUserByCredentials(
		@Param("username") String username, 
		@Param("password") String password);
	
	@Query("SELECT DISTINCT d FROM Doctor d JOIN FETCH d.prescribedRecords p JOIN FETCH p.patient pat WHERE pat=:pat")
	List<Doctor> findDoctorsForPatient(@Param("pat") Patient pat);


}
