package com.noir.job.service.impl;

import com.noir.job.common.domain.AlertFrequency;
import com.noir.job.common.dto.response.JobAlertResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.CreateJobAlertRequest;
import com.noir.job.dto.request.UpdateJobAlertRequest;
import com.noir.job.entity.JobAlert;
import com.noir.job.mapper.PreferenceMapper;
import com.noir.job.repository.JobAlertRepository;
import com.noir.job.service.JobAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobAlertServiceImpl implements JobAlertService {

    private final JobAlertRepository alertRepository;

    @Override
    @Transactional
    public JobAlertResponse createAlert(Long candidateId, CreateJobAlertRequest req) {
        JobAlert alert = JobAlert.builder()
                .candidateId(candidateId)
                .alertName(req.getAlertName())
                .frequency(req.getFrequency() != null ? req.getFrequency() : AlertFrequency.DAILY)
                .keywords(req.getKeywords())
                .locations(req.getLocations() != null ? req.getLocations() : List.of())
                .jobTypes(req.getJobTypes() != null ? req.getJobTypes() : List.of())
                .workModes(req.getWorkModes() != null ? req.getWorkModes() : List.of())
                .experienceLevels(req.getExperienceLevels() != null ? req.getExperienceLevels() : List.of())
                .industries(req.getIndustries() != null ? req.getIndustries() : List.of())
                .minSalary(req.getMinSalary())
                .salaryCurrency(req.getSalaryCurrency() != null ? req.getSalaryCurrency() : "INR")
                .salaryPeriod(req.getSalaryPeriod())
                .build();

        return PreferenceMapper.toAlertResponse(alertRepository.save(alert));
    }

    @Override
    @Transactional(readOnly = true)
    public JobAlertResponse getAlertById(Long alertId, Long candidateId)
            throws ResourceNotFoundException {
        return PreferenceMapper.toAlertResponse(getAlertEntity(alertId, candidateId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobAlertResponse> getMyAlerts(Long candidateId) {
        return alertRepository.findByCandidateIdOrderByCreatedAtDesc(candidateId)
                .stream().map(PreferenceMapper::toAlertResponse).toList();
    }

    @Override
    @Transactional
    public JobAlertResponse updateAlert(Long alertId, Long candidateId, UpdateJobAlertRequest req)
            throws ResourceNotFoundException {
        JobAlert alert = getAlertEntity(alertId, candidateId);

        if (req.getAlertName() != null) alert.setAlertName(req.getAlertName());
        if (req.getIsActive() != null) alert.setIsActive(req.getIsActive());
        if (req.getFrequency() != null) alert.setFrequency(req.getFrequency());
        if (req.getKeywords() != null) alert.setKeywords(req.getKeywords());
        if (req.getLocations() != null) alert.setLocations(req.getLocations());
        if (req.getJobTypes() != null) alert.setJobTypes(req.getJobTypes());
        if (req.getWorkModes() != null) alert.setWorkModes(req.getWorkModes());
        if (req.getExperienceLevels() != null) alert.setExperienceLevels(req.getExperienceLevels());
        if (req.getIndustries() != null) alert.setIndustries(req.getIndustries());
        if (req.getMinSalary() != null) alert.setMinSalary(req.getMinSalary());
        if (req.getSalaryCurrency() != null) alert.setSalaryCurrency(req.getSalaryCurrency());
        if (req.getSalaryPeriod() != null) alert.setSalaryPeriod(req.getSalaryPeriod());

        return PreferenceMapper.toAlertResponse(alertRepository.save(alert));
    }

    @Override
    @Transactional
    public void deleteAlert(Long alertId, Long candidateId) throws ResourceNotFoundException {
        alertRepository.delete(getAlertEntity(alertId, candidateId));
    }

    @Override
    @Transactional
    public JobAlertResponse toggleAlert(Long alertId, Long candidateId)
            throws ResourceNotFoundException {
        JobAlert alert = getAlertEntity(alertId, candidateId);
        alert.setIsActive(!alert.getIsActive());
        return PreferenceMapper.toAlertResponse(alertRepository.save(alert));
    }

    private JobAlert getAlertEntity(Long alertId, Long candidateId) throws ResourceNotFoundException {
        JobAlert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job alert not found with id: " + alertId));
        if (!alert.getCandidateId().equals(candidateId)) {
            throw new ResourceNotFoundException("Job alert not found with id: " + alertId);
        }
        return alert;
    }
}
