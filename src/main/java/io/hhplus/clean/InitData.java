package io.hhplus.clean;

import io.hhplus.clean.entity.Lecture;
import io.hhplus.clean.repository.lecture.LectureJpaRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements ApplicationRunner {

    private final LectureJpaRepository lectureJpaRepository;

    public InitData(LectureJpaRepository lectureJpaRepository) {
        this.lectureJpaRepository = lectureJpaRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 초기 데이터 생성
        Lecture lecture1 = new Lecture();
        lecture1.setTitle("TDD");
        lecture1.setCapacity(30);
        lecture1.setStartAt(System.currentTimeMillis());
        lectureJpaRepository.save(lecture1);

        Lecture lecture2 = new Lecture();
        lecture2.setTitle("Clean Architecture");
        lecture2.setCapacity(30);
        lecture2.setStartAt(System.currentTimeMillis());
        lectureJpaRepository.save(lecture2);

    }


}
