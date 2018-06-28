package webdev.TrialConnect.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import webdev.TrialConnect.models.Doctor;
import webdev.TrialConnect.models.Patient;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

	@Query("SELECT d FROM Doctor d WHERE d.username=:username")
	Optional<Doctor> findUserByUsername(@Param("username") String username);

	@Query("SELECT d FROM Doctor d WHERE d.username=:username AND d.password=:password")
	Optional<Doctor> findUserByCredentials(@Param("username") String username, @Param("password") String password);

	@Query("SELECT p FROM Patient p JOIN FETCH p.medicalRecords m JOIN FETCH m.doctor d WHERE d=:doc")
	List<Patient> findPatientByDoctor(@Param("doc") Doctor doc);

}
