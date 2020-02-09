package com.pyskacz.android.myexpenses.enums;

public enum Sheet {
    //TODO: fix CURRENT_MONTH to dynamic

    CURRENT_MONTH("02.2020"),
    SETTINGS("Ustawienia");

    private String name;

    private Sheet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
