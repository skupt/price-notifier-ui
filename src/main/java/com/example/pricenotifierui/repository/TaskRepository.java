package com.example.pricenotifierui.repository;

import com.example.pricenotifierui.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "select * from task where task_status = :status limit :max", nativeQuery = true)
    List<Task> findAllByTaskStatusLimit(@Param("status") String status, @Param("max") Integer max);

    Optional<Task> findByExternalId(Long externalId);

    Set<Task> findAllByTaskStatus(String taskStatus);
}
