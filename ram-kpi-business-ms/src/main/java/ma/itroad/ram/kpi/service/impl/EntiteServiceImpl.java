package ma.itroad.ram.kpi.service.impl;

import java.util.Optional;

import ma.itroad.ram.kpi.domain.Entite;
import ma.itroad.ram.kpi.repository.EntiteRepository;
import ma.itroad.ram.kpi.service.EntiteService;
import ma.itroad.ram.kpi.service.dto.EntiteDTO;
import ma.itroad.ram.kpi.service.mapper.EntiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Entite}.
 */
@Service
@Transactional
public class EntiteServiceImpl implements EntiteService {

    private final Logger log = LoggerFactory.getLogger(EntiteServiceImpl.class);

    private final EntiteRepository entiteRepository;

    private final EntiteMapper entiteMapper;

    public EntiteServiceImpl(EntiteRepository entiteRepository, EntiteMapper entiteMapper) {
        this.entiteRepository = entiteRepository;
        this.entiteMapper = entiteMapper;
    }

    @Override
    public EntiteDTO save(EntiteDTO entiteDTO) {
        log.debug("Request to save Entite : {}", entiteDTO);
        Entite entite = entiteMapper.toEntity(entiteDTO);
        entite = entiteRepository.save(entite);
        return entiteMapper.toDto(entite);
    }

    @Override
    public Optional<EntiteDTO> partialUpdate(EntiteDTO entiteDTO) {
        log.debug("Request to partially update Entite : {}", entiteDTO);

        return entiteRepository
                .findById(entiteDTO.getId())
                .map(existingEntite -> {
                    entiteMapper.partialUpdate(existingEntite, entiteDTO);

                    return existingEntite;
                })
                .map(entiteRepository::save)
                .map(entiteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entites");
        return entiteRepository.findAll(pageable).map(entiteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntiteDTO> findOne(Long id) {
        log.debug("Request to get Entite : {}", id);
        return entiteRepository.findById(id).map(entiteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entite : {}", id);
        entiteRepository.deleteById(id);
    }
}
