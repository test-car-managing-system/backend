package com.testcar.car.domains.gasStation;


import com.testcar.car.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "GasStation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GasStation extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    @Column(nullable = false, length = 50)
    private String name;

    @Builder
    public GasStation(String name) {
        this.name = name;
    }
}
