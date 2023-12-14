package com.testcar.car.domains.track.repository;

import static com.testcar.car.domains.track.entity.QTrack.track;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.model.vo.TrackFilterCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrackCustomRepositoryImpl implements TrackCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Track> findAllByCondition(TrackFilterCondition condition) {
        return jpaQueryFactory
                .selectFrom(track)
                .where(
                        notDeleted(track),
                        trackNameContainsOrNull(condition.getName()),
                        locationContainsOrNull(condition.getLocation()))
                .orderBy(track.name.asc())
                .fetch();
    }

    private BooleanExpression trackNameContainsOrNull(String name) {
        return (name == null) ? null : track.name.contains(name);
    }

    private BooleanExpression locationContainsOrNull(String location) {
        return (location == null) ? null : track.location.contains(location);
    }
}
