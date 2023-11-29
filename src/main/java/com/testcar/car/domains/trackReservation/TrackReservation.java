package com.testcar.car.domains.trackReservation;

import com.testcar.car.common.entity.BaseEntity;
import com.testcar.car.domains.car.Car;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.track.Track;
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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TrackReservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackReservation extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    // 시험장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trackId", nullable = false)
    private Track track;

    // 예약일시
    @Column(nullable = false)
    private LocalDateTime reservedAt;

    // 예약상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Builder
    public TrackReservation(Member member, Track track, LocalDateTime reservedAt, ReservationStatus status) {
        this.member = member;
        this.track = track;
        this.reservedAt = reservedAt;
        this.status = status;
    }
}
