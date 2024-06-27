package io.hhplus.clean.dto;

import io.hhplus.clean.entity.History;
import io.hhplus.clean.entity.TransactionStatus;

import java.time.LocalDate;

public record HistoryDTO(

        Long id,

        Long studentId,

        Long scheduleId,

        TransactionStatus status,

        LocalDate appliedAt
) {
    public HistoryDTO(History history) {
        this(
                history.getId(),
                history.getStudentId(),
                history.getScheduleId(),
                history.getStatus(),
                history.getAppliedAt()
        );
    }
}