package io.hhplus.clean.repositoryImpl;

import io.hhplus.clean.dto.LectureRegisteredDTO;
import io.hhplus.clean.entity.LectureRegistered;
import io.hhplus.clean.repository.lectureregistered.LectureRegisteredJpaRepository;
import io.hhplus.clean.repository.lectureregistered.LectureRegisteredRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LectureRegisteredRepositoryImpl implements LectureRegisteredRepository {

    private final LectureRegisteredJpaRepository lectureRegisteredJpaRepository;

    public LectureRegisteredRepositoryImpl(LectureRegisteredJpaRepository lectureRegisteredJpaRepository) {
        this.lectureRegisteredJpaRepository = lectureRegisteredJpaRepository;
    }

    @Override
    public Optional<LectureRegistered> findByScheduleId(Long scheduleId) {
        return lectureRegisteredJpaRepository.findByScheduleId(scheduleId);
    }

    @Override
    public LectureRegisteredDTO findAllByScheduleIdOrderByIdDesc(Long scheduleId) {
        return lectureRegisteredJpaRepository.findAllByScheduleIdOrderByIdDesc(scheduleId);
    }
}
