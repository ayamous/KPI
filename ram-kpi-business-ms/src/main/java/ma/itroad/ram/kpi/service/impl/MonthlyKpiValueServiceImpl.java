package ma.itroad.ram.kpi.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import ma.itroad.ram.kpi.domain.Kpi;
import ma.itroad.ram.kpi.domain.MonthlyKpiValue;
import ma.itroad.ram.kpi.domain.MonthlyKpiValueHistory;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.domain.enumeration.Status;
import ma.itroad.ram.kpi.domain.enumeration.ValRef;
import ma.itroad.ram.kpi.repository.KpiRepository;
import ma.itroad.ram.kpi.repository.MonthlyKpiValueHistoryRepository;
import ma.itroad.ram.kpi.repository.MonthlyKpiValueRepository;
import ma.itroad.ram.kpi.service.MonthlyKpiValueService;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import ma.itroad.ram.kpi.service.mapper.MonthlyKpiValueMapper;
import ma.itroad.ram.kpi.service.utils.FileUtils;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MonthlyKpiValue}.
 */
@Service
@Transactional
public class MonthlyKpiValueServiceImpl implements MonthlyKpiValueService {

    private final Logger log = LoggerFactory.getLogger(MonthlyKpiValueServiceImpl.class);
    static String SHEET = "KPI_DATA_SHEET";
    private static final String DATE_FORMAT = "dd-MMM-yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final MonthlyKpiValueRepository monthlyKpiValueRepository;
    private final KpiRepository kpiRepository;
    private final MonthlyKpiValueHistoryRepository monthlyKpiValueHistoryRepository;

    private final MonthlyKpiValueMapper monthlyKpiValueMapper;

    public MonthlyKpiValueServiceImpl(MonthlyKpiValueRepository monthlyKpiValueRepository, KpiRepository kpiRepository, MonthlyKpiValueHistoryRepository monthlyKpiValueHistoryRepository, MonthlyKpiValueMapper monthlyKpiValueMapper) {
        this.monthlyKpiValueRepository = monthlyKpiValueRepository;
        this.kpiRepository = kpiRepository;
        this.monthlyKpiValueHistoryRepository = monthlyKpiValueHistoryRepository;
        this.monthlyKpiValueMapper = monthlyKpiValueMapper;
    }

    @Override
    public MonthlyKpiValueDTO save(MonthlyKpiValueDTO monthlyKpiValueDTO) {
        log.debug("Request to save MonthlyKpiValue : {}", monthlyKpiValueDTO);
        MonthlyKpiValue monthlyKpiValue = monthlyKpiValueMapper.toEntity(monthlyKpiValueDTO);
        monthlyKpiValue = monthlyKpiValueRepository.save(monthlyKpiValue);
        saveMonthlyKpiValueHistory(monthlyKpiValue);
        return monthlyKpiValueMapper.toDto(monthlyKpiValue);
    }


    @Override
    public Optional<MonthlyKpiValueDTO> partialUpdate(MonthlyKpiValueDTO monthlyKpiValueDTO) {
        log.debug("Request to partially update MonthlyKpiValue : {}", monthlyKpiValueDTO);

        return monthlyKpiValueRepository
                .findById(monthlyKpiValueDTO.getId())
                .map(existingMonthlyKpiValue -> {
                    monthlyKpiValueMapper.partialUpdate(existingMonthlyKpiValue, monthlyKpiValueDTO);
                    return existingMonthlyKpiValue;
                })
                .map(monthlyKpiValueRepository::save)
                .map(monthlyKpiValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MonthlyKpiValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MonthlyKpiValues");
        return monthlyKpiValueRepository.findAll(pageable).map(monthlyKpiValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MonthlyKpiValueDTO> findOne(Long id) {
        log.debug("Request to get MonthlyKpiValue : {}", id);
        return monthlyKpiValueRepository.findById(id).map(monthlyKpiValueMapper::toDto);
    }


    @Override
    public void delete(Long id) {
        log.debug("Request to delete MonthlyKpiValue : {}", id);
        monthlyKpiValueRepository.deleteById(id);
    }


    public List<MonthlyKpiValueDTO> importMonthlyKpiValues(MultipartFile file, KpiValueType kpiValType) {
        InputStreamResource xls = null;
        try {
            // Convert to Dtos
            List<MonthlyKpiValueDTO> monthlyKpiValues = excelToMonthlyKpiValues(file.getInputStream(), kpiValType);
            return monthlyKpiValues;
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<MonthlyKpiValueDTO> excelToMonthlyKpiValues(InputStream is, KpiValueType kpiValType) {
        List<MonthlyKpiValue> monthlyKpiValues = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(is);
            System.out.println("workbook : " + workbook);
            Sheet sheet = workbook.getSheet(SHEET);
            System.out.println("Sheet : " + sheet);

            Iterator<Row> rows = sheet.iterator();
            System.out.println("rows : " + rows);
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = (XSSFRow) rows.next();
                System.out.println("CurrentRow : " + currentRow);

              /*  if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }*/
                MonthlyKpiValue monthlyKpiValue = new MonthlyKpiValue();
                int cellIdx = 0;
                KpiValueType enumType;
                LocalDate date;
                Optional<Kpi> kpi;
                for (int colNum = 0; colNum < currentRow.getLastCellNum(); colNum++) {
                    Cell currentCell = currentRow.getCell(colNum, Row.CREATE_NULL_AS_BLANK);
                    System.out.println("Out  : " + FileUtils.cellValue(currentCell));
                    String cellValue = FileUtils.cellValue(currentCell);
                    switch (cellIdx) {
                        case 0:
                            kpi = Optional.ofNullable(kpiRepository.findByReference(cellValue)
                                    .orElseThrow(
                                            () -> new ResourceNotFoundException("Kpi reference not found : " + cellValue)));
                            monthlyKpiValue.setKpi(kpi.get());
                            break;
                        case 2:
                            date = LocalDate.parse(cellValue, formatter);
                            monthlyKpiValue.setDate(date);
                            break;
                        case 3:
                            System.out.println("___________________________________________________________________ " + cellValue);
                            enumType = KpiValueType.valueOf(cellValue);
                            //KpiValueType.fromString(cellValue);
                            monthlyKpiValue.setType(enumType);
                            break;
                        case 4:
                            monthlyKpiValue.setValue(Double.parseDouble(cellValue));
                            break;

                        default:
                            break;
                    }
                    cellIdx++;
                }
                monthlyKpiValue.setStatus(Status.Active);
                monthlyKpiValues.add(monthlyKpiValue);
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("MonthlyKpiValues :  " + monthlyKpiValues.size());
        return monthlyKpiValues.stream().filter(monthlyKpiValue -> {
            System.out.println("monthlyKpiValue type : " + monthlyKpiValue.getType());
            System.out.println("kpiValType : " + kpiValType);
            if (monthlyKpiValue.getType() != null) {
                if (monthlyKpiValue.getType().equals(kpiValType))
                    return true;
                else return false;
            } else {
                return false;
            }

        }).map(monthlyKpiValueMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public Optional<MonthlyKpiValueDTO> findByKpiAndDate(Kpi kpi, LocalDate date) {
        log.debug("Request to get MonthlyKpiValue : {}", date);
        return monthlyKpiValueRepository.findByKpiAndDate(kpi, date).map(monthlyKpiValueMapper::toDto);
    }


    @Override
    public Double getValueByKpiAndDate(Kpi kpi, LocalDate date) {
        log.debug("Request to get MonthlyKpiValue : {}", date);
        Double realisedValue = monthlyKpiValueRepository.getValueByKpiAndDate(kpi.getId(), date, KpiValueType.Realised);
        if (realisedValue != null) {
            return realisedValue;
        }
        Double stoppedValue = monthlyKpiValueRepository.getValueByKpiAndDate(kpi.getId(), date, KpiValueType.Stopped);
        if (stoppedValue != null) {
            return stoppedValue;
        }
        Double estimatedValue = monthlyKpiValueRepository.getValueByKpiAndDate(kpi.getId(), date, KpiValueType.Estimated);
        if (estimatedValue != null) {
            return estimatedValue;
        }
        return null;
    }

    @Override
    public Double sumValuesByKpiAndDatesBetweenStartAndEnd(Kpi kpi, LocalDate start, LocalDate end) {
        long noOfDaysBetween = ChronoUnit.DAYS.between(start, end);
        System.out.println("noOfDaysBetween  : " + noOfDaysBetween);
        List<MonthlyKpiValue> monthlyKpiValuesRealised = monthlyKpiValueRepository.sumValuesByKpiAndDatesBetweenStartAndEnd(kpi.getId(), start, end, KpiValueType.Realised);

        if (monthlyKpiValuesRealised != null && monthlyKpiValuesRealised.size() == noOfDaysBetween) {// some missing days values
            return getMonthlyKpiValuesSum(monthlyKpiValuesRealised);
        }

        List<MonthlyKpiValue> monthlyKpiValuesStopped = monthlyKpiValueRepository.sumValuesByKpiAndDatesBetweenStartAndEnd(kpi.getId(), start, end, KpiValueType.Stopped);
        if (monthlyKpiValuesStopped != null && monthlyKpiValuesStopped.size() == noOfDaysBetween) {// some missing days values
            return getMonthlyKpiValuesSum(monthlyKpiValuesStopped);
        }

        List<MonthlyKpiValue> monthlyKpiValuesEstimated = monthlyKpiValueRepository.sumValuesByKpiAndDatesBetweenStartAndEnd(kpi.getId(), start, end, KpiValueType.Estimated);
        if (monthlyKpiValuesEstimated != null && monthlyKpiValuesEstimated.size() == noOfDaysBetween) {// some missing days values
            return getMonthlyKpiValuesSum(monthlyKpiValuesEstimated);
        }

        return null;

    }


    public Double getMonthlyKpiValuesSum(List<MonthlyKpiValue> monthlyKpiValues) {
        Double sum = 0.0D;
        for (MonthlyKpiValue monthlyKpiValue : monthlyKpiValues) {
            if (monthlyKpiValue.getValue() != null) {
                sum += monthlyKpiValue.getValue();
            }
        }
        return sum;
    }

    private void saveMonthlyKpiValueHistory(MonthlyKpiValue monthlyKpiValue) {
        MonthlyKpiValueHistory monthlyKpiValueHistory = new MonthlyKpiValueHistory();
        monthlyKpiValueHistory.setMonthlyKpiValue(monthlyKpiValue);
        monthlyKpiValueHistory.setDate(monthlyKpiValue.getDate());
        monthlyKpiValueHistory.setValue(monthlyKpiValue.getValue());
        monthlyKpiValueHistoryRepository.save(monthlyKpiValueHistory);
    }

    @Override
    public Double getValueByKpiAndDateAndBudgetVersionType(Kpi kpi, LocalDate date) {
        log.debug("Request to get MonthlyKpiValue : {}", date);
        Double budgetValue = monthlyKpiValueRepository.getValueByKpiAndDate(kpi.getId(), date, KpiValueType.Budget);
        if (budgetValue != null) {
            return budgetValue;
        }
        return null;
    }
}
