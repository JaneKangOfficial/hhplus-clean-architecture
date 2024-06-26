package io.hhplus.clean.service;

import io.hhplus.clean.dto.HistoryDTO;
import io.hhplus.clean.dto.LectureDTO;
import io.hhplus.clean.dto.LectureRegisteredDTO;

import java.util.List;

public interface EnrolmentService {

    LectureRegisteredDTO apply(HistoryDTO historyDTO);

    List<LectureDTO> list();
}
