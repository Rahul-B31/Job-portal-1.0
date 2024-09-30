package com.jobportal.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.JobDetails;
import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.SavedJob;
import com.jobportal.response.ApiResponse;



public interface IJobService {
	
	public Job createJob(JobDetails jobDetails) throws Exception;
	public List<Job> fetchAllJobs();
	public Job fetchJobById(String id);
	
	public List<Application> getApplicationsByUser(String userId);
	
	public Boolean isApplied(String userId,String jobId); 
	
	public Path getResumeById(String appId);
	
	public ApiResponse saveJobByUserIdAndJobId(String jobId,String userId) throws Exception;
	
	public List<SavedJob> getSavedJobByUserId(String userId);
	
	public List<Job> getCreatedJobByRecruiterId(String recruiterId);
	
	public ApiResponse deleteSavedJobByUserIdAndJobId(String userId,String jobId);
	
	public ApiResponse deleteJobById(String jobId);
	
	public List<Application> getAppliedAppliactionByJobId(String jobId);
	
	public ApiResponse updateStatusByApplicationId(String appId,String status) throws Exception;
	
	public ApiResponse updateStatusJob(String jobId,Boolean status);
	
	public List<Job> filteredJobs(String location,String company_Id);
	
	 public List<Job> searchJobs(String keyword); 
	
	public Application applyToJob(
			MultipartFile file,
			String candidate_id,
			String jobId,
			String name,
			String skills,
			String status,
			String education,
			Integer experience
			)throws IOException;

}
