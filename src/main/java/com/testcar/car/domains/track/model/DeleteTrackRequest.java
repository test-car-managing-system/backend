package com.testcar.car.domains.track.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;

@Getter
public class DeleteTrackRequest {
    @NotEmpty
    @Schema(description = "시험장 ID 리스트", example = "[1, 2, 3]")
    private List<Long> ids;
}
