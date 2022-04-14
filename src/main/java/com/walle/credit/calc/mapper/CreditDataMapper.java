package com.walle.credit.calc.mapper;

import com.walle.credit.calc.dto.CreditDataInputDto;
import com.walle.credit.calc.dto.CreditDataResponseDto;
import com.walle.credit.calc.model.CreditData;
import static com.walle.credit.calc.util.CalcConstants.ROUND_MODE;
import static com.walle.credit.calc.util.CalcConstants.SCALE_2;
import java.math.BigDecimal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CreditDataMapper {

    List<CreditDataResponseDto> toDtoList(List<CreditData> allCalculations);

    CreditDataResponseDto toDto(CreditData creditData);

    @Mapping(source = "apartmentCost", target = "apartmentCost", qualifiedByName = "stringToBigDecimal")
    @Mapping(source = "percentage", target = "percentage", qualifiedByName = "stringToBigDecimal")
    @Mapping(source = "userOwnPayment", target = "userOwnPayment", qualifiedByName = "stringToBigDecimal")
    CreditData toEntity(CreditDataInputDto creditDataInputDto);

    @Named("stringToBigDecimal")
    default BigDecimal stringToBigDecimal(String number) {
        return new BigDecimal(number).setScale(SCALE_2, ROUND_MODE);
    }

}
