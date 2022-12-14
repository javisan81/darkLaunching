package com.company;

import java.math.BigDecimal;
import java.util.Map;

public class Vat {
    private static Map<String, BigDecimal> vatPerProduct = Map.of(
      "charity", BigDecimal.ZERO,
      "bread", BigDecimal.valueOf(4)
    );

    private static Map<String, BigDecimal> vatPerProductNewMap = Map.of(
            "charity", BigDecimal.valueOf(5),
            "bread", BigDecimal.valueOf(10)
    );
    private final ToggleRepository toggleRepository;
    private final GovermentMessageRepository governmentMessageRepository;

    public Vat(ToggleRepository toggleRepository, GovermentMessageRepository governmentMessageRepository) {
        this.toggleRepository=toggleRepository;
        this.governmentMessageRepository=governmentMessageRepository;
    }

    public BigDecimal calculate(String product) {
        if(toggleRepository.isNewVatToggleOn()){
            return vatPerProductNewMap.get(product);
        }
        try{
            governmentMessageRepository.sendToGoverment(product, vatPerProductNewMap.get(product));
        }catch (Exception e){
        }
        return vatPerProduct.get(product);
    }
}
