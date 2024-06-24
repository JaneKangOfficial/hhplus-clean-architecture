package io.hhplus.clean.repository;

import io.hhplus.clean.entity.LectureRegistered;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface LectureRegisteredRepository extends JpaRepository<LectureRegistered, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureRegistered> findByLectureId(Long lectureId);
}
