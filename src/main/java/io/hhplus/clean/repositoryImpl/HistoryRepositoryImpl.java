package io.hhplus.clean.repositoryImpl;

import io.hhplus.clean.entity.History;
import io.hhplus.clean.repository.history.HistoryJpaRepository;
import io.hhplus.clean.repository.history.HistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {

    private final HistoryJpaRepository historyJpaRepository;

    public HistoryRepositoryImpl(HistoryJpaRepository historyJpaRepository) {
        this.historyJpaRepository = historyJpaRepository;
    }

    @Override
    public Optional<History> findByStudentIdAndScheduleId(Long studentId, Long schduleId) {
        return historyJpaRepository.findByStudentIdAndScheduleId(studentId, schduleId);
    }

    @Override
    public List<History> findByStudentId(Long studentId) {
        return historyJpaRepository.findByStudentId(studentId);
    }


}
