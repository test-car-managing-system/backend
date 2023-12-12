package com.testcar.car.domains.carStock.request;

import static com.testcar.car.common.Constant.ANOTHER_CAR_STOCK_NUMBER;
import static com.testcar.car.common.Constant.CAR_STOCK_NUMBER;

import com.testcar.car.domains.carStock.entity.StockStatus;
import com.testcar.car.domains.carStock.model.DeleteCarStockRequest;
import com.testcar.car.domains.carStock.model.RegisterCarStockRequest;
import com.testcar.car.domains.carStock.model.UpdateCarStockRequest;
import java.util.List;

public class CarStockRequestFactory {
    private CarStockRequestFactory() {}

    public static RegisterCarStockRequest createRegisterCarStockRequest() {
        return RegisterCarStockRequest.builder()
                .carId(1L)
                .stockNumber(CAR_STOCK_NUMBER)
                .status(StockStatus.AVAILABLE)
                .build();
    }

    public static UpdateCarStockRequest createUpdateCarStockRequest() {
        return UpdateCarStockRequest.builder()
                .stockNumber(ANOTHER_CAR_STOCK_NUMBER)
                .status(StockStatus.AVAILABLE)
                .build();
    }

    public static DeleteCarStockRequest createDeleteCarStockRequest() {
        return DeleteCarStockRequest.builder().ids(List.of(1L, 2L, 3L)).build();
    }
}
