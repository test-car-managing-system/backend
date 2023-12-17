package com.testcar.car.domains.carReservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.domains.carReservation.entity.CarReservation;
import com.testcar.car.domains.carReservation.entity.ReservationStatus;
import com.testcar.car.domains.carReservation.repository.CarReservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarReservationSchedulerServiceTest {
    @Mock private CarReservationRepository carReservationRepository;
    @InjectMocks private CarReservationSchedulerService carReservationSchedulerService;

    private static List<CarReservation> carReservations;

    @BeforeAll
    public static void setUp() {
        carReservations =
                List.of(
                        CarEntityFactory.createCarReservation(),
                        CarEntityFactory.createCarReservation(),
                        CarEntityFactory.createCarReservation());
    }

    @Test
    void 사용중인_차량예약_마감시간이_되면_모두_반납한다() {
        // given
        final LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        given(carReservationRepository.findAllByExpiredAtAndStatusReserved(now))
                .willReturn(carReservations.subList(1, 2));

        // when
        carReservationSchedulerService.updateAllReturned();

        // then
        assertEquals(ReservationStatus.RESERVED, carReservations.get(0).getStatus());
        assertEquals(ReservationStatus.RETURNED, carReservations.get(1).getStatus());
        assertEquals(ReservationStatus.RESERVED, carReservations.get(2).getStatus());
        then(carReservationRepository).should().findAllByExpiredAtAndStatusReserved(now);
        then(carReservationRepository).should().saveAll(carReservations.subList(1, 2));
    }
}
