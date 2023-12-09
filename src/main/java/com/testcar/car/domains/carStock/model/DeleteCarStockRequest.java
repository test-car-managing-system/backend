package com.testcar.car.domains.carStock.model;


import com.testcar.car.domains.carStock.entity.StockStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class DeleteCarStockRequest {
    @NotEmpty
    @Schema(description = "차량 재고 ID", example = "[1, 2, 3]")
    private List<@NotNull @Positive Long> ids;
}
