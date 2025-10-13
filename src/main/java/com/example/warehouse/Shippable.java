package com.example.warehouse;

import java.math.BigDecimal;

//Where an interface requires Double (e.g., weight()), convert BigDecimal to double on return.
public interface Shippable {
    BigDecimal calculateShippingCost();
    double weight();
}
