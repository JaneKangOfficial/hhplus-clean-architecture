package io.hhplus.clean.dto;

public record LectureRegisteredDTO(
        Long id,

        Long lectureId,

        int registered, // 등록 학생 수

        Long createdAt // 등록일
) {
}
