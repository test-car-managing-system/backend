package com.testcar.car.domains.department;


import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.domains.department.entity.Department;
import com.testcar.car.domains.department.model.CreateDepartmentRequest;
import com.testcar.car.domains.department.model.DepartmentResponse;
import com.testcar.car.domains.member.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[부서 관리] ", description = "부서 관리 API")
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    @RoleAllowed(role = Role.USER)
    @Operation(summary = "[부서 관리] 부서 조회", description = "전체 부서를 조회합니다.")
    public List<DepartmentResponse> findAll() {
        final List<Department> departments = departmentService.findAll();
        return departments.stream().map(DepartmentResponse::from).toList();
    }

    @PostMapping
    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[부서 등록] 부서 생성", description = "(관리자) 새로운 부서를 생성합니다.")
    public DepartmentResponse create(@Valid @RequestBody CreateDepartmentRequest request) {
        final Department department = departmentService.create(request);
        return DepartmentResponse.from(department);
    }
}
