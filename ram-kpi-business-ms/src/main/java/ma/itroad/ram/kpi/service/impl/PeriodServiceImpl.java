package ma.itroad.ram.kpi.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import ma.itroad.ram.kpi.domain.Line;
import ma.itroad.ram.kpi.domain.Period;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.repository.PeriodRepository;
import ma.itroad.ram.kpi.service.PeriodService;
import ma.itroad.ram.kpi.service.dto.LineDTO;
import ma.itroad.ram.kpi.service.dto.PeriodDTO;
import ma.itroad.ram.kpi.service.mapper.PeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Period}.
 */
@Service
@Transactional
public class PeriodServiceImpl implements PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodServiceImpl.class);

    private final PeriodRepository periodRepository;

    private final PeriodMapper periodMapper;

    public PeriodServiceImpl(PeriodRepository periodRepository, PeriodMapper periodMapper) {
        this.periodRepository = periodRepository;
        this.periodMapper = periodMapper;
    }

    @Override
    public PeriodDTO save(PeriodDTO periodDTO) {
        log.debug("Request to save Period : {}", periodDTO);
        Period period = periodMapper.toEntity(periodDTO);
        period = periodRepository.save(period);
        return periodMapper.toDto(period);
    }

    @Override
    public Optional<PeriodDTO> partialUpdate(PeriodDTO periodDTO) {
        log.debug("Request to partially update Period : {}", periodDTO);

        return periodRepository
                .findById(periodDTO.getId())
                .map(existingPeriod -> {
                    periodMapper.partialUpdate(existingPeriod, periodDTO);

                    return existingPeriod;
                })
                .map(periodRepository::save)
                .map(periodMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PeriodDTO> findAll() {
        log.debug("Request to get all Periods");
        return periodRepository.findAll().stream()
                .map(periodMapper::toDto)
                .map(this::sortByVersion)
                .collect(Collectors.toCollection(LinkedList::new));

    }

    private PeriodDTO sortByVersion(PeriodDTO periodDTO) {
        List<KpiValueType> sortOrderList = Arrays.asList(KpiValueType.Budget, KpiValueType.Realised, KpiValueType.Estimated, KpiValueType.Stopped);
        if (periodDTO != null) {
            if (periodDTO.getLines() != null && periodDTO.getLines().size() > 0) {
                List<Line> lines = periodDTO.getLines();
                List<Line> sortedLines = new ArrayList<>();
                for (KpiValueType kpiValueType : sortOrderList) {
                    for (Line line : lines) {
                        if (kpiValueType.equals(line.getVersion())) {
                            sortedLines.add(line);
                        }
                    }
                }
                periodDTO.setLines(sortedLines);
            }
        }
        return periodDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodDTO> findOne(Long id) {
        log.debug("Request to get Period : {}", id);
        return periodRepository.findById(id).map(periodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Period : {}", id);
        periodRepository.deleteById(id);
    }
}
