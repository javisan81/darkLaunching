package com.company;

import java.math.BigDecimal;
import java.util.Map;

public class Vat {
    private static Map<String, BigDecimal> vatPerProduct = Map.of(
      "charity", BigDecimal.ZERO,
      "bread", BigDecimal.valueOf(4)
    );

    public BigDecimal calculate(String product) {
        return vatPerProduct.get(product);
    }
}
