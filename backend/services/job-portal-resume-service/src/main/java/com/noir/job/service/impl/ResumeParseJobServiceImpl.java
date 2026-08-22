package com.noir.job.service.impl;

import com.noir.job.common.domain.ParseStatus;
import com.noir.job.common.dto.response.ResumeParseJobResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.ParseResumeRequest;
import com.noir.job.entity.ResumeParseJob;
import com.noir.job.mapper.ResumeMapper;
import com.noir.job.repository.ResumeParseJobRepository;
import com.noir.job.service.ResumeParseJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeParseJobServiceImpl implements ResumeParseJobService {

    private final ResumeParseJobRepository parseJobRepository;

    @Override
    @Transactional
    public ResumeParseJobResponse submitParseJob(Long candidateId, ParseResumeRequest req) {
        ResumeParseJob job = ResumeParseJob.builder()
                .candidateId(candidateId)
                .originalFileUrl(req.getFileUrl())
                .originalFileName(req.getFileName())
                .fileType(req.getFileType())
                .status(ParseStatus.PENDING)
                .build();

        return ResumeMapper.toParseJobResponse(parseJobRepository.save(job));
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeParseJobResponse getParseJob(Long jobId, Long candidateId)
            throws ResourceNotFoundException {
        ResumeParseJob job = parseJobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Parse job not found with id: " + jobId));
        if (!job.getCandidateId().equals(candidateId)) {
            throw new ResourceNotFoundException("Parse job not found with id: " + jobId);
        }
        return ResumeMapper.toParseJobResponse(job);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeParseJobResponse> getMyParseJobs(Long candidateId) {
        return parseJobRepository.findByCandidateIdOrderByCreatedAtDesc(candidateId)
                .stream().map(ResumeMapper::toParseJobResponse).toList();
    }
}
