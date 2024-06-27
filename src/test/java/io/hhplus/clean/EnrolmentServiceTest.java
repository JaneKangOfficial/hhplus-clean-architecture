package io.hhplus.clean;

import io.hhplus.clean.dto.HistoryDTO;
import io.hhplus.clean.dto.LectureDTO;
import io.hhplus.clean.entity.History;
import io.hhplus.clean.entity.Lecture;
import io.hhplus.clean.repository.history.HistoryRepository;
import io.hhplus.clean.repository.lecture.LectureRepository;
import io.hhplus.clean.repository.lectureregistered.LectureRegisteredRepository;
import io.hhplus.clean.serviceImpl.EnrolmentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static io.hhplus.clean.entity.TransactionStatus.APPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnrolmentServiceTest {

    @InjectMocks
    EnrolmentServiceImpl enrolmentServiceImpl;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private LectureRegisteredRepository lectureRegisteredRepository;

    @Test
    @DisplayName("이미 신청한 특강 테스트")
    public void applyTest() throws Exception {

        // given
        HistoryDTO historyDTO = new HistoryDTO(1L, 1L, 1L, APPLY, LocalDate.now());
        when(historyRepository.findByStudentIdAndScheduleId(1L, 1L))
                .thenThrow(new IllegalStateException("이미 신청한 특강입니다."));     // 반환값으로 IllegalStateException 주입

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            enrolmentServiceImpl.apply(historyDTO);
        });

        assertEquals("이미 신청한 특강입니다.", exception.getMessage());  // 같은지 검증

    }

    @Test
    @DisplayName("특강 목록 테스트")
    public void listTest() throws Exception {

        // given
        Lecture lecture1 = new Lecture();
        lecture1.setId(1L);
        lecture1.setTitle("TDD");
        lecture1.setTeacherId(1L);

        Lecture lecture2 = new Lecture();
        lecture2.setId(2L);
        lecture2.setTitle("CLEAN");
        lecture2.setTeacherId(2L);

        List<Lecture> lecture = Arrays.asList(lecture1, lecture2);

        when(lectureRepository.findAll()).thenReturn(lecture);

        // when
        List<LectureDTO> lectureDTOList = enrolmentServiceImpl.list();

        // then
        assertEquals(2, lectureDTOList.size());
        assertEquals("TDD", lectureDTOList.get(0).title());
        assertEquals("CLEAN", lectureDTOList.get(1).title());

    }

    @Test
    @DisplayName("특강 결과 테스트")
    public void statusTest() throws Exception {

        // given
        Long studentId = 1L;

        History history1 = new History();
        history1.setId(1L);
        history1.setStudentId(studentId);
        history1.setScheduleId(1L);
        history1.setStatus(APPLY);
        history1.setAppliedAt(LocalDate.now());

        History history2 = new History();
        history2.setId(2L);
        history2.setStudentId(studentId);
        history2.setScheduleId(2L);
        history2.setStatus(APPLY);
        history2.setAppliedAt(LocalDate.now());

        List<History> history = Arrays.asList(history1, history2);

        when(historyRepository.findByStudentId(studentId)).thenReturn(history);

        // when
        List<HistoryDTO> historyDTO = enrolmentServiceImpl.status(studentId);

        // then
        assertEquals(2, historyDTO.size());
        assertEquals(APPLY, historyDTO.get(0).status());
        assertEquals(APPLY, historyDTO.get(1).status());

    }

}
