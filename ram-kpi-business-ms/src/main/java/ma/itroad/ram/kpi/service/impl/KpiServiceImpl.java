package ma.itroad.ram.kpi.service.impl;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import ma.itroad.ram.kpi.domain.*;
import ma.itroad.ram.kpi.domain.enumeration.InsertionMethod;
import ma.itroad.ram.kpi.domain.enumeration.KpiValueType;
import ma.itroad.ram.kpi.domain.enumeration.Status;
import ma.itroad.ram.kpi.repository.KpiRepository;
import ma.itroad.ram.kpi.repository.MonthlyKpiValueRepository;
import ma.itroad.ram.kpi.service.KpiCriteria;
import ma.itroad.ram.kpi.service.KpiService;
import ma.itroad.ram.kpi.service.PeriodService;
import ma.itroad.ram.kpi.service.dto.KpiDTO;
import ma.itroad.ram.kpi.service.dto.KpiDetailDTO;
import ma.itroad.ram.kpi.service.dto.MonthlyKpiValueDTO;
import ma.itroad.ram.kpi.service.dto.PeriodDTO;
import ma.itroad.ram.kpi.service.mapper.KpiMapper;
import ma.itroad.ram.kpi.service.mapper.MonthlyKpiValueMapper;
import ma.itroad.ram.kpi.web.rest.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import javax.persistence.criteria.JoinType;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Kpi}.
 */
@Service
@Transactional
public class KpiServiceImpl extends QueryService<Kpi> implements KpiService {

    private final Logger log = LoggerFactory.getLogger(KpiServiceImpl.class);
    static String[] HEADERS = {"KPI Name", "KPI REFERENCE", "NOV", "DEC", "JAN", "FÉV", "MAR", "AVR", "MAI", "JUIN", "JUIL", "AOÛT", "SEPT", "OCT", "Total (DH)"};
    static String[] HEADER1_DETAIL = {"Référence Indicateur", "Nom", "Entité", "Délai de production"};
    static String[] HEADER2_DETAIL = {"", "NOV", "DEC", "JAN", "FÉV", "MAR", "AVR", "MAI", "JUIN", "JUIL", "AOÛT", "SEPT", "OCT", "Total (DH)"};


    static String SHEET = "KpisDataSHEET";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final int MONTHS_COUNT = 12;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final KpiRepository kpiRepository;
    private final MonthlyKpiValueRepository monthlyKpiValueRepository;
    private final PeriodService periodService;
    private final MonthlyKpiValueMapper monthlyKpiValueMapper;

    private final KpiMapper kpiMapper;

    public KpiServiceImpl(KpiRepository kpiRepository, MonthlyKpiValueRepository monthlyKpiValueRepository, PeriodService periodService, MonthlyKpiValueMapper monthlyKpiValueMapper, KpiMapper kpiMapper) {
        this.kpiRepository = kpiRepository;
        this.monthlyKpiValueRepository = monthlyKpiValueRepository;
        this.periodService = periodService;
        this.monthlyKpiValueMapper = monthlyKpiValueMapper;
        this.kpiMapper = kpiMapper;
    }

    @Override
    public KpiDTO save(KpiDTO kpiDTO) {
        log.debug("Request to save Kpi : {}", kpiDTO);
        Kpi kpi = kpiMapper.toEntity(kpiDTO);
        kpi = kpiRepository.save(kpi);
        System.out.println("Saved kpi : " + kpi);
        KpiDTO kpiDto = kpiMapper.toDto(kpi);
        System.out.println("Saved kpiDto : " + kpiDto);
        return kpiDto;

    }

    @Override
    public Optional<KpiDTO> partialUpdate(KpiDTO kpiDTO) {
        log.debug("Request to partially update Kpi : {}", kpiDTO);

        return kpiRepository
                .findById(kpiDTO.getId())
                .map(existingKpi -> {
                    kpiMapper.partialUpdate(existingKpi, kpiDTO);
                    return existingKpi;
                })
                .map(kpiRepository::save)
                .map(kpiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KpiDTO> findAll(String filterBy, Pageable pageable) {
        log.debug("Request to get all Kpis");
        return (("notMapped".equalsIgnoreCase(filterBy)) ?
                kpiRepository.findByKpiGroupIdNull(pageable) :
                kpiRepository.findAll(pageable))
                .map(kpiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KpiDTO> findOne(Long id) {
        log.info("Request to get Kpi : {}", kpiRepository.findById(id));
        return kpiRepository.findById(id).map(kpiMapper::toDto);
    }

    private Line getLineByVersion(List<Line> lines, KpiValueType kpiValueType) {
        for (Line line : lines) {
            if (line.getVersion().equals(kpiValueType)) {
                return line;
            }
        }
        return null;
    }

    private List<MonthlyKpiValue> setMonthlyKpiValuesStatus(List<MonthlyKpiValue> monthlyKpiValues, List<PeriodDTO> periods) {
        System.out.println("Periods : " + periods);
        if (periods != null && periods.size() > 0) {
            PeriodDTO period = periods.get(0);
            if (period != null) {
                List<Line> lines = period.getLines();
                for (MonthlyKpiValue mkv : monthlyKpiValues) {
                    KpiValueType mkvType = mkv.getType();
                    Line line = getLineByVersion(lines, mkvType);
                    if (line != null) {
                        if (mkv.getDate().isAfter(line.getFrom()) && mkv.getDate().isBefore(line.getTo()) && line.getStatus().equals(Status.Active)) {
                            mkv.setStatus(Status.Active);
                        } else {
                            mkv.setStatus(Status.Inactive);
                        }
                    }
                }
            }
        }
        return monthlyKpiValues;
    }


    @Override
    @Transactional(readOnly = true)
    public KpiDetailDTO findDetailOne(Long id, Integer year) throws ResourceNotFoundException {
        Kpi kpi = kpiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kpi with ID : " + id + " Not Found!"));

        List<MonthlyKpiValue> monthlyKpiRealisedValuesRefYear1 = new ArrayList<>();
        List<MonthlyKpiValue> monthlyKpiBudgetValuesRefYear1 = new ArrayList<>();
        List<MonthlyKpiValue> monthlyKpiRealisedValuesRefYear2 = new ArrayList<>();
        List<MonthlyKpiValue> monthlyKpiBudgetValuesRefYear2 = new ArrayList<>();
        List<MonthlyKpiValue> budgetMonthlyKpiValues = new ArrayList<>();
        List<MonthlyKpiValue> realisedMonthlyKpiValues = new ArrayList<>();
        List<MonthlyKpiValue> stoppedMonthlyKpiValues = new ArrayList<>();
        List<MonthlyKpiValueDTO> ecarts = new ArrayList<>();
        List<MonthlyKpiValueDTO> ecartsRefYear1 = new ArrayList<>();
        ;
        List<MonthlyKpiValueDTO> ecartsRefYear2 = new ArrayList<>();
        ;

        KpiDetailDTO kpiDetailDTO = new KpiDetailDTO();

        KpiDTO kpiDTO = kpiMapper.toDto(kpi);
        kpiDetailDTO.setId(kpi.getId());
        kpiDetailDTO.setReference(kpi.getReference());
        kpiDetailDTO.setName(kpi.getName());
        kpiDetailDTO.setEntite(kpiDTO.getEntite());
        kpiDetailDTO.setProductionPeriod(kpiDTO.getProductionPeriod());

        if (year != null) {
            LocalDate start = null;
            LocalDate end = null;
            try {
                start = LocalDate.parse(year + "-10-01", formatter);
                end = LocalDate.parse((year + 1) + "-11-01", formatter);
            } catch (DateTimeParseException ex) {
                // or display a user-friendly message on the UI
                throw new IllegalArgumentException(
                        String.format("Could not parse input dates %s, %s " +
                                "please input a date in format dd-MM-yyyy", start, end));
            }

            List<PeriodDTO> periods = periodService.findAll();
            if (periods != null && periods.size() > 0) {
                PeriodDTO period = periods.get(0);
                String stringYearRef1 = period.getYearRef1();
                if (stringYearRef1 != null) {
                    String[] yearRef1Split = stringYearRef1.split("-");
                    try {
                        int yearRef1 = year - Integer.parseInt(yearRef1Split[1]);
                        LocalDate yearRef1Start = LocalDate.parse(yearRef1 + "-10-01", formatter);
                        LocalDate yearRef1End = LocalDate.parse((yearRef1 + 1) + "-11-01", formatter);
                        monthlyKpiRealisedValuesRefYear1 = monthlyKpiValueRepository.fetchMonthlyKpiValuesByVersion(id, yearRef1Start, yearRef1End, KpiValueType.Realised);
                        if (monthlyKpiRealisedValuesRefYear1.size() != MONTHS_COUNT) {
                            monthlyKpiRealisedValuesRefYear1 = generateEmptyMonthlyKpiValues(kpi, yearRef1, monthlyKpiRealisedValuesRefYear1, KpiValueType.Realised);
                        }

                        monthlyKpiBudgetValuesRefYear1 = monthlyKpiValueRepository.fetchMonthlyKpiValuesByVersion(id, yearRef1Start, yearRef1End, KpiValueType.Budget);
                        if (monthlyKpiBudgetValuesRefYear1.size() != MONTHS_COUNT) {
                            monthlyKpiBudgetValuesRefYear1 = generateEmptyMonthlyKpiValues(kpi, yearRef1, monthlyKpiBudgetValuesRefYear1, KpiValueType.Budget);
                        }

                        if (monthlyKpiRealisedValuesRefYear1.size() == MONTHS_COUNT) {
                            kpiDetailDTO.setRefYear1(toDTO(monthlyKpiRealisedValuesRefYear1));
                            kpiDetailDTO.setTotalRefYear1(kpiDetailDTO.getSumOfMonthlyKpiValues(kpiDetailDTO.getRefYear1()));
                        }
                        //ecartsRefYear1
                        List<MonthlyKpiValueDTO> monthlyKpiBudgetValuesRefYear1DTO = monthlyKpiBudgetValuesRefYear1.stream().map(monthlyKpiValueMapper::toDto).collect(Collectors.toList());
                        ecartsRefYear1 = getEcart(monthlyKpiBudgetValuesRefYear1DTO, kpiDetailDTO.getRefYear1());
                        kpiDetailDTO.setEcartsRefYear1(ecartsRefYear1);
                        kpiDetailDTO.setTotalEcartsRefYear1(kpiDetailDTO.getSumOfMonthlyKpiValues(kpiDetailDTO.getEcartsRefYear1()));

                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }

                String stringYearRef2 = period.getYearRef2();
                if (stringYearRef2 != null) {
                    String[] yearRef2Split = stringYearRef2.split("-");
                    try {
                        int yearRef2 = year - Integer.parseInt(yearRef2Split[1]);
                        LocalDate yearRef2Start = LocalDate.parse(yearRef2 + "-10-01", formatter);
                        LocalDate yearRef2End = LocalDate.parse((yearRef2 + 1) + "-11-01", formatter);
                        monthlyKpiRealisedValuesRefYear2 = monthlyKpiValueRepository.fetchMonthlyKpiValuesByVersion(id, yearRef2Start, yearRef2End, KpiValueType.Realised);
                        if (monthlyKpiRealisedValuesRefYear2.size() != MONTHS_COUNT) {
                            monthlyKpiRealisedValuesRefYear2 = generateEmptyMonthlyKpiValues(kpi, yearRef2, monthlyKpiRealisedValuesRefYear2, KpiValueType.Realised);
                        }
                        monthlyKpiBudgetValuesRefYear2 = monthlyKpiValueRepository.fetchMonthlyKpiValuesByVersion(id, yearRef2Start, yearRef2End, KpiValueType.Realised);
                        if (monthlyKpiBudgetValuesRefYear2.size() != MONTHS_COUNT) {
                            monthlyKpiBudgetValuesRefYear2 = generateEmptyMonthlyKpiValues(kpi, yearRef2, monthlyKpiBudgetValuesRefYear2, KpiValueType.Realised);
                        }

                        if (monthlyKpiRealisedValuesRefYear2.size() == MONTHS_COUNT) {
                            kpiDetailDTO.setRefYear2(toDTO(monthlyKpiRealisedValuesRefYear2));
                            kpiDetailDTO.setTotalRefYear2(kpiDetailDTO.getSumOfMonthlyKpiValues(kpiDetailDTO.getRefYear2()));
                        }
                        //ecartsRefYear2
                        List<MonthlyKpiValueDTO> monthlyKpiBudgetValuesRefYear2DTO = monthlyKpiBudgetValuesRefYear2.stream().map(monthlyKpiValueMapper::toDto).collect(Collectors.toList());
                        ecartsRefYear2 = getEcart(monthlyKpiBudgetValuesRefYear2DTO, kpiDetailDTO.getRefYear2());
                        kpiDetailDTO.setEcartsRefYear2(ecartsRefYear2);
                        kpiDetailDTO.setTotalEcartsRefYear2(kpiDetailDTO.getSumOfMonthlyKpiValues(kpiDetailDTO.getEcartsRefYear2()));

                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            budgetMonthlyKpiValues = monthlyKpiValueRepository.fetchMonthlyKpiValuesByVersion(id, start, end, KpiValueType.Budget);
            if (budgetMonthlyKpiValues.size() != MONTHS_COUNT) {
                budgetMonthlyKpiValues = generateEmptyMonthlyKpiValues(kpi, year, budgetMonthlyKpiValues, KpiValueType.Budget);
            }
            realisedMonthlyKpiValues = monthlyKpiValueRepository.fetchMonthlyKpiValuesByVersion(id, start, end, KpiValueType.Realised);
            if (realisedMonthlyKpiValues.size() != MONTHS_COUNT) {
                realisedMonthlyKpiValues = generateEmptyMonthlyKpiValues(kpi, year, realisedMonthlyKpiValues, KpiValueType.Realised);
            }
            stoppedMonthlyKpiValues = monthlyKpiValueRepository.fetchMonthlyKpiValuesByVersion(id, start, end, KpiValueType.Stopped);
            if (stoppedMonthlyKpiValues.size() != MONTHS_COUNT) {
                stoppedMonthlyKpiValues = generateEmptyMonthlyKpiValues(kpi, year, stoppedMonthlyKpiValues, KpiValueType.Stopped);
            }
        }

        List<PeriodDTO> periods = periodService.findAll();
        kpiDetailDTO.setBudgetMonthlyKpiValues(toDTO(setMonthlyKpiValuesStatus(budgetMonthlyKpiValues, periods)));
        kpiDetailDTO.setTotalBudget(kpiDetailDTO.getSumOfMonthlyKpiValues(kpiDetailDTO.getBudgetMonthlyKpiValues()));

        kpiDetailDTO.setRealisedMonthlyKpiValues(toDTO(setMonthlyKpiValuesStatus(realisedMonthlyKpiValues, periods)));
        kpiDetailDTO.setTotalRealised(kpiDetailDTO.getSumOfMonthlyKpiValues(kpiDetailDTO.getRealisedMonthlyKpiValues()));

        kpiDetailDTO.setStoppedMonthlyKpiValues(toDTO(setMonthlyKpiValuesStatus(stoppedMonthlyKpiValues, periods)));
        kpiDetailDTO.setTotalStopped(kpiDetailDTO.getSumOfMonthlyKpiValues(kpiDetailDTO.getStoppedMonthlyKpiValues()));
        // Applied rule is (Budget - Realised) of each monthly kpi value of a specified year
        //ecarts
        ecarts = getEcart(kpiDetailDTO.getBudgetMonthlyKpiValues(), kpiDetailDTO.getRealisedMonthlyKpiValues());
        kpiDetailDTO.setEcarts(ecarts);
        kpiDetailDTO.setTotalEcarts(kpiDetailDTO.getSumOfMonthlyKpiValues(kpiDetailDTO.getEcarts()));

        return kpiDetailDTO;
    }


    private List<MonthlyKpiValueDTO> getEcart(List<MonthlyKpiValueDTO> budgetValues, List<MonthlyKpiValueDTO> realisedValues) {
        if (budgetValues.size() != 12 || realisedValues.size() != 12) {
            throw new ArrayIndexOutOfBoundsException("Out of Bounds Exc. Size must be 12");
        }
        // check if the same indices are set in the same date
        //percentage = ( part / total ) × 100
        List<MonthlyKpiValueDTO> ecartValues = new ArrayList<>();
        int i = 0;
        while (i < MONTHS_COUNT) {// 12 Months
            MonthlyKpiValueDTO monthlyKpiValue = new MonthlyKpiValueDTO();
            MonthlyKpiValueDTO budgetMonthlyKpiValue = budgetValues.get(i);
            MonthlyKpiValueDTO realisedMonthlyKpiValue = realisedValues.get(i);
            monthlyKpiValue.setLabel(budgetMonthlyKpiValue.getLabel());
            monthlyKpiValue.setDate(budgetMonthlyKpiValue.getDate());
            Double percentage = (budgetMonthlyKpiValue.getValue() - realisedMonthlyKpiValue.getValue()) / 100; // gives 2
            monthlyKpiValue.setValue(percentage);
            monthlyKpiValue.setKpi(budgetMonthlyKpiValue.getKpi());
            ecartValues.add(monthlyKpiValue);
            i++;
        }
        return ecartValues;
    }

    private List<MonthlyKpiValueDTO> toDTO(List<MonthlyKpiValue> list) {
        return list.stream().map(monthlyKpiValueMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kpi : {}", id);
        kpiRepository.deleteById(id);
    }

    @Override
    public Optional<KpiDTO> findByIdAndkpiGroupId(Long kpiId, Long kpiGroupId) {
        log.debug("Request to get Kpi : {}", kpiId);
        return kpiRepository.findByIdAndKpiGroupId(kpiId, kpiGroupId).map(kpiMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Page<KpiDTO> findByCriteria(KpiCriteria criteria, Integer year, HttpHeaders responseHeaders, Pageable page) throws ParseException {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Kpi> specification = createSpecification(criteria);
        Page<Kpi> kpis = kpiRepository.findAll(specification, page);
        System.out.println(" kpi content : " + kpis.getContent());
        if (year != null) {
            LocalDate start = LocalDate.parse(year + "-10-01", formatter);
            LocalDate end = LocalDate.parse((year + 1) + "-11-01", formatter);
            List<PeriodDTO> periods = periodService.findAll();
            kpis.forEach(kpi -> {
                List<MonthlyKpiValue> monthlyKpiValues = monthlyKpiValueRepository.fetchMonthlyKpiValuesByVersion(kpi.getId(), start, end, KpiValueType.Realised);
                if (monthlyKpiValues.size() != MONTHS_COUNT) {
                    monthlyKpiValues = generateEmptyMonthlyKpiValues(kpi, year, monthlyKpiValues, KpiValueType.Realised);
                }
                ;
                kpi.resetMonthlyKpiValues(setMonthlyKpiValuesStatus(monthlyKpiValues, periods));
            });

            kpis.forEach(kpi -> System.out.println(" kpi : " + kpi));
            return kpis.map(kpiMapper::toDto).map(kpiDTO -> {
                kpiDTO.setTotalKpiValues(kpiDTO.getSumOfMonthlyKpiValues(kpiDTO.getMonthlyKpiValues()));
                return kpiDTO;
            });
        }

        System.out.println("probably error happened 2");
        return kpis.map(kpiMapper::toDto);
    }

    private List<MonthlyKpiValue> generateEmptyMonthlyKpiValues(Kpi kpi, Integer year, List<MonthlyKpiValue> monthlyKpiValues, KpiValueType version) {
        List<MonthlyKpiValue> mKpiValues = new ArrayList<>();//ArrayList (Order Maintained)
        LocalDate date = LocalDate.parse(year + "-11-01");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-yy").withLocale(Locale.FRENCH);

        int i = 0;
        while (i < MONTHS_COUNT) {// 12 Months
            MonthlyKpiValue monthlyKpiValue = getMonthtlyKpiValueIfAlreadyExist(date.plusMonths(i), monthlyKpiValues);
            if (monthlyKpiValue != null) {
                if (monthlyKpiValue.getLabel() == null || "".equals(monthlyKpiValue.getLabel())) {
                    monthlyKpiValue.setLabel(formatter.format(date.plusMonths(i)));
                }
                mKpiValues.add(monthlyKpiValue);
            } else {
                mKpiValues.add(new MonthlyKpiValue(formatter.format(date.plusMonths(i)), date.plusMonths(i), 0.0, version, Status.Active, InsertionMethod.MANUALLY, kpi));
            }
            i++;
        }
        mKpiValues.sort(Comparator.comparing(MonthlyKpiValue::getDate));

        return mKpiValues;
    }

    private MonthlyKpiValue getMonthtlyKpiValueIfAlreadyExist(LocalDate month, List<MonthlyKpiValue> monthlyKpiValues) {
        for (MonthlyKpiValue monthlyKpiValue : monthlyKpiValues) {
            boolean areEquals = month.equals(monthlyKpiValue.getDate());
            System.out.println("Dates areEquals " + areEquals);
            if (areEquals) {
                return monthlyKpiValue;
            }
        }
        return null;
    }


    /**
     * Function to convert {@link KpiCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Kpi> createSpecification(KpiCriteria criteria) {
        Specification<Kpi> specification = Specification.where(null);
        if (criteria != null) {

            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Kpi_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Kpi_.name));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), Kpi_.reference));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Kpi_.status));
            }
            if (criteria.getReminder() != null) {
                specification = specification.and(buildSpecification(criteria.getReminder(), Kpi_.reminder));
            }
            if (criteria.getEntiteId() != null) {
                specification =
                        specification.and(
                                buildSpecification(criteria.getEntiteId(), root -> root.join(Kpi_.entite, JoinType.LEFT).get(Entite_.id))
                        );
            }
            if (criteria.getCategoryId() != null) {
                specification =
                        specification.and(
                                buildSpecification(criteria.getCategoryId(), root -> root.join(Kpi_.category, JoinType.LEFT).get(Category_.id))
                        );
            }
            if (criteria.getSystemSourceId() != null) {
                specification =
                        specification.and(
                                buildSpecification(
                                        criteria.getSystemSourceId(),
                                        root -> root.join(Kpi_.systemSources, JoinType.LEFT).get(SystemSource_.id)
                                )
                        );
            }

            if (criteria.getIsInductor() != null) {
                specification = specification.and(buildSpecification(criteria.getIsInductor(), Kpi_.isInductor));

            }
        }
        return specification;
    }


    public ByteArrayInputStream load(List<KpiDTO> kpisDto, Integer year) {
        ByteArrayInputStream in = exportKpisToExcel(kpisDto, year);
        return in;
    }

    public ByteArrayInputStream loadKpiDetailDTO(KpiDetailDTO kpiDetailDTO, Integer year) {
        ByteArrayInputStream in = exportKpiDetailDTOToExcel(kpiDetailDTO, year);
        return in;
    }

    private ByteArrayInputStream exportKpiDetailDTOToExcel(KpiDetailDTO kpiDetailDTO, Integer year) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            Row headerRow1 = sheet.createRow(0);

            for (int col = 0; col < HEADER1_DETAIL.length; col++) {
                Cell cell = headerRow1.createCell(col);
                cell.setCellValue(HEADER1_DETAIL[col]);
            }
            Row row = sheet.createRow(1);
            row.createCell(0).setCellValue(kpiDetailDTO.getReference());
            row.createCell(1).setCellValue(kpiDetailDTO.getName());
            row.createCell(2).setCellValue(kpiDetailDTO.getEntite() != null ? kpiDetailDTO.getEntite().getLabel() : null);
            row.createCell(3).setCellValue("M+" + kpiDetailDTO.getProductionPeriod());


            Row headerRow2 = sheet.createRow(4);

            for (int col = 0; col < HEADER2_DETAIL.length; col++) {
                Cell cell = headerRow2.createCell(col);
                cell.setCellValue(HEADER2_DETAIL[col] + (col < 1 || col == 13 ? "" : "-" + (col < 3 ? year : year + 1)));
            }
            int rowIdx = 5;
            List<MonthlyKpiValueDTO> refYear1 = kpiDetailDTO.getRefYear1();
            if (refYear1 != null && refYear1.size() > 0) {
                Row row1 = sheet.createRow(rowIdx);
                LocalDate localDate = refYear1.get(0).getDate();
                row1.createCell(0).setCellValue(localDate.getYear());

                for (MonthlyKpiValueDTO monthlyKpiValueDTO : refYear1) {

                    LocalDate date = monthlyKpiValueDTO.getDate();
                    int month = date.getMonthValue();
                    System.out.println("Month : " + month);
                    int i = 0;
                    if (month < 11) {
                        i = month + 2;
                    } else if (month == 11) {
                        i = 1;
                    } else if (month == 12) {
                        i = 2;
                    }
                    row1.createCell(i).setCellValue(monthlyKpiValueDTO.getValue());
                }
                row1.createCell(13).setCellValue(kpiDetailDTO.getTotalRefYear1());
                rowIdx++;
            }

            List<MonthlyKpiValueDTO> refYear2 = kpiDetailDTO.getRefYear2();
            if (refYear2 != null && refYear2.size() > 0) {
                Row row1 = sheet.createRow(rowIdx);
                LocalDate localDate = refYear2.get(0).getDate();
                row1.createCell(0).setCellValue(localDate.getYear());

                for (MonthlyKpiValueDTO monthlyKpiValueDTO : refYear1) {

                    LocalDate date = monthlyKpiValueDTO.getDate();
                    int month = date.getMonthValue();
                    System.out.println("Month : " + month);
                    int i = 0;
                    if (month < 11) {
                        i = month + 2;
                    } else if (month == 11) {
                        i = 1;
                    } else if (month == 12) {
                        i = 2;
                    }
                    row1.createCell(i).setCellValue(monthlyKpiValueDTO.getValue());
                }
                row1.createCell(13).setCellValue(kpiDetailDTO.getTotalRefYear2());

                rowIdx++;
            }


            List<MonthlyKpiValueDTO> budgets = kpiDetailDTO.getBudgetMonthlyKpiValues();
            if (budgets != null && budgets.size() > 0) {
                Row row1 = sheet.createRow(rowIdx);
                row1.createCell(0).setCellValue("Budget");

                for (MonthlyKpiValueDTO budget : budgets) {
                    LocalDate date = budget.getDate();
                    int month = date.getMonthValue();
                    System.out.println("Month : " + month);
                    int i = 0;
                    if (month < 11) {
                        i = month + 2;
                    } else if (month == 11) {
                        i = 1;
                    } else if (month == 12) {
                        i = 2;
                    }
                    row1.createCell(i).setCellValue(budget.getValue());
                }
                row1.createCell(13).setCellValue(kpiDetailDTO.getTotalBudget());

                rowIdx++;
            }

            List<MonthlyKpiValueDTO> realised = kpiDetailDTO.getRealisedMonthlyKpiValues();
            if (realised != null && realised.size() > 0) {
                Row row1 = sheet.createRow(rowIdx);
                row1.createCell(0).setCellValue("N (Réalisé)");

                for (MonthlyKpiValueDTO realiz : realised) {
                    LocalDate date = realiz.getDate();
                    int month = date.getMonthValue();
                    System.out.println("Month : " + month);
                    int i = 0;
                    if (month < 11) {
                        i = month + 2;
                    } else if (month == 11) {
                        i = 1;
                    } else if (month == 12) {
                        i = 2;
                    }
                    row1.createCell(i).setCellValue(realiz.getValue());
                }
                row1.createCell(13).setCellValue(kpiDetailDTO.getTotalRealised());

                rowIdx++;
            }


            List<MonthlyKpiValueDTO> stopped = kpiDetailDTO.getStoppedMonthlyKpiValues();
            if (stopped != null && stopped.size() > 0) {
                Row row1 = sheet.createRow(rowIdx);
                row1.createCell(0).setCellValue("N (Arrêté)");

                for (MonthlyKpiValueDTO stop : stopped) {
                    LocalDate date = stop.getDate();
                    int month = date.getMonthValue();
                    System.out.println("Month : " + month);
                    int i = 0;
                    if (month < 11) {
                        i = month + 2;
                    } else if (month == 11) {
                        i = 1;
                    } else if (month == 12) {
                        i = 2;
                    }
                    row1.createCell(i).setCellValue(stop.getValue());
                }
                row1.createCell(13).setCellValue(kpiDetailDTO.getTotalStopped());

                rowIdx++;
            }
            List<MonthlyKpiValueDTO> ecarts = kpiDetailDTO.getEcarts();
            if (ecarts != null && ecarts.size() > 0) {
                Row row1 = sheet.createRow(rowIdx);
                row1.createCell(0).setCellValue("Ecart/Budget");

                for (MonthlyKpiValueDTO ecart : ecarts) {
                    LocalDate date = ecart.getDate();
                    int month = date.getMonthValue();
                    System.out.println("Month : " + month);
                    int i = 0;
                    if (month < 11) {
                        i = month + 2;
                    } else if (month == 11) {
                        i = 1;
                    } else if (month == 12) {
                        i = 2;
                    }
                    row1.createCell(i).setCellValue(ecart.getValue());
                }
                row1.createCell(13).setCellValue(kpiDetailDTO.getTotalEcarts());

                rowIdx++;
            }

            List<MonthlyKpiValueDTO> ecartsYearRef1 = kpiDetailDTO.getEcartsRefYear1();
            if (ecartsYearRef1 != null && ecartsYearRef1.size() > 0) {
                Row row1 = sheet.createRow(rowIdx);
                LocalDate localDate = ecartsYearRef1.get(0).getDate();
                row1.createCell(0).setCellValue("Ecart/" + localDate.getYear());

                for (MonthlyKpiValueDTO ecart : ecartsYearRef1) {
                    LocalDate date = ecart.getDate();
                    int month = date.getMonthValue();
                    System.out.println("Month : " + month);
                    int i = 0;
                    if (month < 11) {
                        i = month + 2;
                    } else if (month == 11) {
                        i = 1;
                    } else if (month == 12) {
                        i = 2;
                    }
                    row1.createCell(i).setCellValue(ecart.getValue());
                }
                row1.createCell(13).setCellValue(kpiDetailDTO.getTotalEcartsRefYear1());

                rowIdx++;
            }

            List<MonthlyKpiValueDTO> ecartsYearRef2 = kpiDetailDTO.getEcartsRefYear2();
            if (ecartsYearRef2 != null && ecartsYearRef2.size() > 0) {
                Row row1 = sheet.createRow(rowIdx);
                LocalDate localDate = ecartsYearRef2.get(0).getDate();
                row1.createCell(0).setCellValue("Ecart/" + localDate.getYear());

                for (MonthlyKpiValueDTO ecart : ecartsYearRef2) {
                    LocalDate date = ecart.getDate();
                    int month = date.getMonthValue();
                    System.out.println("Month : " + month);
                    int i = 0;
                    if (month < 11) {
                        i = month + 2;
                    } else if (month == 11) {
                        i = 1;
                    } else if (month == 12) {
                        i = 2;
                    }
                    row1.createCell(i).setCellValue(ecart.getValue());
                }
                row1.createCell(13).setCellValue(kpiDetailDTO.getTotalEcartsRefYear2());

                rowIdx++;
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }


    public ByteArrayInputStream exportKpisToExcel(List<KpiDTO> kpisDto, Integer year) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col] + (col < 2 || col == 14 ? "" : "-" + (col < 4 ? year : year + 1)));
            }
            int rowIdx = 1;
            if (!kpisDto.isEmpty()) {
                for (KpiDTO kpiDto : kpisDto) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(kpiDto.getName());
                    row.createCell(1).setCellValue(kpiDto.getReference());
                    for (MonthlyKpiValueDTO monthlyKpiValueDTO : kpiDto.getMonthlyKpiValues()) {
                        LocalDate date = monthlyKpiValueDTO.getDate();
                        int month = date.getMonthValue();
                        System.out.println("Month : " + month);
                        int i = 0;
                        if (month < 11) {
                            i = month + 3;
                        } else if (month == 11) {
                            i = 2;
                        } else if (month == 12) {
                            i = 3;
                        }
                        row.createCell(i).setCellValue(monthlyKpiValueDTO.getValue());
                    }
                    row.createCell(14).setCellValue(kpiDto.getTotalKpiValues());
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
