package io.hhplus.clean.repository.history;

import io.hhplus.clean.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryJpaRepository extends JpaRepository<History, Long> {
    Optional<History> findByStudentIdAndScheduleId(Long studentId, Long scheduleId);

    List<History> findByStudentId(Long studentId);
}
