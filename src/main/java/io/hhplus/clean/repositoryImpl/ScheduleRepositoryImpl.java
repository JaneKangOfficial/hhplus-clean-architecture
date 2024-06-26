package io.hhplus.clean.repositoryImpl;

import io.hhplus.clean.repository.schedule.ScheduleJpaRepository;
import io.hhplus.clean.repository.schedule.ScheduleRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    public ScheduleRepositoryImpl(ScheduleJpaRepository scheduleJpaRepository) {
        this.scheduleJpaRepository = scheduleJpaRepository;
    }
}
