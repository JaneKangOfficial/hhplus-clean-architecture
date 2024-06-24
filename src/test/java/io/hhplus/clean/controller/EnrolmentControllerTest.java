package io.hhplus.clean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.clean.dto.HistoryDTO;
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

import static org.mockito.Mockito.when;

@WebMvcTest(EnrolmentController.class)
public class EnrolmentControllerTest {

    @Autowired
    MockMvc mockMvc;

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

        // when & then
        when(enrolmentService.apply(historyDTO))
                .thenReturn(historyDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/lectures/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(historyDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(historyDTO.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentId").value(historyDTO.studentId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lectureId").value(historyDTO.lectureId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(historyDTO.status().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.appliedAt").isNotEmpty());

    }


}
