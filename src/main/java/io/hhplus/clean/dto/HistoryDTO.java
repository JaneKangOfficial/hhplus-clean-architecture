package io.hhplus.clean.dto;

import io.hhplus.clean.entity.TransactionStatus;

public record HistoryDTO(

        Long id,

        Long studentId,

        Long lectureId,

        TransactionStatus status,

        Long appliedAt
) {
}