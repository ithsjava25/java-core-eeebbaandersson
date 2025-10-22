package com.example.warehouse;

import java.math.BigDecimal;

public interface Shippable {
    BigDecimal calculateShippingCost();
    double weight();
}
