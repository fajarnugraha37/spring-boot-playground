package com.jhipster.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.jhipster.domain.Department;
import com.jhipster.repository.DepartmentRepository;
import com.jhipster.repository.search.DepartmentSearchRepository;
import com.jhipster.service.DepartmentService;
import com.jhipster.service.dto.DepartmentDTO;
import com.jhipster.service.mapper.DepartmentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Department}.
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    private final DepartmentSearchRepository departmentSearchRepository;

    public DepartmentServiceImpl(
        DepartmentRepository departmentRepository,
        DepartmentMapper departmentMapper,
        DepartmentSearchRepository departmentSearchRepository
    ) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.departmentSearchRepository = departmentSearchRepository;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        log.debug("Request to save Department : {}", departmentDTO);
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        DepartmentDTO result = departmentMapper.toDto(department);
        departmentSearchRepository.index(department);
        return result;
    }

    @Override
    public DepartmentDTO update(DepartmentDTO departmentDTO) {
        log.debug("Request to update Department : {}", departmentDTO);
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        DepartmentDTO result = departmentMapper.toDto(department);
        departmentSearchRepository.index(department);
        return result;
    }

    @Override
    public Optional<DepartmentDTO> partialUpdate(DepartmentDTO departmentDTO) {
        log.debug("Request to partially update Department : {}", departmentDTO);

        return departmentRepository
            .findById(departmentDTO.getId())
            .map(existingDepartment -> {
                departmentMapper.partialUpdate(existingDepartment, departmentDTO);

                return existingDepartment;
            })
            .map(departmentRepository::save)
            .map(savedDepartment -> {
                departmentSearchRepository.save(savedDepartment);

                return savedDepartment;
            })
            .map(departmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAll() {
        log.debug("Request to get all Departments");
        return departmentRepository.findAll().stream().map(departmentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentDTO> findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        return departmentRepository.findById(id).map(departmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);
        departmentRepository.deleteById(id);
        departmentSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> search(String query) {
        log.debug("Request to search Departments for query {}", query);
        return StreamSupport
            .stream(departmentSearchRepository.search(query).spliterator(), false)
            .map(departmentMapper::toDto)
            .collect(Collectors.toList());
    }
}
