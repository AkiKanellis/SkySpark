package com.github.dkanellis.skyspark.api.math.point;

import java.io.Serializable;

/**
 * @author Dimitris Kanellis
 */
public class PointFlag implements Serializable {

    private final int xBit;
    private final int yBit;

    public PointFlag(int xBit, int yBit) {
        this.xBit = xBit;
        this.yBit = yBit;
    }

    public int getXBit() {
        return xBit;
    }

    public int getYBit() {
        return yBit;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PointFlag)) {
            return false;
        }

        PointFlag other = (PointFlag) o;
        return xBit == other.getXBit() && yBit == other.getYBit();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + xBit;
        hash = 59 * hash + yBit;
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[').append(xBit).append(yBit).append(']');
        return buffer.toString();
    }

}
