package com.testcar.car.domains.trackReservation;


import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import com.testcar.car.domains.trackReservation.repository.TrackReservationRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 시험장 예약에 대한 스케쥴링 설정 */
@Service
@Transactional
@RequiredArgsConstructor
public class TrackReservationSchedulerService {
    private final TrackReservationRepository trackReservationRepository;

    /** 매 시간 예약중인 시험장의 반납시간이 도래하면 이용완료로 변경한다. */
    @Scheduled(cron = "0 0 * * * ?")
    public void updateAllCompleted() {
        // 각 시험장 예약의 슬롯 중, 마지막 시간의 expiredAt 시간이 now 와 같다면 이용완료 시킨다.
        final LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        final List<TrackReservation> trackReservations =
                trackReservationRepository.findAllBySlotExpiredAtAndStatusReserved(now);

        trackReservations.stream()
                .filter(trackReservation -> isLastSlotExpiredAtEquals(trackReservation, now))
                .forEach(TrackReservation::completed);
        trackReservationRepository.saveAll(trackReservations);
    }

    private boolean isLastSlotExpiredAtEquals(
            TrackReservation trackReservation, LocalDateTime time) {
        return trackReservation.getTrackReservationSlots().stream()
                .max(Comparator.comparing(TrackReservationSlot::getExpiredAt))
                .map(TrackReservationSlot::getExpiredAt)
                .filter(expiredAt -> expiredAt.equals(time))
                .isPresent();
    }
}
