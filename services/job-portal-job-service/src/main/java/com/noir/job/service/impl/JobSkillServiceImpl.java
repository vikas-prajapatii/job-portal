package com.noir.job.service.impl;

import com.noir.job.dto.JobSkillResponse;
import com.noir.job.mapper.JobSkillMapper;
import com.noir.job.model.JobSkill;
import com.noir.job.payload.JobSkillRequest;
import com.noir.job.repository.JobSkillRepository;
import com.noir.job.service.JobSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSkillServiceImpl implements JobSkillService {
    private final JobSkillRepository jobSkillRepository;

    @Override
    public JobSkillResponse createSkill(JobSkillRequest req) throws Exception {
        if(jobSkillRepository.existsByName(req.getName())) {
            throw new Exception("skill name already exist");
        }
        String slug = generateUniqueSlug(req.getName());
        JobSkill skill = JobSkill.builder()
                .name(req.getName())
                .slug(slug)
                .category(req.getCategory())
                .build();
        JobSkill saveSkill = jobSkillRepository.save(skill);
        return JobSkillMapper.toJobSkillResponse(saveSkill);
    }

    @Override
    public List<JobSkillResponse> getAllSkills() {
        return jobSkillRepository.findByActiveTrue()
                .stream().map(JobSkillMapper::toJobSkillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobSkillResponse getSkillsById(Long id) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id)
                .orElseThrow(() -> new Exception("Skill not found with id: " + id));
        return JobSkillMapper.toJobSkillResponse(skill);
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest req) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id)
                .orElseThrow(() -> new Exception("Skill not found with id: " + id));

        if (!skill.getName().equalsIgnoreCase(req.getName()) && jobSkillRepository.existsByName(req.getName())) {
            throw new Exception("Skill name already exists");
        }

        skill.setName(req.getName());
        skill.setCategory(req.getCategory());
        JobSkill updated = jobSkillRepository.save(skill);
        return JobSkillMapper.toJobSkillResponse(updated);
    }

    @Override
    public void deleteSkill(Long id) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id)
                .orElseThrow(() -> new Exception("Skill not found with id: " + id));
        skill.setActive(false);
        JobSkill updated = jobSkillRepository.save(skill);
    }

    @Override
    public Set<JobSkill> getSkillsByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Set.of();
        }
        return new java.util.HashSet<>(jobSkillRepository.findAllById(ids));
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-zA-Z0-9]", "").trim()
                .replaceAll("[\\s-]", "-");
        if(!jobSkillRepository.existBySlug(base)){
            return base;
        }
        int counter = 1;
        while (jobSkillRepository.existBySlug(base+"-"+counter)){
            counter++;
        }
        return base+"-"+counter;

    }
}
