package com.testcar.car.domains.department;


import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.department.exception.ErrorCode;
import com.testcar.car.domains.department.model.CreateDepartmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Department findById(Long id) {
        return departmentRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    public Department create(CreateDepartmentRequest request) {
        final Department department = Department.builder().name(request.getName()).build();
        return departmentRepository.save(department);
    }
}
