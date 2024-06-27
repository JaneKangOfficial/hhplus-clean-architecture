package io.hhplus.clean.dto;

import java.time.LocalDate;

public record LectureRegisteredDTO(
        Long id,

        Long scheduleId,

        int registered, // 등록 학생 수

        LocalDate createdAt // 등록일
) {
}
