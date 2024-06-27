package io.hhplus.clean;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.clean.controller.EnrolmentController;
import io.hhplus.clean.dto.HistoryDTO;
import io.hhplus.clean.dto.LectureDTO;
import io.hhplus.clean.dto.LectureRegisteredDTO;
import io.hhplus.clean.entity.TransactionStatus;
import io.hhplus.clean.service.EnrolmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrolmentController.class)
public class EnrolmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnrolmentService enrolmentService;

    @Test
    @DisplayName("특강 신청 테스트")
    public void applyTest() throws Exception {
        // given
        Long studentId = 1L;
        Long scheduleId = 2L;
        HistoryDTO historyDTO = new HistoryDTO(1L, studentId, scheduleId, TransactionStatus.APPLY, LocalDate.now());

        // 반환될 Mock LectureRegisteredDTO 설정
        LectureRegisteredDTO lectureRegisteredDTO = new LectureRegisteredDTO(1L, scheduleId, 3, LocalDate.now());

        given(enrolmentService.apply(historyDTO)).willReturn(lectureRegisteredDTO);

        // 동시성 테스트를 위한 스레드 풀 생성
        int threadCount = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when & then
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    System.out.println(finalI + "번째 접근 시작");
                    mockMvc.perform(MockMvcRequestBuilders.post("/apply")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(historyDTO)))
                            .andExpect(status().isOk())
                            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(lectureRegisteredDTO.id()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.scheduleId").value(lectureRegisteredDTO.scheduleId()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.registered").value(lectureRegisteredDTO.registered()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                    System.out.println(finalI + "번째 접근 종료");

                }
            });
        }

        countDownLatch.await(10, TimeUnit.SECONDS);

        executorService.shutdown();

    }

    @Test
    @DisplayName("특강 목록 테스트")
    public void listTest() throws Exception {

        // given
        // 반환될 Mock List<LectureDTO> 설정
        LectureDTO lecture1 = new LectureDTO(1L, "TDD", 1L);
        LectureDTO lecture2 = new LectureDTO(2L, "CLEAN", 2L);
        List<LectureDTO> lecture = Arrays.asList(lecture1, lecture2);

        when(enrolmentService.list()).thenReturn(lecture);  // thenReturn(lecture) 반환값 주입

        // when & then
        MvcResult rst = mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = rst.getResponse().getContentAsString();
        System.out.println("content = " + content);
    }

    @Test
    @DisplayName("특강 신청 결과")
    public void statusTest() throws Exception {

        // given
        Long studentId = 1L;

        HistoryDTO history1 = new HistoryDTO(1L, studentId, 1L, TransactionStatus.APPLY, LocalDate.now());
        HistoryDTO history2 = new HistoryDTO(2L, studentId, 2L, TransactionStatus.APPLY, LocalDate.now());
        List<HistoryDTO> history = Arrays.asList(history1, history2);

        when(enrolmentService.status(studentId)).thenReturn(history);

        // when & then
        MvcResult rst = mockMvc.perform(MockMvcRequestBuilders.get("/application/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = rst.getResponse().getContentAsString();
        System.out.println("response = " + response);
    }

}
