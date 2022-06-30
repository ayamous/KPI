package ma.itroad.ram.kpi.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ma.itroad.ram.kpi.domain.MonthlyKpiValueHistory;
import ma.itroad.ram.kpi.repository.MonthlyKpiValueHistoryRepository;
import ma.itroad.ram.kpi.service.MonthlyKpiValueHistoryService;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueHistoryDTO;
import ma.itroad.ram.kpi.service.mapper.MonthlyKpiValueHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MonthlyKpiValueHistory}.
 */
@Service
@Transactional
public class MonthlyKpiValueHistoryServiceImpl implements MonthlyKpiValueHistoryService {

    private final Logger log = LoggerFactory.getLogger(MonthlyKpiValueHistoryServiceImpl.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final MonthlyKpiValueHistoryRepository monthlyKpiValueHistoryRepository;

    private final MonthlyKpiValueHistoryMapper monthlyKpiValueHistoryMapper;

    public MonthlyKpiValueHistoryServiceImpl(
            MonthlyKpiValueHistoryRepository monthlyKpiValueHistoryRepository,
            MonthlyKpiValueHistoryMapper monthlyKpiValueHistoryMapper
    ) {
        this.monthlyKpiValueHistoryRepository = monthlyKpiValueHistoryRepository;
        this.monthlyKpiValueHistoryMapper = monthlyKpiValueHistoryMapper;
    }

    @Override
    public MonthlyKpiValueHistoryDTO save(MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO) {
        log.debug("Request to save MonthlyKpiValueHistory : {}", monthlyKpiValueHistoryDTO);
        MonthlyKpiValueHistory monthlyKpiValueHistory = monthlyKpiValueHistoryMapper.toEntity(monthlyKpiValueHistoryDTO);
        monthlyKpiValueHistory = monthlyKpiValueHistoryRepository.save(monthlyKpiValueHistory);
        return monthlyKpiValueHistoryMapper.toDto(monthlyKpiValueHistory);
    }

    @Override
    public Optional<MonthlyKpiValueHistoryDTO> partialUpdate(MonthlyKpiValueHistoryDTO monthlyKpiValueHistoryDTO) {
        log.debug("Request to partially update MonthlyKpiValueHistory : {}", monthlyKpiValueHistoryDTO);

        return monthlyKpiValueHistoryRepository
                .findById(monthlyKpiValueHistoryDTO.getId())
                .map(existingMonthlyKpiValueHistory -> {
                    monthlyKpiValueHistoryMapper.partialUpdate(existingMonthlyKpiValueHistory, monthlyKpiValueHistoryDTO);

                    return existingMonthlyKpiValueHistory;
                })
                .map(monthlyKpiValueHistoryRepository::save)
                .map(monthlyKpiValueHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonthlyKpiValueHistoryDTO> findAll() {
        log.debug("Request to get all MonthlyKpiValueHistories");
        return monthlyKpiValueHistoryRepository
                .findAll()
                .stream()
                .map(monthlyKpiValueHistoryMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MonthlyKpiValueHistoryDTO> findOne(Long id) {
        log.debug("Request to get MonthlyKpiValueHistory : {}", id);
        return monthlyKpiValueHistoryRepository.findById(id).map(monthlyKpiValueHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MonthlyKpiValueHistory : {}", id);
        monthlyKpiValueHistoryRepository.deleteById(id);
    }


    /**
     * Get all the monthlyKpiValueHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MonthlyKpiValueHistoryDTO> findByMonthlyKpiValueId(Long id) {
        log.debug("Request to get all MonthlyKpiValueHistories");

        return monthlyKpiValueHistoryRepository
                .findAllByMonthlyKpiValueId(id)
                .stream()
                .map(monthlyKpiValueHistoryMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}

