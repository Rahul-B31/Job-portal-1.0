package com.jobportal.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.JobDetails;
import com.jobportal.exception.JobNotFound;
import com.jobportal.exception.UserException;
import com.jobportal.model.Application;
import com.jobportal.model.Company;
import com.jobportal.model.Job;
import com.jobportal.model.SavedJob;
import com.jobportal.model.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.CompanyRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.SavedJobRepository;
import com.jobportal.repository.SearchRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.response.ApiResponse;


@Service
public class JobServiceImpl implements IJobService {

	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	SavedJobRepository savedJobRepository;
	
	@Autowired
	SearchRepository searchRepository;
	
	@Value("${candidate.resume-dir}")
	String uploadResumeDir;
	
	
	
	
	
	@Override
	public Job createJob(JobDetails jobDetails) throws Exception {
		
		
		Company company = companyRepository.findById(jobDetails.getCompany_id()).get();
		User recruiter = userRepository.findById(jobDetails.getRecruiter_id()).get();
		
	
		Job job = new Job();
		
		job.setCompany(company);
		job.setRecruiter(recruiter);
		job.setCreatedAt(LocalDateTime.now());
		job.setJobDescription(jobDetails.getDescription());
		job.setLocation(jobDetails.getLocation());
		job.setOpen(true);
		job.setRequirements(jobDetails.getRequirements());
		job.setTitle(jobDetails.getTitle());
		
		Job save = jobRepository.save(job);
		if(save==null)
			throw new Exception("Erorr while creating the job");
		return save;
	}



	@Override
	public List<Job> fetchAllJobs() {
	     List<Job> jobs = jobRepository.findAll();
		return jobs;
	}
	
	@Override
	public List<Job> filteredJobs(String location,String company_Id) {
          Company company = null;
          List<Job> jobs = null;
          
		  if(company_Id !=null)
			 company = companyRepository.findById(company_Id).get();
		
		
		if(location!=null && company!=null)
		 jobs = jobRepository.findByLocationAndCompany(location, company);
		else if(location!=null)
			jobs = jobRepository.findByLocation(location);
		else if(company!=null)
			jobs = jobRepository.findByCompany(company);
		return jobs;
	}



	@Override
	public Job fetchJobById(String id) {
		
		Optional<Job> job = jobRepository.findById(id);
		return job.orElseThrow(()-> new JobNotFound("Job Not Found for the id "+id));
	}



	@Override
	public Application applyToJob(
			MultipartFile file,String candidate_id,
			String jobId,
			String name,
			String skills,
			String status,
			String education,
			Integer experience
			) throws IOException {
		
		
		Path uploadPath = Paths.get(uploadResumeDir);
		
		if(!Files.exists(uploadPath))
		{
			Files.createDirectories(uploadPath);
		}
		
		String filename = file.getOriginalFilename();
		String uid = UUID.randomUUID().toString();
		String uniquefilename = uid + filename.substring(filename.lastIndexOf("."));
		
		Path filepath = uploadPath.resolve(uniquefilename);
		Files.copy(file.getInputStream(),filepath,StandardCopyOption.REPLACE_EXISTING);
		
		String resume = filepath.toString();
		User user = userRepository.findById(candidate_id).orElseThrow(()->new UserException("User Not Found"));
		Job job =   jobRepository.findById(jobId).orElseThrow(()->new JobNotFound("job not found"));
		
		Application application = new Application();
		
		application.setEducation(education);
		application.setExperience(experience);
		application.setJob(job);
		application.setName(name);
		application.setResume(resume);
		application.setSkills(skills);
		application.setStatus(status);
		application.setUser(user);
		application.setCreatedAt(LocalDateTime.now());
		
		// increment the job application count
		
		job.setApplicationCount(job.getApplicationCount()+1);	
		jobRepository.save(job);
		
		Application app = applicationRepository.save(application);
		
		return app;
	}



	@Override
	public Boolean isApplied(String userId,String jobId) {
		
		Job job = jobRepository.findById(jobId).get();
		User user = userRepository.findById(userId).get();

		boolean existsByJobAndUser = applicationRepository.existsByJobAndUser(job,user);
		return existsByJobAndUser;
	}



	@Override
	public List<Application> getApplicationsByUser(String userId) {
		   User user = userRepository.findById(userId).get();
		   return applicationRepository.findByUser(user);
	
	}
	
	
	public Path getResumeById(String appId) {
		
		Application application = applicationRepository.findById(appId).get();
		String filepath = application.getResume();
		Path path = Paths.get(filepath);
		
		return path;
	    
	}
	
	@Override
	public ApiResponse saveJobByUserIdAndJobId(String jobId,String userId) throws Exception
	{
		
		  Job job = jobRepository.findById(jobId).orElseThrow(()->new JobNotFound("Job not found for the id "+jobId));
		  User user = userRepository.findById(userId).orElseThrow(()->new UserException("User Not Found for the ID "+userId));
		  
		  SavedJob savedJob = new SavedJob();
		  savedJob.setJob(job);
		  savedJob.setUser(user);	   
		  SavedJob save = savedJobRepository.save(savedJob);
		  if(save!=null) {
			  return new ApiResponse("The job is saved successfully with the id "+save.getId(),true);
		  }else {
			  throw new Exception("Cannot able to saved the job");
		  }
		
	}
	
	public List<SavedJob> getSavedJobByUserId(String userId)
	{
		  
		  User user = userRepository.findById(userId).orElseThrow(()->new UserException("User Not Found for the id" +userId));
	
		  return savedJobRepository.findByUser(user);
		
	}
	
	public List<Job> getCreatedJobByRecruiterId(String recruiterId){
		
		  User recuiter = userRepository.findById(recruiterId).orElseThrow(()->new UserException("Recruiter Not Found For the Id "+recruiterId));
		  return jobRepository.findByRecruiter(recuiter);
	}
	
	public ApiResponse deleteSavedJobByUserIdAndJobId(String userId,String jobId) {
		
		 User user = userRepository.findById(userId).orElseThrow(()->new UserException("User Not Found For the Id "+userId));
		 Job job = jobRepository.findById(jobId).orElseThrow(()-> new JobNotFound("Job Not Found For the ID "+jobId));
		
		savedJobRepository.deleteByUserAndJob(user,job);
		return new ApiResponse("The Job Unsaved",true);
	}
	
	public ApiResponse deleteJobById(String jobId) {	
		Job job = jobRepository.findById(jobId).orElseThrow(()->new JobNotFound("Job Not Found for the id "+jobId));
		jobRepository.delete(job);
		return new ApiResponse("The Job is deleted successfully",true);
	
	}
	
	
	@Override
	public List<Application> getAppliedAppliactionByJobId(String jobId) {
		Job job = jobRepository.findById(jobId).orElseThrow(()->new JobNotFound("Job Not Found"));
		return applicationRepository.findByJob(job);
	
	}
	
	public ApiResponse updateStatusByApplicationId(String appId,String status) throws Exception {	
		Application application = applicationRepository.findById(appId).orElseThrow(()->new Exception("Appliaction not found"));
		application.setStatus(status);
		Application save = applicationRepository.save(application);
		if(save!=null)
		   return new ApiResponse("The Job status is updated successfully",true);
		else
			throw new Exception("Cannot able to change the status");
		
	}
	public ApiResponse updateStatusJob(String jobId,Boolean status) {	
		Job job = jobRepository.findById(jobId).orElseThrow(()->new JobNotFound("Appliaction not found"));
		job.setOpen(status);
		Job save = jobRepository.save(job);
		if(save!=null)
			return new ApiResponse("The Job status is changes successfully",true);
		else
			throw new JobNotFound("cannot able to change the status of the job");
	
	}
	
	  @Override   
	  public List<Job> searchJobs(String keyword){
		  
	        return searchRepository.searchJobsByKeywords(keyword);
	  }
	
	
	
	

}
