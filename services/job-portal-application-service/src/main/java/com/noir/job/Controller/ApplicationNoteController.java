package com.noir.job.Controller;

import com.noir.job.dto.ApplicationNoteResponse;
import com.noir.job.payload.AddApplicationNoteRequest;
import com.noir.job.service.ApplicationNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications/{applicationId}/notes")
public class ApplicationNoteController {

    private final ApplicationNoteService applicationNoteService;

    // Add a note to an application (Employer)
    @PostMapping
    public ResponseEntity<ApplicationNoteResponse> addNote(
            @PathVariable Long applicationId,
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid AddApplicationNoteRequest req) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationNoteService.addNote(applicationId, employerId, req));
    }

    // Get all notes for an application (Employer)
    @GetMapping
    public ResponseEntity<List<ApplicationNoteResponse>> getNotesByApplication(
            @PathVariable Long applicationId,
            @RequestHeader("X-User-Id") Long employerId) throws Exception {
        return ResponseEntity.ok(applicationNoteService.getNotesByApplication(applicationId, employerId));
    }

    // Delete a note from an application (Employer)
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable Long applicationId,
            @PathVariable Long noteId,
            @RequestHeader("X-User-Id") Long employerId) throws Exception {
        applicationNoteService.deleteNote(noteId, applicationId, employerId);
        return ResponseEntity.noContent().build();
    }
}
