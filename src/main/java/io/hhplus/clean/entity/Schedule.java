package io.hhplus.clean.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Schedule {     // 특강 일정 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long lectureId;

    private int capacity; // 정원

    private Long startAt; // 시작일
}
