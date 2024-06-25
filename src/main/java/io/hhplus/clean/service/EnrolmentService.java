package io.hhplus.clean.service;

import io.hhplus.clean.dto.HistoryDTO;
import io.hhplus.clean.dto.LectureRegisteredDTO;
import io.hhplus.clean.entity.History;
import io.hhplus.clean.entity.Lecture;
import io.hhplus.clean.entity.LectureRegistered;
import io.hhplus.clean.entity.TransactionStatus;
import io.hhplus.clean.repository.history.HistoryJpaRepository;
import io.hhplus.clean.repository.history.HistoryRepository;
import io.hhplus.clean.repository.lecture.LectureRepository;
import io.hhplus.clean.repository.lectureregistered.LectureRegisteredJpaRepository;
import io.hhplus.clean.repository.lectureregistered.LectureRegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EnrolmentService {

    private HistoryRepository historyRepository;
    private HistoryJpaRepository historyJpaRepository;
    private LectureRepository lectureRepository;
    private LectureRegisteredRepository lectureRegisteredRepository;
    private LectureRegisteredJpaRepository lectureRegisteredJpaRepository;

    @Autowired
    public EnrolmentService(HistoryRepository historyRepository, HistoryJpaRepository historyJpaRepository
            , LectureRepository lectureRepository
            , LectureRegisteredRepository lectureRegisteredRepository
            , LectureRegisteredJpaRepository lectureRegisteredJpaRepository) {
        this.historyRepository = historyRepository;
        this.historyJpaRepository = historyJpaRepository;
        this.lectureRepository = lectureRepository;
        this.lectureRegisteredRepository = lectureRegisteredRepository;
        this.lectureRegisteredJpaRepository = lectureRegisteredJpaRepository;
    }

    @Transactional
    public LectureRegisteredDTO apply(HistoryDTO historyDTO) {

        Optional<History> history = historyRepository.findByStudentIdAndLectureId(historyDTO.studentId(), historyDTO.lectureId());
        if (history.isPresent()) {
            throw new IllegalStateException("이미 신청한 특강입니다.");
        }

        Lecture lecture = lectureRepository.findById(historyDTO.lectureId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 특강입니다."));

        LectureRegistered lectureRegistered = lectureRegisteredRepository.findByLectureId(historyDTO.lectureId())
                .orElseGet(() -> {
                    LectureRegistered newLectureRegistered = new LectureRegistered();
                    newLectureRegistered.setId(historyDTO.lectureId());
                    newLectureRegistered.setRegistered(0);
                    return newLectureRegistered;
                });
        if (lectureRegistered.getRegistered() >= 30) {
            throw new IllegalStateException("정원이 초과되었습니다.");
        }

        lectureRegistered.setRegistered(lectureRegistered.getRegistered() + 1);
        lectureRegisteredJpaRepository.save(lectureRegistered);

        History historyEntity = new History();
        historyEntity.setStudentId(historyDTO.studentId());
        historyEntity.setLectureId(historyDTO.lectureId());
        historyEntity.setStatus(TransactionStatus.APPLY);
        historyEntity.setAppliedAt(System.currentTimeMillis());
        historyJpaRepository.save(historyEntity);

        LectureRegisteredDTO resLectureRegisteredDTO = lectureRegisteredRepository.findAllByLectureIdOrderByIdDesc(historyDTO.lectureId());

        return resLectureRegisteredDTO;

    }
}
