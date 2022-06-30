package ma.itroad.ram.kpi.service.impl;

import java.util.Optional;

import ma.itroad.ram.kpi.domain.ControlRule;
import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.repository.ControlRuleRepository;
import ma.itroad.ram.kpi.service.ControlRuleService;
import ma.itroad.ram.kpi.service.dto.ControlRuleDTO;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import ma.itroad.ram.kpi.service.mapper.ControlRuleMapper;
import ma.itroad.ram.kpi.service.mapper.KpiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ControlRule}.
 */
@Service
@Transactional
public class ControlRuleServiceImpl implements ControlRuleService {

    private final Logger log = LoggerFactory.getLogger(ControlRuleServiceImpl.class);

    private final ControlRuleRepository controlRuleRepository;

    private final ControlRuleMapper controlRuleMapper;

    public ControlRuleServiceImpl( ControlRuleRepository controlRuleRepository, ControlRuleMapper controlRuleMapper) {
        this.controlRuleRepository = controlRuleRepository;
        this.controlRuleMapper = controlRuleMapper;
    }

    @Override
    public ControlRuleDTO save(ControlRuleDTO controlRuleDTO) {
        log.debug("Request to save ControlRule : {}", controlRuleDTO);
        ControlRule controlRule = controlRuleMapper.toEntity(controlRuleDTO);
        controlRule = controlRuleRepository.save(controlRule);
        return controlRuleMapper.toDto(controlRule);
    }

    @Override
    public Optional<ControlRuleDTO> partialUpdate(ControlRuleDTO controlRuleDTO) {
        log.debug("Request to partially update ControlRule : {}", controlRuleDTO);

        return controlRuleRepository
                .findById(controlRuleDTO.getId())
                .map(existingControlRule -> {
                    controlRuleMapper.partialUpdate(existingControlRule, controlRuleDTO);

                    return existingControlRule;
                })
                .map(controlRuleRepository::save)
                .map(controlRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ControlRules");
//        return controlRuleRepository.findAll(pageable).stream().map(controlRuleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
        return controlRuleRepository.findAll(pageable).map(controlRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ControlRuleDTO> findOne(Long id) {
        log.debug("Request to get ControlRule : {}", id);
        return controlRuleRepository.findById(id).map(controlRuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ControlRule : {}", id);
        controlRuleRepository.deleteById(id);
    }

    @Override
    public Optional<ControlRuleDTO> findByKpiBase(Kpi baseKpi) {
        log.debug("Request to get ControlRule : {}", baseKpi.getId());
        return controlRuleRepository.findByKpiBase(baseKpi).map(controlRuleMapper::toDto);
    }
}
