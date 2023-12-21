package com.testcar.car.domains.gasStation;


import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.gasStation.entity.GasStation;
import com.testcar.car.domains.gasStation.exception.ErrorCode;
import com.testcar.car.domains.gasStation.model.DeleteGasStationRequest;
import com.testcar.car.domains.gasStation.model.RegisterGasStationRequest;
import com.testcar.car.domains.gasStation.model.UpdateGasStationRequest;
import com.testcar.car.domains.gasStation.repository.GasStationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GasStationService {
    private final GasStationRepository gasStationRepository;

    /** 주유소를 이름으로 조회합니다. */
    public GasStation findByName(String name) {
        return gasStationRepository
                .findByNameAndDeletedFalse(name)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GAS_STATION_NOT_FOUND));
    }

    /** 등록된 모든 주유소를 조회합니다. */
    public List<GasStation> findAll() {
        return gasStationRepository.findAllByDeletedFalseOrderByCreatedAtDesc();
    }

    /** 주유소를 id로 조회합니다. */
    public GasStation findById(Long id) {
        return gasStationRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GAS_STATION_NOT_FOUND));
    }

    /** 주유소를 id 리스트로 조회합니다. */
    public List<GasStation> findAllByIdIn(List<Long> ids) {
        final List<GasStation> gasStations = gasStationRepository.findAllByIdInAndDeletedFalse(ids);
        if (gasStations.size() != ids.size()) {
            throw new NotFoundException(ErrorCode.GAS_STATION_NOT_FOUND);
        }
        return gasStations;
    }

    /** 주유소를 등록합니다. */
    public GasStation register(RegisterGasStationRequest request) {
        validateNameNotDuplicated(request.getName());
        final GasStation gasStation = GasStation.builder().name(request.getName()).build();
        return gasStationRepository.save(gasStation);
    }

    /** 주유소를 수정합니다. */
    public GasStation update(Long id, UpdateGasStationRequest request) {
        final GasStation gasStation = findById(id);
        if (!gasStation.getName().equals(request.getName())) {
            validateNameNotDuplicated(request.getName());
        }
        gasStation.update(request.getName());
        return gasStationRepository.save(gasStation);
    }

    /** 주유소를 삭제합니다. */
    public void deleteAll(DeleteGasStationRequest request) {
        final List<GasStation> gasStations = this.findAllByIdIn(request.getIds());
        gasStations.forEach(GasStation::delete);
        gasStationRepository.saveAll(gasStations);
    }

    /** 주유소명 중복을 검사합니다. */
    public void validateNameNotDuplicated(String name) {
        if (gasStationRepository.existsByNameAndDeletedFalse(name)) {
            throw new BadRequestException(ErrorCode.GAS_STATION_NAME_ALREADY_EXISTS);
        }
    }
}
