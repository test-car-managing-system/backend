package com.testcar.car.domains.department.repository;


import com.testcar.car.domains.department.entity.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByIdAndDeletedFalse(Long id);

    List<Department> findAllByDeletedFalse();

    boolean existsByNameAndDeletedFalse(String name);
}
