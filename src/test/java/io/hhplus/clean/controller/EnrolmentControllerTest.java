package io.hhplus.clean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.clean.dto.HistoryDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;

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
        Long lecturerId = 2L;
        HistoryDTO historyDTO = new HistoryDTO(1L, studentId, lecturerId, TransactionStatus.APPLY, System.currentTimeMillis());

        // 반환될 Mock LectureRegisteredDTO 설정
        LectureRegisteredDTO lectureRegisteredDTO = new LectureRegisteredDTO(1L, lecturerId, 30, System.currentTimeMillis());

        // when & then
        when(enrolmentService.apply(historyDTO))
                .thenReturn(lectureRegisteredDTO);

        // 동시성 테스트를 위한 스레드 풀 생성
        int threadCount = 30;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    mockMvc.perform(MockMvcRequestBuilders.post("/lectures/apply")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(historyDTO)))
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(lectureRegisteredDTO.id()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.lectureId").value(lectureRegisteredDTO.lectureId()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.registered").value(lectureRegisteredDTO.registered()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await(10, TimeUnit.SECONDS);

        executorService.shutdown();

    }

}
