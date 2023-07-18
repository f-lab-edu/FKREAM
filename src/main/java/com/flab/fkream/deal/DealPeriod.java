package com.flab.fkream.deal;

import java.time.LocalDate;

public enum DealPeriod {
    ONE_YEAR(1),
    SIX_MONTH(6),
    THREE_MONTH(3),
    ONE_MONTH(1);

    private final int value;

    DealPeriod(int value) {
        this.value = value;
    }

    public LocalDate getPeriodFromNow() {
        if (this == ONE_YEAR) {
            return LocalDate.now().minusYears(this.value);
        } else {
            return LocalDate.now().minusMonths(this.value);
        }
    }
}