package com.testcar.car.domains.trackReservation.entity;


import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.track.Track;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TrackReservationSlot")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackReservationSlot extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 시험장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trackId", nullable = false)
    private Track track;

    // 시험장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trackReservationId", nullable = false)
    private TrackReservation trackReservation;

    // 예약 시작 일시
    @Column(nullable = false)
    private LocalDateTime startedAt;

    // 예약 종료 일시
    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Builder
    public TrackReservationSlot(
            Track track,
            TrackReservation trackReservation,
            LocalDateTime startedAt,
            LocalDateTime expiredAt) {
        this.track = track;
        this.trackReservation = trackReservation;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
    }
}
