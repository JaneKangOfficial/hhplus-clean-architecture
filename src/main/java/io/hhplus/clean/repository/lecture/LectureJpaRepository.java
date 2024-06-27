package io.hhplus.clean.repository.lecture;

import io.hhplus.clean.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

    Optional<Lecture> findById(Long lectureId);

    @Query("SELECT l, s FROM Lecture l LEFT JOIN Schedule s ON l.id = s.lectureId")
    List<Lecture> findAll();
}
