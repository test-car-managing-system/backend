package com.testcar.car.domains.member;


import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.department.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 부서
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    // 이메일 아이디
    @Column(length = 50, nullable = false)
    private String email;

    // 비밀번호
    @Column(length = 50, nullable = false)
    private String password;

    // 이름
    @Column(length = 20, nullable = false)
    private String name;

    // 권한
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(Department department, String email, String password, String name, Role role) {
        this.department = department;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Member update(Member member) {
        this.department = member.getDepartment();
        this.email = member.getEmail();
        this.name = member.getName();
        this.role = member.getRole();
        return this;
    }
}
