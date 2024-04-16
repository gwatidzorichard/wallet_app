package io.basaltx.walletapp.mapper;

import io.basaltx.walletapp.dto.TransactionDTO;
import io.basaltx.walletapp.model.Transaction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    Transaction toEntity(TransactionDTO transactionDTO);

    @Mapping(source = "senderAccount.id", target = "senderAccountId")
    @Mapping(source = "recipientAccount.id", target = "recipientAccountId")
    TransactionDTO toDto(Transaction transaction);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction partialUpdate(TransactionDTO transactionDTO, @MappingTarget Transaction transaction);
}
