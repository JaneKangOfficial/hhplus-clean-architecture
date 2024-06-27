package io.hhplus.clean.repository.lectureregistered;

import io.hhplus.clean.dto.LectureRegisteredDTO;
import io.hhplus.clean.entity.LectureRegistered;

import java.util.Optional;

public interface LectureRegisteredRepository {

    Optional<LectureRegistered> findByScheduleId(Long scheduleId);

    LectureRegisteredDTO findAllByScheduleIdOrderByIdDesc(Long scheduleId);
}
