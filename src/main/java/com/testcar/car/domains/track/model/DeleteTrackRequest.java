package com.testcar.car.domains.track.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTrackRequest {
    @NotNull(message = "시험장 ID 리스트를 입력해주세요.")
    @NotEmpty(message = "시험장 ID 리스트를 입력해주세요.")
    @Schema(description = "시험장 ID 리스트", example = "[1, 2, 3]")
    private List<@Valid @NotNull @Positive Long> ids;
}
