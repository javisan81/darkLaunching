package com.company;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class VatTest {
    private final GovermentMessageRepository inMemoryGovernmentRepository = new GovermentMessageRepository() {
        public BigDecimal vatCalculated;
        public String product;
        @Override
        public void sendToGoverment(String product, BigDecimal vatCalculated) {
            this.vatCalculated=vatCalculated;
            this.product=product;
        }

        @Override
        public String getProduct() {
            return product;
        }

        @Override
        public BigDecimal getVatCalculated() {
            return vatCalculated;
        }
    };
    private final GovermentMessageRepository nullGovernmentRepository = new GovermentMessageRepository() {
        @Override
        public void sendToGoverment(String product, BigDecimal vatCalculated) {

        }

        @Override
        public String getProduct() {
            return null;
        }

        @Override
        public BigDecimal getVatCalculated() {
            return null;
        }
    };

    @Test
    public void forCharity(){
        Vat vat = new Vat(() -> false, nullGovernmentRepository);
        assertEquals(BigDecimal.ZERO, vat.calculate("charity"));
        assertEquals(BigDecimal.ZERO, vat.calculate("charity"));
    }

    @Test
    public void forBread(){
        Vat vat = new Vat(() -> false, inMemoryGovernmentRepository);
        assertEquals(BigDecimal.valueOf(4), vat.calculate("bread"));
        assertEquals(BigDecimal.valueOf(10), inMemoryGovernmentRepository.getVatCalculated());
        assertEquals("bread", inMemoryGovernmentRepository.getProduct());
    }
    @Test
    public void newVatForCharity(){
        ToggleRepository toggleRepository= () -> true;
        Vat vat = new Vat(toggleRepository, inMemoryGovernmentRepository);
        assertEquals(BigDecimal.valueOf(5), vat.calculate("charity"));
    }

    @Test
    public void newVatForBread(){
        Vat vat = new Vat(() -> true, nullGovernmentRepository);
        assertEquals(BigDecimal.valueOf(10), vat.calculate("bread"));
    }
}
