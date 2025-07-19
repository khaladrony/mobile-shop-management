package com.rony.erpsoft.inventory.enums;

public enum InventoryAction {
    RECEIPT(1),
    ISSUE(-1),
    TRANSFER(2);

    private final int sign;

    InventoryAction(int sign) {
        this.sign = sign;
    }

    public int getSign() {
        return sign;
    }
}
