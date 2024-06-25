package io.hhplus.clean.repository.lecture;

import io.hhplus.clean.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

    Optional<Lecture> findById(Long lectureId);
}
