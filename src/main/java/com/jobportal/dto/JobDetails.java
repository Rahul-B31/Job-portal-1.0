package com.jobportal.dto;




public class JobDetails {
	
	private String title;
	private String description;
	private String location;
	private String requirements;
	
	private String company_id;
	
	private String recruiter_id;
	
	public JobDetails() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getRecruiter_id() {
		return recruiter_id;
	}

	public void setRecruiter_id(String recruiter_id) {
		this.recruiter_id = recruiter_id;
	}

	public JobDetails(String title, String description, String location, String requirements, String company_id,
			String recruiter_id) {
		super();
		this.title = title;
		this.description = description;
		this.location = location;
		this.requirements = requirements;
		this.company_id = company_id;
		this.recruiter_id = recruiter_id;
	}
	
	

}
