package webdev.TrialConnect.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.TrialConnect.models.Doctor;
import webdev.TrialConnect.models.Patient;
import webdev.TrialConnect.models.Trial;
import webdev.TrialConnect.repositories.DoctorRepository;
import webdev.TrialConnect.repositories.PatientRepository;
import webdev.TrialConnect.repositories.TrialRepository;
import webdev.TrialConnect.repositories.VitalsRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PatientService {

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	DoctorRepository docRepository;

	@Autowired
	TrialRepository trialRepository;

	@PostMapping("/api/patient")
	public Patient createPatient(@RequestBody Patient patient) {
		return patientRepository.save(patient);
	}

	@GetMapping("/api/patients")
	public List<Patient> findAllPatients() {
		return (List<Patient>) patientRepository.findAll();
	}

	@GetMapping("/api/findPatient/{username}")
	public Patient findPatientByUsername(@PathVariable("username") String username) {
		Patient pat = new Patient();
		Optional<Patient> data = patientRepository.findUserByUsername(username);
		if (data.isPresent()) {
			return data.get();
		}
		return pat;
	}

	@PostMapping("/api/findPatientByCredentials/patient")
	public Patient findPatientByCredentials(@RequestBody Patient patient) {
		Patient pat = new Patient();
		Optional<Patient> data = patientRepository.findUserByCredentials(patient.getUsername(), patient.getPassword());
		if (data.isPresent()) {
			return data.get();
		}
		return pat;
	}

	@GetMapping("/api/patient/{id}")
	public Patient findPatientById(@PathVariable("id") int pid) {
		Patient pat = new Patient();
		Optional<Patient> data = patientRepository.findById(pid);
		if (data.isPresent()) {
			return data.get();
		}
		return pat;
	}

	@DeleteMapping("/api/patient/{id}")
	public void deletePatient(@PathVariable("id") int pid) {
		patientRepository.deleteById(pid);
	}

	@PutMapping("/api/patient/{id}")
	public Patient updatePatient(@PathVariable("id") int pid, @RequestBody Patient newPatient) {
		Patient pat = new Patient();
		Optional<Patient> data = patientRepository.findById(pid);
		if (data.isPresent()) {
			Patient patient = data.get();

			if (newPatient.getEmail() != null && !newPatient.getEmail().equals("")) {
				patient.setEmail(newPatient.getEmail());
			}
			if (newPatient.getFirstName() != null && !newPatient.getFirstName().equals("")) {
				patient.setFirstName(newPatient.getFirstName());
			}
			if (newPatient.getLastName() != null && !newPatient.getLastName().equals("")) {
				patient.setLastName(newPatient.getLastName());
			}
			if (newPatient.getPassword() != null && !newPatient.getPassword().equals("")) {
				patient.setPassword(newPatient.getPassword());
			}

			if (newPatient.getPhone() != null && !newPatient.getPhone().equals("")) {
				patient.setPhone(newPatient.getPhone());
			}

			patientRepository.save(patient);
			return patient;
		}
		return pat;
	}

	@PostMapping("/api/patient/{patientId}/mr/{doctorId}")
	public Patient createPatientMedicalRecord(@RequestBody Patient newPatient, @PathVariable("patientId") int patientId,
			@PathVariable("doctorId") int doctorId) {

		Optional<Patient> dataPatient = patientRepository.findById(patientId);
		Optional<Doctor> dataDoctor = docRepository.findById(doctorId);
		Patient patient = new Patient();

		if (dataPatient.isPresent() && dataDoctor.isPresent()) {
			patient = dataPatient.get();
			if (newPatient.getEmail() != null && !newPatient.getEmail().equals("")) {
				patient.setEmail(newPatient.getEmail());
			}
			if (newPatient.getFirstName() != null && !newPatient.getFirstName().equals("")) {
				patient.setFirstName(newPatient.getFirstName());
			}
			if (newPatient.getLastName() != null && !newPatient.getLastName().equals("")) {
				patient.setLastName(newPatient.getLastName());
			}
			if (newPatient.getPassword() != null && !newPatient.getPassword().equals("")) {
				patient.setPassword(newPatient.getPassword());
			}

			if (newPatient.getPhone() != null && !newPatient.getPhone().equals("")) {
				patient.setPhone(newPatient.getPhone());
			}

			if (newPatient.getGender() != null) {
				patient.setGender(newPatient.getGender());
			}
			if (newPatient.getMedicalRecords() != null) {
				patient.setMedicalRecords(newPatient.getMedicalRecords());
			}

			return patientRepository.save(patient);
		}
		return patient;
	}

	@GetMapping("/api/patient/{patientId}/recommendedtrials")
	public List<Trial> findRecommenedTrialsForPatient(@PathVariable("patientId") int patientId) {
		Optional<Patient> data = patientRepository.findById(patientId);
		if (data.isPresent()) {
			List<Trial> trials = data.get().getTrial();
			return trials;
		}
		return new ArrayList<>();
	}

	@PostMapping("/api/patient/{patientId}/trial")
	public Trial recommendTrialToPatient(@RequestBody Trial trial, @PathVariable int patientId) {
		Optional<Patient> data = patientRepository.findById(patientId);
		if (data.isPresent()) {
			Patient patient = data.get();
			trial.setPatient(patient);
			return trialRepository.save(trial);
		}
		return new Trial();
	}
}
