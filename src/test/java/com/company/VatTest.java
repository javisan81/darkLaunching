package com.company;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class VatTest {
    private static BigDecimal vatCalculatedForGovernment;
    private static String productForGovernment;
    private final GovermentMessageRepository inMemoryGovernmentRepository = (product, vatCalculated) -> {
        VatTest.vatCalculatedForGovernment = vatCalculated;
        VatTest.productForGovernment = product;
    };
    private final GovermentMessageRepository nullGovernmentRepository = (product, vatCalculated) -> {};

    @Test
    public void forCharity() {
        Vat vat = new Vat(() -> false, nullGovernmentRepository);
        assertEquals(BigDecimal.ZERO, vat.calculate("charity"));
        assertEquals(BigDecimal.ZERO, vat.calculate("charity"));
    }

    @Test
    public void forBread() {
        Vat vat = new Vat(() -> false, inMemoryGovernmentRepository);
        assertEquals(BigDecimal.valueOf(4), vat.calculate("bread"));
        assertEquals(BigDecimal.valueOf(10), vatCalculatedForGovernment);
        assertEquals("bread", productForGovernment);
    }

    @Test
    public void newVatForCharity() {
        ToggleRepository toggleRepository = () -> true;
        Vat vat = new Vat(toggleRepository, inMemoryGovernmentRepository);
        assertEquals(BigDecimal.valueOf(5), vat.calculate("charity"));
    }

    @Test
    public void newVatForBread() {
        Vat vat = new Vat(() -> true, nullGovernmentRepository);
        assertEquals(BigDecimal.valueOf(10), vat.calculate("bread"));
    }
}
