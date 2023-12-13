package com.testcar.car.domains.department;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.department.entity.Department;
import com.testcar.car.domains.department.model.CreateDepartmentRequest;
import com.testcar.car.domains.department.repository.DepartmentRepository;
import com.testcar.car.domains.department.request.DepartmentRequestFactory;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock private DepartmentRepository departmentRepository;
    @InjectMocks private DepartmentService departmentService;

    private static Department department;
    private static final Long departmentId = 1L;

    @BeforeAll
    public static void setUp() {
        department = MemberEntityFactory.createDepartment();
    }

    @Test
    void 부서ID로_DB에서_부서_엔티티를_가져온다() {
        // given
        when(departmentRepository.findByIdAndDeletedFalse(departmentId))
                .thenReturn(Optional.of(department));

        // when
        final Department result = departmentService.findById(departmentId);

        // then
        assertNotNull(result);
        assertEquals(department, result);
        then(departmentRepository).should().findByIdAndDeletedFalse(departmentId);
    }

    @Test
    void 부서_ID에_해당하는_정보가_DB에_존재하지_않으면_오류가_발생한다() {
        // given
        when(departmentRepository.findByIdAndDeletedFalse(departmentId))
                .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    departmentService.findById(departmentId);
                });
        then(departmentRepository).should().findByIdAndDeletedFalse(departmentId);
    }

    @Test
    void 새로운_부서를_생성한다() {
        // given
        final CreateDepartmentRequest request =
                DepartmentRequestFactory.createDepartmentRequestFactory();
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(departmentRepository.existsByNameAndDeletedFalse(request.getName())).thenReturn(false);

        // when
        final Department result = departmentService.create(request);

        // then
        assertNotNull(result);
        assertEquals(department, result);
        then(departmentRepository).should().save(any(Department.class));
        then(departmentRepository).should().existsByNameAndDeletedFalse(request.getName());
    }

    @Test
    void 부서명이_중복되면_오류가_발생한다() {
        // given
        final CreateDepartmentRequest request =
                DepartmentRequestFactory.createDepartmentRequestFactory();
        when(departmentRepository.existsByNameAndDeletedFalse(request.getName())).thenReturn(true);

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    departmentService.create(request);
                });
        then(departmentRepository).should().existsByNameAndDeletedFalse(request.getName());
        then(departmentRepository).shouldHaveNoMoreInteractions();
    }
}
