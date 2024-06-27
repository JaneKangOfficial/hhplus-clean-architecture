package io.hhplus.clean.repository.lectureregistered;

import io.hhplus.clean.dto.LectureRegisteredDTO;
import io.hhplus.clean.entity.LectureRegistered;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface LectureRegisteredJpaRepository extends JpaRepository<LectureRegistered, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureRegistered> findByScheduleId(Long scheduleId);

    LectureRegisteredDTO findAllByScheduleIdOrderByIdDesc(Long scheduleId);
}
