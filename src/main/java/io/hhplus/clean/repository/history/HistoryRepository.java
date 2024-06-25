package io.hhplus.clean.repository.history;

import io.hhplus.clean.entity.History;

import java.util.Optional;

public interface HistoryRepository {
    Optional<History> findByStudentIdAndLectureId(Long studentId, Long lectureId);

}
