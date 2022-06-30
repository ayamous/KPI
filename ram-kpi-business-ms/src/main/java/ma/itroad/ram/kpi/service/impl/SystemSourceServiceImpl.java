package ma.itroad.ram.kpi.service.impl;

import java.util.Optional;

import ma.itroad.ram.kpi.domain.SystemSource;
import ma.itroad.ram.kpi.repository.SystemSourceRepository;
import ma.itroad.ram.kpi.service.SystemSourceService;
import ma.itroad.ram.kpi.service.dto.SystemSourceDTO;
import ma.itroad.ram.kpi.service.mapper.SystemSourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SystemSource}.
 */
@Service
@Transactional
public class SystemSourceServiceImpl implements SystemSourceService {

    private final Logger log = LoggerFactory.getLogger(SystemSourceServiceImpl.class);

    private final SystemSourceRepository systemSourceRepository;

    private final SystemSourceMapper systemSourceMapper;

    public SystemSourceServiceImpl(SystemSourceRepository systemSourceRepository, SystemSourceMapper systemSourceMapper) {
        this.systemSourceRepository = systemSourceRepository;
        this.systemSourceMapper = systemSourceMapper;
    }

    @Override
    public SystemSourceDTO save(SystemSourceDTO systemSourceDTO) {
        log.debug("Request to save SystemSource : {}", systemSourceDTO);
        SystemSource systemSource = systemSourceMapper.toEntity(systemSourceDTO);
        systemSource = systemSourceRepository.save(systemSource);
        return systemSourceMapper.toDto(systemSource);
    }

    @Override
    public Optional<SystemSourceDTO> partialUpdate(SystemSourceDTO systemSourceDTO) {
        log.debug("Request to partially update SystemSource : {}", systemSourceDTO);

        return systemSourceRepository
                .findById(systemSourceDTO.getId())
                .map(existingSystemSource -> {
                    systemSourceMapper.partialUpdate(existingSystemSource, systemSourceDTO);

                    return existingSystemSource;
                })
                .map(systemSourceRepository::save)
                .map(systemSourceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SystemSourceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemSources");
        return systemSourceRepository.findAll(pageable).map(systemSourceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SystemSourceDTO> findOne(Long id) {
        log.debug("Request to get SystemSource : {}", id);
        return systemSourceRepository.findById(id).map(systemSourceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemSource : {}", id);
        systemSourceRepository.deleteById(id);
    }
}
