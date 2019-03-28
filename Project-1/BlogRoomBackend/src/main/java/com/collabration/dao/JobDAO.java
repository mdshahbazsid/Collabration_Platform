package com.collabration.dao;

import java.util.List;

import com.collabration.model.Job;

public interface JobDAO {

	public void addJob(Job job);
	public List<Job> getAllJobs();
	
}
