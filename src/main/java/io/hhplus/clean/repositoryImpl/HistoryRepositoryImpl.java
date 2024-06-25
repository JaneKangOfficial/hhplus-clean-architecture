package io.hhplus.clean.repositoryImpl;

import io.hhplus.clean.entity.History;
import io.hhplus.clean.repository.history.HistoryJpaRepository;
import io.hhplus.clean.repository.history.HistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {

    private final HistoryJpaRepository historyJpaRepository;

    public HistoryRepositoryImpl(HistoryJpaRepository historyJpaRepository) {
        this.historyJpaRepository = historyJpaRepository;
    }

    @Override
    public Optional<History> findByStudentIdAndLectureId(Long studentId, Long lectureId) {
        return historyJpaRepository.findByStudentIdAndLectureId(studentId, lectureId);
    }
}
