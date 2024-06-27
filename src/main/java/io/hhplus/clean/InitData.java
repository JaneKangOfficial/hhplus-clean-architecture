package io.hhplus.clean;

import io.hhplus.clean.entity.History;
import io.hhplus.clean.entity.Lecture;
import io.hhplus.clean.entity.Schedule;
import io.hhplus.clean.repository.history.HistoryJpaRepository;
import io.hhplus.clean.repository.lecture.LectureJpaRepository;
import io.hhplus.clean.repository.schedule.ScheduleJpaRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InitData implements ApplicationRunner {

    private final LectureJpaRepository lectureJpaRepository;
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final HistoryJpaRepository historyJpaRepository;

    public InitData(LectureJpaRepository lectureJpaRepository, ScheduleJpaRepository scheduleJpaRepository, HistoryJpaRepository historyJpaRepository) {
        this.lectureJpaRepository = lectureJpaRepository;
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.historyJpaRepository = historyJpaRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 초기 데이터 생성 - 특강 테이블
        Lecture lecture1 = new Lecture();
        lecture1.setTitle("TDD");
        lecture1.setTeacherId(1L);
        lectureJpaRepository.save(lecture1);

        Lecture lecture2 = new Lecture();
        lecture2.setTitle("Clean Architecture");
        lecture2.setTeacherId(2L);
        lectureJpaRepository.save(lecture2);

        // 초기 데이터 생성 - 일정 테이블
        Schedule schedule1 = new Schedule();
        schedule1.setCapacity(30);
        schedule1.setLectureId(1L);
        schedule1.setStartAt(LocalDate.now());
        scheduleJpaRepository.save(schedule1);

        Schedule schedule2 = new Schedule();
        schedule2.setCapacity(30);
        schedule2.setLectureId(2L);
        schedule2.setStartAt(LocalDate.now());
        scheduleJpaRepository.save(schedule2);

        // 초기 데이터 생성 - 결과 테이블
        History history1 = new History();
        schedule1.setCapacity(30);
        schedule1.setLectureId(1L);
        schedule1.setStartAt(LocalDate.now());
        scheduleJpaRepository.save(schedule1);

    }


}
