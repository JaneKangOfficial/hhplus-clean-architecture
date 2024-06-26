package io.hhplus.clean.repository.lecture;

import io.hhplus.clean.entity.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {
    Optional<Lecture> findById(Long lectureId);

    List<Lecture> findAll();
}
