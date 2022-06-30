package ma.itroad.ram.kpi.service.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class KpiRequestValidationErrorsDTO {

   private  Long controlRuleNumber;
   private String kpiName;
   private String errorMessage;
   private LocalDate period;

}
