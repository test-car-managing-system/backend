package com.testcar.car.domains.car.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {
    SEDAN("세단"),
    SUV("SUV"),
    TRUCK("트럭"),
    VAN("승합차");

    private final String description;
}
