package com.testcar.car.domains.track;


import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Track")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Track extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 시험장명
    @Column(length = 50, nullable = false)
    private String name;

    // 위치(주소)
    @Column(nullable = false)
    private String location;

    // 위도
    @Column private Double latitude;

    // 경도
    @Column private Double longitude;

    // 시험장 특성
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // 시험장 길이
    @Column(nullable = false)
    private Double length;

    @OneToMany(mappedBy = "track")
    private Set<TrackReservationSlot> trackReservationSlots = new HashSet<>();

    @Builder
    public Track(String name, String location, String description, Double length) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.length = length;
    }

    public void update(Track track) {
        this.name = track.getName();
        this.location = track.getLocation();
        this.description = track.getDescription();
        this.length = track.getLength();
    }
}
