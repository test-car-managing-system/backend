package com.testcar.car.domains.trackReservation;

import static com.testcar.car.domains.trackReservation.exception.ErrorCode.ALREADY_RESERVED_SLOT;
import static com.testcar.car.domains.trackReservation.exception.ErrorCode.EMPTY_RESERVATION_SLOT;
import static com.testcar.car.domains.trackReservation.exception.ErrorCode.INVALID_RESERVATION_SLOT;

import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.domains.track.Track;
import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import com.testcar.car.domains.trackReservation.model.TrackReservationRequest;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
import com.testcar.car.domains.trackReservation.repository.TrackReservationSlotRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackReservationSlotService {
    private final TrackReservationSlotRepository trackReservationSlotRepository;

    /** 해당 시험장의 예약된 슬롯을 시험장 id로 조회합니다. */
    public Set<TrackReservationSlot> findAllByTrackIdAndDate(Long trackId, LocalDate date) {
        return trackReservationSlotRepository.findAllByTrackIdAndDate(trackId, date);
    }

    /** 해당 시험장을 예약합니다. */
    public List<TrackReservationSlot> reserve(
            Track track, TrackReservation trackReservation, TrackReservationRequest request) {
        this.validateReservationSlots(request.getDate(), request.getReservationSlots());
        final List<ReservationSlotVo> slots = request.getReservationSlots();

        final Map<LocalDateTime, TrackReservationSlot> existSlotMap =
                this.findAllExistedSlotMap(track.getId(), request.getDate());
        slots.forEach(slot -> this.validateSlotNotDuplicated(slot, existSlotMap));

        final List<TrackReservationSlot> reservations =
                slots.stream().map(slot -> createEntity(track, trackReservation, slot)).toList();
        return trackReservationSlotRepository.saveAll(reservations);
    }

    /** 예약하려는 시간과 DB에 존재하는 예약 시간 슬롯 Map 을 비교하여 중복을 검증한다 */
    private Map<LocalDateTime, TrackReservationSlot> findAllExistedSlotMap(
            Long trackId, LocalDate date) {
        final Set<TrackReservationSlot> existedSlots =
                trackReservationSlotRepository.findAllByTrackIdAndDate(trackId, date);
        return existedSlots.stream()
                .collect(Collectors.toMap(TrackReservationSlot::getStartedAt, Function.identity()));
    }

    private void validateSlotNotDuplicated(
            ReservationSlotVo slot, Map<LocalDateTime, TrackReservationSlot> existSlotMap) {
        if (existSlotMap.containsKey(slot.getStartedAt())
                && existSlotMap
                        .get(slot.getStartedAt())
                        .getExpiredAt()
                        .equals(slot.getExpiredAt())) {
            throw new BadRequestException(ALREADY_RESERVED_SLOT);
        }
    }

    /** 해당 예약 건의 슬롯을 모두 삭제(취소) 합니다. */
    public List<TrackReservationSlot> cancelByTrackReservationId(Long trackReservationId) {
        final Set<TrackReservationSlot> slots =
                trackReservationSlotRepository.findAllByTrackReservationId(trackReservationId);
        slots.forEach(TrackReservationSlot::delete);
        return trackReservationSlotRepository.saveAll(slots);
    }

    /** 예약하려는 시간이 현재 시간 이후이며, 모든 날짜가 똑같은지 검증합니다. */
    private void validateReservationSlots(LocalDate date, List<ReservationSlotVo> slots) {
        if (slots == null || slots.isEmpty()) {
            throw new BadRequestException(EMPTY_RESERVATION_SLOT);
        }
        slots.forEach(slot -> validateReservationSlot(date, slot));
    }

    private void validateReservationSlot(LocalDate date, ReservationSlotVo slot) {
        final LocalDateTime startedAt = slot.getStartedAt();
        final LocalDateTime expiredAt = slot.getExpiredAt();

        validateAfterNow(startedAt);
        validateTimeAfter(startedAt, expiredAt);

        if (!startedAt.toLocalDate().equals(date)) {
            throw new BadRequestException(INVALID_RESERVATION_SLOT);
        }
    }

    private void validateAfterNow(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        if (time == null || time.isBefore(now)) {
            throw new BadRequestException(INVALID_RESERVATION_SLOT);
        }
    }

    private void validateTimeAfter(LocalDateTime startedAt, LocalDateTime expiredAt) {
        if (startedAt == null || expiredAt == null) {
            throw new BadRequestException(EMPTY_RESERVATION_SLOT);
        }
        if (expiredAt.isBefore(startedAt)) {
            throw new BadRequestException(INVALID_RESERVATION_SLOT);
        }
    }

    /** 영속되지 않은 시험장 예약 슬롯 엔티티를 생성합니다. */
    private TrackReservationSlot createEntity(
            Track track, TrackReservation trackReservation, ReservationSlotVo slot) {
        return TrackReservationSlot.builder()
                .track(track)
                .trackReservation(trackReservation)
                .startedAt(slot.getStartedAt())
                .expiredAt(slot.getExpiredAt())
                .build();
    }
}
