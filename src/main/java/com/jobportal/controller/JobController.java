package com.jobportal.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.JobDetails;
import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.SavedJob;
import com.jobportal.request.SaveJobRequest;
import com.jobportal.response.ApiResponse;
import com.jobportal.services.IJobService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class JobController {
	
	
	@Autowired
	IJobService jobService;
	
	
	
	@PostMapping("/post-job")
	public ResponseEntity<Job> createJob(@RequestBody JobDetails jobDetails) throws Exception{
		
		    Job job = jobService.createJob(jobDetails);
		    return new ResponseEntity<Job>(job,HttpStatus.CREATED);	
	}
	
	
	@GetMapping("/jobs")
	public ResponseEntity<?> getAllJobs(@RequestParam(required = false) String location,
			                          @RequestParam(required = false) String comapany_id,
			                          @RequestParam(required =  false) String searchQuery) throws Exception{
		    List<Job> jobs = null;
		    
		    System.err.println(searchQuery);
		    if(searchQuery!=null)
		    {
		    	jobs = jobService.searchJobs(searchQuery);
		    }else if(location!=null || comapany_id!=null) {
		    	  jobs = jobService.filteredJobs(location, comapany_id);
		    }else {    	
		    	jobs = jobService.fetchAllJobs();
		    }
		
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);	
	}
	
	@GetMapping("/job/{id}")
	public ResponseEntity<?> getJobById(@PathVariable String id){
		Job job = jobService.fetchJobById(id);
		return new ResponseEntity<Job>(job,HttpStatus.OK);	
	}
	
	@PostMapping("/apply")
	public ResponseEntity<?> applyJob(
			@RequestParam MultipartFile file,
			@RequestParam String candidate_id,
			@RequestParam String jobId,
			@RequestParam String name,
			@RequestParam String skills,
			@RequestParam String status,
			@RequestParam String education,
			@RequestParam Integer experience
			
			) throws IOException{
		
		
		Application application = jobService.applyToJob(file, candidate_id, jobId, name, skills, status, education, experience);
		return new ResponseEntity<Application>(application,HttpStatus.OK);
	}
	
	@GetMapping("/isapply")
	public ResponseEntity<?> isApplyed (@RequestParam("userId") String userId,@RequestParam("jobId") String jobId)
	{
		  Boolean applied =  jobService.isApplied(userId, jobId);
		  return new ResponseEntity<Boolean>(applied,HttpStatus.OK);
	}
	
	@GetMapping("/my-applications/{userId}")
	public ResponseEntity<?> getUserApplications(@PathVariable String userId) {
		
		List<Application> applications = jobService.getApplicationsByUser(userId);
		return new ResponseEntity<List<Application>>(applications,HttpStatus.OK);
		
	}
	
	@GetMapping("/download-resume/{applicationId}")
	public ResponseEntity<Resource> getMethodName(@PathVariable String applicationId,HttpServletRequest request) throws IOException{
		
		Path path = jobService.getResumeById(applicationId);
		Resource resource = null;
		
		try {
			 resource = new UrlResource(path.toUri());
			 if (!resource.exists()) {
	                throw new FileNotFoundException("File not found ");
	            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file = new File(path.toUri());
	  String contentType = Files.probeContentType(file.toPath());
//		try {
//			
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//			
//		} catch (IOException e) {
//			contentType = "application/octet-stream";
//		}
		  if (contentType == null) {
	            contentType = "application/octet-stream";
	        }
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename())
				.body(resource);
		
	}
	
	
	@PostMapping("/saved-job")
	public ResponseEntity<ApiResponse> saveJob(@RequestBody SaveJobRequest request) throws Exception {
		
		  ApiResponse response = jobService.saveJobByUserIdAndJobId(request.getJobId(), request.getUserId());		 
		  
		  return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/saved-job/{userId}")
	public ResponseEntity<List<SavedJob>> getSavedJobs(@PathVariable String userId) {
		  
		List<SavedJob> savedJobs = jobService.getSavedJobByUserId(userId);
		return new ResponseEntity<List<SavedJob>>(savedJobs,HttpStatus.OK);
	}
	
	@GetMapping("/created-jobs/{recruiterId}")
	public ResponseEntity<?> getMethodName(@PathVariable String recruiterId) {
		  List<Job> jobs = jobService.getCreatedJobByRecruiterId(recruiterId);
		  return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}
	
	@DeleteMapping("/saved-job/{userId}/{jobId}")
	public ResponseEntity<ApiResponse> deleteSavedJob(@PathVariable String userId,@PathVariable String jobId){
	    System.err.println(userId+jobId);
		ApiResponse response = jobService.deleteSavedJobByUserIdAndJobId(userId, jobId);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete-job/{jobId}")
	public ResponseEntity<ApiResponse> deleteJob(@PathVariable String jobId) {
	
	      ApiResponse response = jobService.deleteJobById(jobId);
	  	  return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	      
	}
	
	@GetMapping("/applied-appliactions/{jobId}")
	public ResponseEntity<List<Application>> getAppliedAppliaction (@PathVariable String jobId) {
		
		   List<Application> applications = jobService.getAppliedAppliactionByJobId(jobId);
		  
		  return new ResponseEntity<List<Application>>(applications,HttpStatus.OK);
	}
	
	@PatchMapping("/appliaction-status/{appliactionId}")
	public ResponseEntity<ApiResponse> updateStatus(@PathVariable String appliactionId,
			@RequestBody Map<String,String> request) throws Exception {
		  
		  String status = request.get("status"); 
	      ApiResponse response = jobService.updateStatusByApplicationId(appliactionId,status);
	  	  return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}      
	
	@PatchMapping("/job-status/{jobId}")
	public ResponseEntity<ApiResponse> updateJobStatus(@PathVariable String jobId,
			@RequestBody  Map<String,Boolean> request) throws Exception {
		
		Boolean status = request.get("status"); 
		ApiResponse response = jobService.updateStatusJob(jobId,status);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}   
	
	
	
	@GetMapping("/search")
	public ResponseEntity<?> searchJobs(@RequestParam("keyword") String keyword) {
		
		List<Job> jobs = jobService.searchJobs(keyword);
		System.err.println(keyword);
		return new ResponseEntity<>(jobs,HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	 
	

}
