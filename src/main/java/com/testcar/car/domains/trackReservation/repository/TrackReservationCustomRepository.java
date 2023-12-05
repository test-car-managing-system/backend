package com.testcar.car.domains.trackReservation.repository;


import com.testcar.car.domains.track.Track;
import com.testcar.car.domains.track.model.vo.TrackFilterCondition;
import java.util.List;

public interface TrackReservationCustomRepository {
    List<Track> findAllByCondition(TrackFilterCondition condition);
}
