package com.github.dkanellis.skyspark.api.algorithms.bitmap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitmapTest {

    private Bitmap bitmap;

    @Before
    public void setUp() {
        bitmap = new Bitmap();
    }

    @Test
    public void toString_returnName() {
        String expectedName = "Bitmap";

        String actualName = bitmap.toString();

        assertEquals(expectedName, actualName);
    }
}