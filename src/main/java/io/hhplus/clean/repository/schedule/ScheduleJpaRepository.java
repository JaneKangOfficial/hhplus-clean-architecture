package io.hhplus.clean.repository.schedule;

import io.hhplus.clean.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {
}
