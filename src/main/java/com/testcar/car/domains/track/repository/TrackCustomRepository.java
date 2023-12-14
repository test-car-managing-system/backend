package com.testcar.car.domains.track.repository;


import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.model.vo.TrackFilterCondition;
import java.util.List;

public interface TrackCustomRepository {
    List<Track> findAllByCondition(TrackFilterCondition condition);
}
