package io.hhplus.clean.repository.history;

import io.hhplus.clean.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryJpaRepository extends JpaRepository<History, Long> {
    Optional<History> findByStudentIdAndLectureId(Long studentId, Long lectureId);

}
