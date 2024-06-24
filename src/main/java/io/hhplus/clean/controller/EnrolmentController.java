package io.hhplus.clean.controller;

import io.hhplus.clean.dto.HistoryDTO;
import io.hhplus.clean.service.EnrolmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrolmentController {

    private final EnrolmentService enrolmentService;

    public EnrolmentController(EnrolmentService enrolmentService) {
        this.enrolmentService = enrolmentService;
    }

    @PostMapping("/lectures/apply")
    public HistoryDTO apply(@RequestBody HistoryDTO historyDTO) {

        HistoryDTO resHistoryDTO = enrolmentService.apply(historyDTO);
        return resHistoryDTO;
    }

}
