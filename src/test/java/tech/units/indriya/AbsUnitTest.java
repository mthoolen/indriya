/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2019, Units of Measurement project.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-385, Indriya nor the names of their contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tech.units.indriya;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static javax.measure.MetricPrefix.*;
import static tech.units.indriya.unit.Units.*;

import java.util.Map;

import javax.measure.Dimension;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tech.units.indriya.AbstractUnit;
import tech.units.indriya.format.SimpleUnitFormat;
import tech.units.indriya.unit.BaseUnit;

/**
 * Unit tests on the AbstractUnit class.
 *
 */
public class AbsUnitTest {
  private static final AbstractUnit<Length> sut = new BaseUnit<>("m");

  @BeforeAll
  public static void init() {
    sut.setName("Test");
  }

  @Test
  public void testName() {
    assertEquals("Test", sut.getName());
  }

  @Test
  public void testReturnedClass() {
    // assertEquals("Q", String.valueOf(sut.getActualType())); // TODO we
    // hope to get better type information like <Length> in future Java
    // versions
    assertEquals("java.lang.reflect.TypeVariable<D>", String.valueOf(sut.getActualType()));
  }

  @Test
  public void testParse() {
    assertEquals(KILO(WATT), AbstractUnit.parse("kW"));
  }

  @Test
  public void testParse2() {
    assertEquals(MILLI(CELSIUS), AbstractUnit.parse("m°C"));
  }
  
/*
  @Test
  public void testAnnotate() {
    assertEquals("g{Gr}", (((AbstractUnit<Mass>) GRAM).annotate("Gr")).toString());
  }

  @Test
  public void testAnnotateClass() {
    assertEquals("tech.units.indriya.unit.AnnotatedUnit", (((AbstractUnit<Mass>) GRAM).annotate("Gr")).getClass().getName());
  }
  */
  

  private static final String SYMBOL = "symbol";
  private static final String NAME = "name";
  private static final String LABEL = "label";

  /**
   * Trivial implementation of the AbstractUnit class, in order to test the functionality provided in the abstract class.
   *
   */
  class DummyUnit extends AbstractUnit {

    private DummyUnit(String symbol) {
      super(symbol);
    }

    public int compareTo(Object arg0) {
      return 0;
    }

    @Override
    protected Unit toSystemUnit() {
      return null;
    }

    @Override
    public UnitConverter getSystemConverter() {
      return null;
    }

    @Override
    public Map getBaseUnits() {
      return null;
    }

    @Override
    public Dimension getDimension() {
      return null;
    }

    @Override
    public boolean equals(Object obj) {
      return false;
    }

    @Override
    public int hashCode() {
      return 0;
    }

  }

  /**
   * Verifies that the constructor has the symbol parameter wired correctly.
   */
  @Test
  public void constructorWithSymbolHasTheSymbolWiredCorrectly() {
    DummyUnit unit = new DummyUnit(SYMBOL);
    assertEquals(SYMBOL, unit.getSymbol());
  }

  /**
   * Verifies that the setter and getter for name are wired correctly.
   */
  @Test
  public void setterAndGetterForNameAreWiredCorrectly() {
    DummyUnit unit = new DummyUnit(SYMBOL);
    unit.setName(NAME);
    assertEquals(NAME, unit.getName());
  }

  /**
   * Verifies that the parse method is wired to a parser.
   */
  @Test
  public void parseMethodIsWiredToAParser() {
    assertEquals(KILO(WATT), AbstractUnit.parse("kW"));
  }

  /**
   * Verifies that the toString method is wired to the SimpleUnitFormat formatter.
   */
  @Test
  public void toStrindMethodIsWiredToAFormatter() {
    DummyUnit unit = new DummyUnit(SYMBOL);
    SimpleUnitFormat.getInstance().label(unit, LABEL);
    assertEquals(LABEL, unit.toString());
  }

  /**
   * Verifies that a unit is equal to itself according to the compareTo method.
   */
  @Test
  public void compareToReturnsZeroWhenObjectIsProvidedAsArgument() {
    DummyUnit unit = new DummyUnit(SYMBOL);
    assertEquals(0, unit.compareTo(unit));
  }
  
  /**
   * Verifies that a unit is equal to another unit with the same symbol according to the compareTo method.
   */
  @Test
  public void compareToReturnsZeroWhenUnitWithSameSymbolIsProvided() {
    DummyUnit unit = new DummyUnit(SYMBOL);
    DummyUnit otherUnit = new DummyUnit(SYMBOL);
    assertEquals(0, unit.compareTo(otherUnit));
  }
  
  /**
   * Verifies that the compareTo method returns a positive number when a unit with a symbol is compared to a unit without a symbol.
   */
  @Test
  public void compareToReturnsPositiveNumberWhenTheOtherUnitHasNoSymbol() {
    DummyUnit unit = new DummyUnit(SYMBOL);
    DummyUnit otherUnit = new DummyUnit(null);
    assertTrue(unit.compareTo(otherUnit) > 0);
  }
  
  /**
   * Verifies that a unit is equal to another unit with the same symbol and name according to the compareTo method.
   */
  @Test
  public void compareToReturnsZeroWhenUnitWithSameSymbolAndNameIsProvided() {
    DummyUnit unit = new DummyUnit(SYMBOL);
    unit.setName(NAME);
    DummyUnit otherUnit = new DummyUnit(SYMBOL);
    otherUnit.setName(NAME);
    assertEquals(0, unit.compareTo(otherUnit));
  }
  
  /**
   * Verifies that when compared to a unit with a smaller symbol, the compareTo method returns a positive number.
   */
  @Test
  public void compareToReturnsAPositiveIntegerWhenUnitWithSmallerSymbolIsProvided() {
    DummyUnit unit = new DummyUnit("b");
    DummyUnit otherUnit = new DummyUnit("a");
    assertTrue(unit.compareTo(otherUnit) > 0);
  }

  /**
   * Verifies that when compared to a unit with the same symbol but a smaller name, the compareTo method returns a positive number.
   */
  @Test
  public void compareToReturnsAPositiveNumberWhenUnitWithSameSymbolButSmallerNameIsProvided() {
    DummyUnit unit = new DummyUnit(SYMBOL);
    unit.setName("b");
    DummyUnit otherUnit = new DummyUnit(SYMBOL);
    otherUnit.setName("a");
    assertTrue(unit.compareTo(otherUnit) > 0);
  }

  /**
   * Verifies that when compared to a unit with a smaller symbol but a larger name, the compareTo method returns a positive number.
   */
  @Test
  public void compareToReturnsAPositiveNumberWhenUnitWithSmallerSymbolButLargerNameIsProvided() {
    DummyUnit unit = new DummyUnit("B");
    unit.setName("a");
    DummyUnit otherUnit = new DummyUnit("A");
    otherUnit.setName("b");
    assertTrue(unit.compareTo(otherUnit) > 0);
  }

  /**
   * Verifies that a unit is equivalent to itself.
   */
  @Test
  public void unitIsEquivalentToItself() {
    DummyUnit unit = new DummyUnit(SYMBOL);
    assertTrue(unit.isEquivalentOf(unit));
  }
  
  /**
   * Verifies that a kilo of a gram is equivalent to a kilogram.
   */
  @Test
  public void aKiloOfAGramIsEquivalentToAKilogram() {
    assertTrue((((AbstractUnit<Mass>) KILO(GRAM))).isEquivalentOf(KILOGRAM));
  }
  
  /**
   * Verifies that a gram is not equivalent to a kilogram.
   */
  @Test
  public void aGramIsNotEquivalentToAKilogram() {
    assertFalse((((AbstractUnit<Mass>) GRAM)).isEquivalentOf(KILOGRAM));
  }

}
