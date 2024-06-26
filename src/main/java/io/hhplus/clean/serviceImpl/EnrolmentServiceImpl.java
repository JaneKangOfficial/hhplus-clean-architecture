package io.hhplus.clean.serviceImpl;

import io.hhplus.clean.dto.HistoryDTO;
import io.hhplus.clean.dto.LectureDTO;
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
import io.hhplus.clean.service.EnrolmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrolmentServiceImpl implements EnrolmentService {

    private final HistoryRepository historyRepository;
    private final HistoryJpaRepository historyJpaRepository;
    private final LectureRepository lectureRepository;
    private final LectureRegisteredRepository lectureRegisteredRepository;
    private final LectureRegisteredJpaRepository lectureRegisteredJpaRepository;

    @Autowired
    public EnrolmentServiceImpl(HistoryRepository historyRepository, HistoryJpaRepository historyJpaRepository
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

        lectureRegistered.setRegistered(lectureRegistered.getRegistered() + 1); // 현재 신청자 수 + 1
        lectureRegisteredJpaRepository.save(lectureRegistered);

        History historyEntity = new History();
        historyEntity.setStudentId(historyDTO.studentId());
        historyEntity.setLectureId(historyDTO.lectureId());
        historyEntity.setStatus(TransactionStatus.APPLY);
        historyEntity.setAppliedAt(System.currentTimeMillis());
        historyJpaRepository.save(historyEntity);   // 신청 내역 저장

        LectureRegisteredDTO resLectureRegisteredDTO = lectureRegisteredRepository.findAllByLectureIdOrderByIdDesc(historyDTO.lectureId());

        return resLectureRegisteredDTO;

    }

    @Transactional
    public List<LectureDTO> list() {

        List<Lecture> lectureList = lectureRepository.findAll();

        // Lecture 객체를 LectureDTO 객체로 변환
        List<LectureDTO> lectureDTOList = lectureList.stream()
                .map(lecture -> new LectureDTO(lecture))
                .collect(Collectors.toList());

        return lectureDTOList;


    }
}
