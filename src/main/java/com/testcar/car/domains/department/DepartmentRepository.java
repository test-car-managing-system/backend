package com.testcar.car.domains.department;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByIdAndDeletedFalse(Long id);
}
