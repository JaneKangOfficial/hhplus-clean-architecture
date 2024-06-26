package io.hhplus.clean.repositoryImpl;

import io.hhplus.clean.entity.Lecture;
import io.hhplus.clean.repository.lecture.LectureJpaRepository;
import io.hhplus.clean.repository.lecture.LectureRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    public LectureRepositoryImpl(LectureJpaRepository lectureJpaRepository) {
        this.lectureJpaRepository = lectureJpaRepository;
    }

    @Override
    public Optional<Lecture> findById(Long lectureId) {
        return lectureJpaRepository.findById(lectureId);
    }

    @Override
    public List<Lecture> findAll() {
        return lectureJpaRepository.findAll();
    }
}
