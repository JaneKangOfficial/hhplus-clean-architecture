package io.hhplus.clean.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class History {      // 신청 내역 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "student_id")
    private Long studentId;

    private Long lectureId;

    private TransactionStatus status;   // 신청, 취소 상태

    private Long appliedAt;

}
