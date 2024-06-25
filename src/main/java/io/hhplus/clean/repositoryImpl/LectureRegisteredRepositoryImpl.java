package io.hhplus.clean.repositoryImpl;

import io.hhplus.clean.dto.LectureRegisteredDTO;
import io.hhplus.clean.entity.LectureRegistered;
import io.hhplus.clean.repository.lectureregistered.LectureRegisteredJpaRepository;
import io.hhplus.clean.repository.lectureregistered.LectureRegisteredRepository;

import java.util.Optional;

public class LectureRegisteredRepositoryImpl implements LectureRegisteredRepository {

    private final LectureRegisteredJpaRepository lectureRegisteredJpaRepository;

    public LectureRegisteredRepositoryImpl(LectureRegisteredJpaRepository lectureRegisteredJpaRepository) {
        this.lectureRegisteredJpaRepository = lectureRegisteredJpaRepository;
    }

    @Override
    public Optional<LectureRegistered> findByLectureId(Long lectureId) {
        return lectureRegisteredJpaRepository.findByLectureId(lectureId);
    }

    @Override
    public LectureRegisteredDTO findAllByLectureIdOrderByIdDesc(Long lectureId) {
        return lectureRegisteredJpaRepository.findAllByLectureIdOrderByIdDesc(lectureId);
    }
}
