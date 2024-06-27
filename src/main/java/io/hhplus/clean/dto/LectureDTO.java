package io.hhplus.clean.dto;

import io.hhplus.clean.entity.Lecture;

public record LectureDTO(
        Long id,

        String title,   // 강의명

        Long teacherId
) {
    public LectureDTO(Lecture lecture) {
        this(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getTeacherId()
        );
    }
}
