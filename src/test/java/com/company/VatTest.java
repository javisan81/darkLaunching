package com.company;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class VatTest {
    GovermentMessageRepository governmentMessageRepository = new GovermentMessageRepository() {
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

    @Test
    public void forCharity(){
        Vat vat = new Vat();
        assertEquals(BigDecimal.ZERO, vat.calculate("charity"));
        assertEquals(BigDecimal.ZERO, vat.calculate("charity"));

    }

    @Test
    public void forBread(){
        ToggleRepository toggleRepository= new ToggleRepository() {
            @Override
            public boolean isNewVatToggleOn() {
                return false;
            }
        };
        Vat vat = new Vat(toggleRepository, governmentMessageRepository);
        assertEquals(BigDecimal.valueOf(4), vat.calculate("bread"));
        assertEquals(BigDecimal.valueOf(10), governmentMessageRepository.getVatCalculated());
        assertEquals("bread", governmentMessageRepository.getProduct());
    }
    @Test
    public void newVatForCharity(){
        ToggleRepository toggleRepository= new ToggleRepository() {
            @Override
            public boolean isNewVatToggleOn() {
                return true;
            }
        };
        Vat vat = new Vat(toggleRepository, governmentMessageRepository);
        assertEquals(BigDecimal.valueOf(5), vat.calculate("charity"));
    }

    @Test
    public void newVatForBread(){
        ToggleRepository toggleRepository= new ToggleRepository() {
            @Override
            public boolean isNewVatToggleOn() {
                return true;
            }
        };
        Vat vat = new Vat(toggleRepository);
        assertEquals(BigDecimal.valueOf(10), vat.calculate("bread"));
    }
}
