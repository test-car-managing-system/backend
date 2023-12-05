package com.testcar.car.domains.trackReservation.repository;

import static com.testcar.car.domains.trackReservation.QTrackReservationSlot.trackReservationSlot;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.trackReservation.TrackReservationSlot;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrackReservationSlotCustomRepositoryImpl
        implements TrackReservationSlotCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<TrackReservationSlot> findAllByTrackIdAndDate(Long trackId, LocalDate date) {
        final List<TrackReservationSlot> result =
                jpaQueryFactory
                        .selectFrom(trackReservationSlot)
                        .where(
                                notDeleted(trackReservationSlot),
                                trackReservationSlot.track.id.eq(trackId),
                                reservationTimeIn(date))
                        .orderBy(trackReservationSlot.startedAt.asc())
                        .fetch();
        return new HashSet<>(result);
    }

    @Override
    public boolean existsByTrackIdAndSlots(Long trackId, List<ReservationSlotVo> slots) {
        return jpaQueryFactory
                        .selectFrom(trackReservationSlot)
                        .where(
                                notDeleted(trackReservationSlot),
                                trackReservationSlot.track.id.eq(trackId),
                                anyReservationTimeIn(slots))
                        .fetchFirst()
                != null;
    }

    // date 에 해당하는 날짜에 이루어진 모든 예약을 가져옵니다.
    private BooleanExpression reservationTimeIn(LocalDate date) {
        final LocalDateTime startOfDay = date.atStartOfDay();
        final LocalDateTime tomorrow = date.plusDays(1).atStartOfDay();
        return trackReservationSlot
                .startedAt
                .goe(startOfDay)
                .and(trackReservationSlot.expiredAt.lt(tomorrow));
    }

    // slots 의 startAt, expiredAt 이 예약된 시간과 겹치는지 확인합니다.
    private BooleanExpression anyReservationTimeIn(List<ReservationSlotVo> slots) {
        List<BooleanExpression> anyMatch =
                slots.stream()
                        .map(
                                slot -> {
                                    final LocalDateTime startedAt = slot.getStartedAt();
                                    final LocalDateTime expiredAt = slot.getExpiredAt();
                                    return trackReservationSlot
                                            .startedAt
                                            .goe(startedAt)
                                            .and(trackReservationSlot.expiredAt.lt(startedAt))
                                            .and(
                                                    trackReservationSlot
                                                            .startedAt
                                                            .goe(expiredAt)
                                                            .and(
                                                                    trackReservationSlot.expiredAt
                                                                            .lt(expiredAt)));
                                })
                        .toList();
        return anyMatch.stream().reduce(BooleanExpression::or).orElse(null);
    }
}
