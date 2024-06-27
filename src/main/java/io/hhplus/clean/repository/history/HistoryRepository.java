package io.hhplus.clean.repository.history;

import io.hhplus.clean.entity.History;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository {
    Optional<History> findByStudentIdAndScheduleId(Long studentId, Long schduleId);

    List<History> findByStudentId(Long studentId);
}
