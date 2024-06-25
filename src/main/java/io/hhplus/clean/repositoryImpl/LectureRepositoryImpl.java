package io.hhplus.clean.repositoryImpl;

import io.hhplus.clean.entity.Lecture;
import io.hhplus.clean.repository.lecture.LectureJpaRepository;
import io.hhplus.clean.repository.lecture.LectureRepository;

import java.util.Optional;

public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    public LectureRepositoryImpl(LectureJpaRepository lectureJpaRepository) {
        this.lectureJpaRepository = lectureJpaRepository;
    }

    @Override
    public Optional<Lecture> findById(Long lectureId) {
        return lectureJpaRepository.findById(lectureId);
    }
}
