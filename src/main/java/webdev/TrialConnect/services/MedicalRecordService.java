package webdev.TrialConnect.services;

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
import webdev.TrialConnect.models.MedicalRecord;
import webdev.TrialConnect.models.Patient;
import webdev.TrialConnect.repositories.DoctorRepository;
import webdev.TrialConnect.repositories.MedicalRecordRepository;
import webdev.TrialConnect.repositories.PatientRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MedicalRecordService {

	@Autowired
	MedicalRecordRepository medicalRepository;

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	DoctorRepository doctorRepository;

	@PostMapping("/api/medicalrecord/{pid}/doc/{docId}")
	public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord, @PathVariable("pid") int pid,
			@PathVariable("docId") int docId) {
		Optional<Patient> data = patientRepository.findById(pid);
		Optional<Doctor> docData = doctorRepository.findById(docId);
		if (data.isPresent()) {
			Patient pat = data.get();
			Doctor doc = docData.get();
			medicalRecord.setPatient(pat);
			medicalRecord.setDoctor(doc);
			return medicalRepository.save(medicalRecord);
		}
		return new MedicalRecord();

	}

	@GetMapping("/api/medicalrecords")
	public List<MedicalRecord> findAllMedicalRecords() {
		return (List<MedicalRecord>) medicalRepository.findAll();
	}

	@GetMapping("/api/medicalrecord/{id}")
	public MedicalRecord findMedicalRecordById(@PathVariable("id") int mid) {
		MedicalRecord medicalRecord = new MedicalRecord();
		Optional<MedicalRecord> data = medicalRepository.findById(mid);
		if (data.isPresent()) {
			return data.get();
		}
		return medicalRecord;
	}

	@DeleteMapping("/api/medicalrecord/{id}")
	public void deleteMedicalRecord(@PathVariable("id") int mid) {
		medicalRepository.deleteById(mid);
	}

	@PutMapping("/api/medicalrecord/{id}")
	public MedicalRecord updateMedicalRecord(@PathVariable("id") int did, @RequestBody MedicalRecord newMedicalRecord) {
		MedicalRecord mr = new MedicalRecord();
		Optional<MedicalRecord> data = medicalRepository.findById(did);
		if (data.isPresent()) {
			MedicalRecord medicalRecord = data.get();

			if (newMedicalRecord.getMedicine() != null && !newMedicalRecord.getMedicine().equals("")) {
				medicalRecord.setMedicine(newMedicalRecord.getMedicine());
			}

			if (newMedicalRecord.getPatient() != null) {
				medicalRecord.setPatient(newMedicalRecord.getPatient());
			}
			if (newMedicalRecord.getDoctor() != null) {
				medicalRecord.setDoctor(newMedicalRecord.getDoctor());
			}
			if (newMedicalRecord.getBodyTemperature() != null) {
				medicalRecord.setBodyTemperature(newMedicalRecord.getBodyTemperature());
			}
			if (newMedicalRecord.getBloodPressure() != null) {
				medicalRecord.setBloodPressure(newMedicalRecord.getBloodPressure());
			}
			if (newMedicalRecord.getMedicalCondition() != null) {
				medicalRecord.setMedicalCondition(newMedicalRecord.getMedicalCondition());
			}
			if (newMedicalRecord.getAllergyName() != null) {
				medicalRecord.setAllergyName(newMedicalRecord.getAllergyName());
			}
			if (newMedicalRecord.getBMI() != null) {
				medicalRecord.setBMI(newMedicalRecord.getBMI());
			}
			if (newMedicalRecord.getAllergyCause() != null) {
				medicalRecord.setAllergyCause(newMedicalRecord.getAllergyCause());
			}
			if (newMedicalRecord.getAllergyTreatment() != null) {
				medicalRecord.setAllergyTreatment(newMedicalRecord.getAllergyTreatment());
			}
			
			

			return newMedicalRecord;
		}
		return mr;
	}

}
