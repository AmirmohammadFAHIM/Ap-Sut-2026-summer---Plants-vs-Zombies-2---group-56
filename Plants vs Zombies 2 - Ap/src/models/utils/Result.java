package models.utils;

import models.factory.builder.PlantType;

public record Result(boolean success, String message , PlantType plantType) {
}
