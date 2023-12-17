package com.testcar.car.domains.carReservation;


import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.repository.CarReservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 차량 예약에 대한 스케쥴링 설정 */
@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationSchedulerService {
    private final CarReservationRepository carReservationRepository;

    /** 매일 자정 예약중인 차량의 반납시간이 도래하면 이용완료로 변경한다. */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateAllReturned() {
        // 각 차량 예약 건의 expiredAt 시간이 now 와 같다면 이용완료 시킨다.
        final LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        final List<CarReservation> carReservations =
                carReservationRepository.findAllByExpiredAtAndStatusReserved(now);

        carReservations.forEach(CarReservation::updateReturn);
        carReservationRepository.saveAll(carReservations);
    }
}
