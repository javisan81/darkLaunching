package com.company;

import java.math.BigDecimal;

public interface GovermentMessageRepository {
    void sendToGoverment(String product, BigDecimal vatCalculated);

}
