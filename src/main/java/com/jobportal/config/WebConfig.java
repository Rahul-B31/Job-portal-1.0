package com.jobportal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${company.logo-dir}")
	String company_logo_dir;
	
	@Value("${candidate.resume-dir}")
	String candiate_resumes;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapping the URL pattern to the external directory
    	 registry.addResourceHandler("uploads/UploadCompanyLogo/**")
         .addResourceLocations("file:" + company_logo_dir + "/");
    	 
    	 
    	 registry.addResourceHandler("uploads/UploadResume/**")
    	 .addResourceLocations("file:" + candiate_resumes + "/");
        
    }
}
