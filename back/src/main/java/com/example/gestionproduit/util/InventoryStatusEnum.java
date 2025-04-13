package com.example.gestionproduit.util;

public enum InventoryStatusEnum {
    INSTOCK,
    LOWSTOCK,
    OUTOFSTOCK;

    public static boolean isValidStatus(String status) {
        for (InventoryStatusEnum inventoryStatus : InventoryStatusEnum.values()) {
            if (inventoryStatus.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}