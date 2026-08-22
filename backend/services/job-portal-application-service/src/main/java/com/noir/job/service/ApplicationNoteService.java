package com.noir.job.service;

import com.noir.job.common.dto.response.ApplicationNoteResponse;
import com.noir.job.common.exception.ApplicationException;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddApplicationNoteRequest;

import java.util.List;

public interface ApplicationNoteService {

    ApplicationNoteResponse addNote(
            Long applicationId, Long employerId,
                                     AddApplicationNoteRequest req)
            throws ResourceNotFoundException, ApplicationException;

    List<ApplicationNoteResponse> getNotesByApplication(
            Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException;

    void deleteNote(Long noteId, Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException;
}
