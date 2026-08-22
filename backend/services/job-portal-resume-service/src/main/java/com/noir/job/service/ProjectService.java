package com.noir.job.service;

import com.noir.job.common.dto.response.ProjectResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddProjectRequest;

import java.util.List;

public interface ProjectService {

    ProjectResponse addProject(Long resumeId, Long candidateId, AddProjectRequest req)
            throws ResourceNotFoundException;

    List<ProjectResponse> getProjects(Long resumeId) throws ResourceNotFoundException;

    ProjectResponse updateProject(Long projectId, Long resumeId, Long candidateId,
            AddProjectRequest req) throws ResourceNotFoundException;

    void deleteProject(Long projectId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException;
}
