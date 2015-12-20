package com.github.dkanellis.skyspark.api.algorithms.bitmap;

import com.github.dkanellis.skyspark.api.test_utils.categories.types.UnitTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import scala.Tuple2;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;

import static com.github.dkanellis.skyspark.api.utils.BitSets.bitSetFromString;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
@Category(UnitTests.class)
public class BitSliceCreatorImplTest {

    private final BitSet expected;
    private final Tuple2<Tuple2<Double, Long>, Iterable<BitSet>> data;
    private BitSliceCreatorImpl bitSliceCreator;

    public BitSliceCreatorImplTest(Tuple2<Tuple2<Double, Long>, Iterable<BitSet>> data, BitSet expected) {
        this.data = data;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Iterable<BitSet> bitVectors = Arrays.asList(
                bitSetFromString("000111111"), // 5.4
                bitSetFromString("001111111"), // 5.0
                bitSetFromString("011111111"), // 3.6
                bitSetFromString("000011111"), // 5.9
                bitSetFromString("000011111"), // 5.9
                bitSetFromString("111111111"), // 2.5
                bitSetFromString("000000111"), // 6.3
                bitSetFromString("000000001"), // 9.9
                bitSetFromString("000000011"), // 6.7
                bitSetFromString("000001111")  // 6.1
        );
        return Arrays.asList(new Object[][]{
                {new Tuple2<>(new Tuple2<>(2.5, 8L), bitVectors), bitSetFromString("0000010000")},
                {new Tuple2<>(new Tuple2<>(3.6, 7L), bitVectors), bitSetFromString("0010010000")},
                {new Tuple2<>(new Tuple2<>(5.0, 6L), bitVectors), bitSetFromString("0110010000")},
                {new Tuple2<>(new Tuple2<>(5.4, 5L), bitVectors), bitSetFromString("1110010000")},
                {new Tuple2<>(new Tuple2<>(5.9, 4L), bitVectors), bitSetFromString("1111110000")},
                {new Tuple2<>(new Tuple2<>(6.1, 3L), bitVectors), bitSetFromString("1111110001")},
                {new Tuple2<>(new Tuple2<>(6.3, 2L), bitVectors), bitSetFromString("1111111001")},
                {new Tuple2<>(new Tuple2<>(6.7, 1L), bitVectors), bitSetFromString("1111111011")},
                {new Tuple2<>(new Tuple2<>(9.9, 0L), bitVectors), bitSetFromString("1111111111")}
        });
    }

    @Before
    public void setUp() {
        this.bitSliceCreator = new BitSliceCreatorImpl();
    }

    @Test
    public void returnCorrectBitSlices() {
        //BitSlice actual = bitSliceCreator.fromTuple(null);// TODO

        assertEquals(expected, null);
    }
}