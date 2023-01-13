package com.walle.credit.calc.mapper;

import com.walle.credit.calc.dto.BankDto;
import com.walle.credit.calc.model.Bank;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface BankMapper {

    BankMapper INSTANCE = Mappers.getMapper(BankMapper.class);

    List<BankDto> toDtoList(List<Bank> banks);

    BankDto toDto(Bank bank);

    Bank toEntity(BankDto bankDto);
}
