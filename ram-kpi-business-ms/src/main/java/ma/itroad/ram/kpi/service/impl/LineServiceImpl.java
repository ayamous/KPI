package ma.itroad.ram.kpi.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ma.itroad.ram.kpi.domain.Line;
import ma.itroad.ram.kpi.repository.LineRepository;
import ma.itroad.ram.kpi.service.LineService;
import ma.itroad.ram.kpi.service.dto.LineDTO;
import ma.itroad.ram.kpi.service.mapper.LineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Line}.
 */
@Service
@Transactional
public class LineServiceImpl implements LineService {

    private final Logger log = LoggerFactory.getLogger(LineServiceImpl.class);

    private final LineRepository lineRepository;

    private final LineMapper lineMapper;

    public LineServiceImpl(LineRepository lineRepository, LineMapper lineMapper) {
        this.lineRepository = lineRepository;
        this.lineMapper = lineMapper;
    }

    @Override
    public LineDTO save(LineDTO lineDTO) {
        log.debug("Request to save Line : {}", lineDTO);
        Line line = lineMapper.toEntity(lineDTO);
        line = lineRepository.save(line);
        return lineMapper.toDto(line);
    }

    @Override
    public Optional<LineDTO> partialUpdate(LineDTO lineDTO) {
        log.debug("Request to partially update Line : {}", lineDTO);

        return lineRepository
                .findById(lineDTO.getId())
                .map(existingLine -> {
                    lineMapper.partialUpdate(existingLine, lineDTO);

                    return existingLine;
                })
                .map(lineRepository::save)
                .map(lineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineDTO> findAll() {
        log.debug("Request to get all Lines");
        return lineRepository.findAll().stream().map(lineMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LineDTO> findOne(Long id) {
        log.debug("Request to get Line : {}", id);
        return lineRepository.findById(id).map(lineMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Line : {}", id);
        lineRepository.deleteById(id);
    }
}
