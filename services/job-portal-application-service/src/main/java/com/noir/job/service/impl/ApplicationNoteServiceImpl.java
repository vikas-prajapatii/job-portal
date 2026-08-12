package com.noir.job.service.impl;

import com.noir.job.dto.ApplicationNoteResponse;
import com.noir.job.mapper.ApplicationMapper;
import com.noir.job.model.Application;
import com.noir.job.model.ApplicationNote;
import com.noir.job.payload.AddApplicationNoteRequest;
import com.noir.job.repository.ApplicationNoteRepository;
import com.noir.job.service.ApplicationNoteService;
import com.noir.job.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationNoteServiceImpl implements ApplicationNoteService {
    private final ApplicationNoteRepository noteRepository;
    private final ApplicationService applicationService;
    @Override
    public ApplicationNoteResponse addNote(Long applicationId, Long employerId, AddApplicationNoteRequest req) throws Exception {
        Application application = applicationService
                .getApplicationEntity(applicationId);

        assertEmployer(application, employerId);

        ApplicationNote note = ApplicationNote.builder()
                .application(application)
                .addedByUserId(employerId)
                .content(req.getContent())
                .build();

        ApplicationNoteResponse response = ApplicationMapper.toNoteResponse(noteRepository.save(note));
        return response;
    }

    @Override
    public List<ApplicationNoteResponse> getNotesByApplication(Long applicationId, Long employerId) throws Exception {
        Application application = applicationService.getApplicationEntity(applicationId);
        assertEmployer(application, employerId);

        return noteRepository.findByApplicationIdOrderByCreatedAtDesc(applicationId)
                .stream()
                .map(ApplicationMapper::toNoteResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNote(Long noteId, Long applicationId, Long employerId) throws Exception {
        Application application = applicationService.getApplicationEntity(applicationId);
        assertEmployer(application, employerId);

        ApplicationNote note = noteRepository.findById(noteId)
                .orElseThrow(() -> new Exception(
                        "Note not found with id: " + noteId));

        if (!note.getApplication().getId().equals(applicationId)) {
            throw new Exception(
                    "Note does not belong to application with id: " + applicationId);
        }

        noteRepository.delete(note);
    }
    private void assertEmployer(Application application, Long employerId) throws Exception {
        if (!application.getEmployerId().equals(employerId)) {
            throw new Exception("You are not the employer for this application");
        }
    }
}
