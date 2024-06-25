package io.hhplus.clean.repository.lecture;

import io.hhplus.clean.entity.Lecture;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureRepository {
    Optional<Lecture> findById(Long lectureId);

}
