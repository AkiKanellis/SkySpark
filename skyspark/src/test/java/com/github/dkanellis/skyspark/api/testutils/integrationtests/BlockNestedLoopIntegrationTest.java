package com.github.dkanellis.skyspark.api.testUtils.integrationTests;

import com.github.dkanellis.skyspark.api.algorithms.SkylineAlgorithm;
import com.github.dkanellis.skyspark.api.algorithms.bnl.BlockNestedLoop;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import com.github.dkanellis.skyspark.api.testUtils.categories.algorithms.BlockNestedLoopTests;
import com.github.dkanellis.skyspark.api.testUtils.categories.combinations.BlockNestedLoopBigSizeTests;
import com.github.dkanellis.skyspark.api.testUtils.categories.combinations.BlockNestedLoopMediumSizeTests;
import com.github.dkanellis.skyspark.api.testUtils.categories.combinations.BlockNestedLoopSmallSizeTests;
import com.github.dkanellis.skyspark.api.testUtils.categories.speeds.SlowTests;
import com.github.dkanellis.skyspark.api.testUtils.categories.types.IntegrationTests;

@Category({BlockNestedLoopTests.class, IntegrationTests.class, SlowTests.class})
public class BlockNestedLoopIntegrationTest extends BaseSkylineAlgorithmIntegrationTest {

    @Override
    protected SkylineAlgorithm getSkylineAlgorithm() {
        return new BlockNestedLoop();
    }

    @Test
    @Category(BlockNestedLoopSmallSizeTests.class)
    public void smallAnticorrelated() {
        super.smallAnticorrelated();
    }

    @Test
    @Category(BlockNestedLoopSmallSizeTests.class)
    public void smallCorrelated() {
        super.smallCorrelated();
    }

    @Test
    @Category(BlockNestedLoopSmallSizeTests.class)
    public void smallUniform() {
        super.smallUniform();
    }

    @Test
    @Category(BlockNestedLoopMediumSizeTests.class)
    public void mediumAnticorrelated() {
        super.mediumAnticorrelated();
    }

    @Test
    @Category(BlockNestedLoopMediumSizeTests.class)
    public void mediumCorrelated() {
        super.mediumCorrelated();
    }

    @Test
    @Category(BlockNestedLoopMediumSizeTests.class)
    public void mediumUniform() {
        super.mediumUniform();
    }

    @Test
    @Category(BlockNestedLoopBigSizeTests.class)
    public void bigAnticorrelated() {
        super.bigAnticorrelated();
    }

    @Test
    @Category(BlockNestedLoopBigSizeTests.class)
    public void bigCorrelated() {
        super.bigCorrelated();
    }

    @Test
    @Category(BlockNestedLoopBigSizeTests.class)
    public void bigUniform() {
        super.bigUniform();
    }
}