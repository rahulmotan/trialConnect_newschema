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
import webdev.TrialConnect.models.Trial;
import webdev.TrialConnect.repositories.TrialRepository;

@RestController
@CrossOrigin(origins="*",maxAge=3600)
public class TrialService {
	
	@Autowired
	TrialRepository trialRepository;
     

	@PostMapping("/api/trial")
	public Trial createTrial(@RequestBody Trial trial) {
		return trialRepository.save(trial);
	}
	
	@GetMapping("/api/trials")
	public List<Trial> findAllTrials(){
		return (List<Trial>) trialRepository.findAll();
	}
	
	@GetMapping("/api/trial/{id}")
	public Trial findTrialById(@PathVariable("id") int tid) {
		Trial tr = new Trial();
		Optional<Trial> data = trialRepository.findById(tid);
		if(data.isPresent()) {
			return data.get();
		}
		return tr;	
	}
	
	@DeleteMapping("/api/trial/{id}")
	public void deleteTrial(@PathVariable("id") int tid)
	{
		trialRepository.deleteById(tid);
	}
	
	@PutMapping("/api/trial/{id}")
	public Trial updateTrial(@PathVariable("id") int tid, @RequestBody Trial newTrial) {
		Trial tr = new Trial();
		Optional<Trial> data = trialRepository.findById(tid);
		if(data.isPresent()) {
			Trial trial = data.get();
		
			if(newTrial.getBriefDescription()!= null && !newTrial.getBriefDescription().equals("")) {
				trial.setBriefDescription(newTrial.getBriefDescription());
			}
			
			if(newTrial.getDetailedDescription()!= null && !newTrial.getDetailedDescription().equals("")) {
				trial.setDetailedDescription(newTrial.getDetailedDescription());
			}
		
			if(newTrial.getTitle()!= null && !newTrial.getTitle().equals("")) {
				trial.setTitle(newTrial.getTitle());
			}
			
			return newTrial;
		}
		return tr;
	}
	

}
