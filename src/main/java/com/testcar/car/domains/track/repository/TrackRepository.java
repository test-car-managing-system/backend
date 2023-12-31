package com.testcar.car.domains.track.repository;


import com.testcar.car.domains.track.entity.Track;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long>, TrackCustomRepository {
    Optional<Track> findByIdAndDeletedFalse(Long id);

    Optional<Track> findByNameAndDeletedFalse(String name);

    List<Track> findAllByIdInAndDeletedFalse(List<Long> ids);

    List<Track> findAllByDeletedFalse();

    boolean existsByNameAndDeletedFalse(String name);
}
