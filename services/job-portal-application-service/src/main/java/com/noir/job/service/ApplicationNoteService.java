package com.noir.job.service;

import com.noir.job.dto.ApplicationNoteResponse;
import com.noir.job.payload.AddApplicationNoteRequest;

import java.util.List;

public interface ApplicationNoteService {

    ApplicationNoteResponse addNote(
            Long applicationId, Long employerId,
            AddApplicationNoteRequest req) throws Exception;

    List<ApplicationNoteResponse> getNotesByApplication(
            Long applicationId, Long employerId) throws Exception;

    void deleteNote(Long noteId, Long applicationId, Long employerId) throws Exception;
}
