package com.company;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class VatTest {
    @Test
    public void forCharity(){
        Vat vat = new Vat();
        assertEquals(BigDecimal.ZERO, vat.calculate("charity"));
    }

    @Test
    public void forBread(){
        Vat vat = new Vat();
        assertEquals(BigDecimal.valueOf(4), vat.calculate("bread"));
    }
}
