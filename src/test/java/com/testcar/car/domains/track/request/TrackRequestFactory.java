package com.testcar.car.domains.track.request;

import static com.testcar.car.common.Constant.TRACK_DESCRIPTION;
import static com.testcar.car.common.Constant.TRACK_LENGTH;
import static com.testcar.car.common.Constant.TRACK_LOCATION;
import static com.testcar.car.common.Constant.TRACK_NAME;

import com.testcar.car.domains.track.model.DeleteTrackRequest;
import com.testcar.car.domains.track.model.RegisterTrackRequest;
import java.util.List;

public class TrackRequestFactory {
    private TrackRequestFactory() {}

    public static RegisterTrackRequest createRegisterTrackRequest() {
        return RegisterTrackRequest.builder()
                .name(TRACK_NAME)
                .location(TRACK_LOCATION)
                .description(TRACK_DESCRIPTION)
                .length(TRACK_LENGTH)
                .build();
    }

    public static RegisterTrackRequest createRegisterTrackRequest(String name) {
        return RegisterTrackRequest.builder()
                .name(name)
                .location(TRACK_LOCATION)
                .description(TRACK_DESCRIPTION)
                .length(TRACK_LENGTH)
                .build();
    }

    public static DeleteTrackRequest createDeleteTrackRequest(List<Long> ids) {
        return DeleteTrackRequest.builder().ids(ids).build();
    }
}
