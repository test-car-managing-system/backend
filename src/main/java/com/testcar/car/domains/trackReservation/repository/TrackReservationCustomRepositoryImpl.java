package com.testcar.car.domains.trackReservation.repository;

import static com.testcar.car.domains.track.QTrack.track;
import static com.testcar.car.domains.trackReservation.entity.QTrackReservation.trackReservation;
import static com.testcar.car.domains.trackReservation.entity.QTrackReservationSlot.trackReservationSlot;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.trackReservation.entity.ReservationStatus;
import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.model.vo.TrackReservationFilterCondition;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrackReservationCustomRepositoryImpl
        implements TrackReservationCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<TrackReservation> findByMemberIdAndId(Long memberId, Long trackReservationId) {
        final TrackReservation result =
                jpaQueryFactory
                        .selectFrom(trackReservation)
                        .leftJoin(trackReservation.track, track)
                        .fetchJoin()
                        .leftJoin(trackReservation.trackReservationSlots, trackReservationSlot)
                        .fetchJoin()
                        .where(
                                notDeleted(trackReservation),
                                trackReservation.member.id.eq(memberId),
                                trackReservation.id.eq(trackReservationId))
                        .fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public List<TrackReservation> findAllByMemberIdAndCondition(
            Long memberId, TrackReservationFilterCondition condition) {
        return jpaQueryFactory
                .selectFrom(trackReservation)
                .leftJoin(trackReservation.track, track)
                .fetchJoin()
                .where(
                        notDeleted(trackReservation),
                        trackReservation.member.id.eq(memberId),
                        trackNameContainsOrNull(condition.getName()),
                        createdAtEqOrNull(condition.getCreatedAt()),
                        reservationStatusEqOrNull(condition.getStatus()))
                .orderBy(orderByReservationStatus(), trackReservation.createdAt.desc())
                .fetch();
    }

    private BooleanExpression trackNameContainsOrNull(String name) {
        return (name == null) ? null : track.name.contains(name);
    }

    private BooleanExpression createdAtEqOrNull(LocalDate date) {
        final LocalDateTime startTime = date.atStartOfDay();
        final LocalDateTime tomorrowStartTime = date.plusDays(1).atStartOfDay();
        return (date == null)
                ? null
                : track.createdAt.goe(startTime).and(track.createdAt.lt(tomorrowStartTime));
    }

    private BooleanExpression reservationStatusEqOrNull(ReservationStatus status) {
        return (status == null) ? null : trackReservation.status.eq(status);
    }

    private OrderSpecifier<?> orderByReservationStatus() {
        return trackReservation
                .status
                .when(ReservationStatus.RESERVED)
                .then(1)
                .when(ReservationStatus.USING)
                .then(2)
                .when(ReservationStatus.CANCELED)
                .then(3)
                .when(ReservationStatus.COMPLETED)
                .then(4)
                .otherwise(5)
                .asc();
    }
}
