package com.noir.job.service.impl;

import com.noir.job.dto.JobTagResponse;
import com.noir.job.mapper.JobMapper;
import com.noir.job.mapper.JobTagMapper;
import com.noir.job.model.JobTags;
import com.noir.job.payload.JobTagRequest;
import com.noir.job.repository.JobTagRepository;
import com.noir.job.service.JobTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class JobTagServiceImpl implements JobTagService {
    private final JobTagRepository jobTagRepository;
    @Override
    public JobTagResponse createTag(JobTagRequest req) throws Exception {
        if (jobTagRepository.existsByName(req.getName())) {
            throw new Exception("Tag '" + req.getName() + "' already exists");
        }

        String slug = generateUniqueSlug(req.getName());
        JobTags tag = JobTags.builder()
                .name(req.getName())
                .slug(slug)
                .build();
        JobTags savedTag = jobTagRepository.save(tag);
        return JobTagMapper.toResponse(savedTag);
    }
    @Override
    public List<JobTagResponse> getAllTags() {
        return jobTagRepository.findAll().stream()
                .map(JobTagMapper::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<JobTagResponse> searchTags(String keyword) {
        return List.of();
    }
    @Override
    public JobTagResponse getTagId(Long id) throws Exception {
       JobTags jobTags = getTagEntitiesByIds(id);
       return JobTagMapper.toResponse(jobTags);

    }
    @Override
    public JobTagResponse updateTag(Long id, JobTagRequest req) throws Exception {
        JobTags tag = getTagEntitiesByIds(id);

        if (!tag.getName().equals(req.getName()) && jobTagRepository.existsByName(req.getName())) {
            throw new Exception("Tag '" + req.getName() + "' already exists");
        }
        tag.setName(req.getName());
        return JobTagMapper.toResponse(jobTagRepository.save(tag));
    }
    @Override
    public void deleteTag(Long id) throws Exception {
        JobTags jobTag = getTagEntitiesByIds(id);

    }
    @Override
    public JobTags getTagEntitiesByIds(Long id) throws Exception {
      return jobTagRepository.findById(id).orElseThrow(
              () -> new Exception("job tag not found"));

    }
    @Override
    public Set<JobTags> getTagsByIds(Set<Long> ids) throws Exception {
        List<JobTags> jobTags = jobTagRepository.findAllById(ids);
        return new HashSet<>(jobTags);
    }
    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-zA-Z0-9]", "").trim()
                .replaceAll("[\\s-]", "-");
        if(!jobTagRepository.existsBySlug(base)){
            return base;
        }
        int counter = 1;
        while (jobTagRepository.existsBySlug(base+"-"+counter)){
            counter++;
        }
        return base+"-"+counter;
    }
}
