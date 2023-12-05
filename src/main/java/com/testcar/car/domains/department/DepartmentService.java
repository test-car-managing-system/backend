package com.testcar.car.domains.department;

import static com.testcar.car.domains.department.exception.ErrorCode.DUPLICATED_DEPARTMENT_NAME;

import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.department.exception.ErrorCode;
import com.testcar.car.domains.department.model.CreateDepartmentRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    /** 부서를 ID로 조회합니다. */
    public Department findById(Long id) {
        return departmentRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    /** 부서를 전부 조회합니다. */
    public List<Department> findAll() {
        return departmentRepository.findAllByDeletedFalse();
    }

    /** 부서를 생성합니다. */
    public Department create(CreateDepartmentRequest request) {
        this.validateDepartmentNameNotDuplicated(request.getName());
        final Department department = Department.builder().name(request.getName()).build();
        return departmentRepository.save(department);
    }

    /** 부서명의 중복을 검증합니다. */
    private void validateDepartmentNameNotDuplicated(String name) {
        if (departmentRepository.existsByNameAndDeletedFalse(name)) {
            throw new BadRequestException(DUPLICATED_DEPARTMENT_NAME);
        }
    }
}
