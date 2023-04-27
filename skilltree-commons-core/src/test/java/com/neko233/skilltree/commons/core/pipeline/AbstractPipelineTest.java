package com.neko233.skilltree.commons.core.pipeline;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2022-12-15
 */
public class AbstractPipelineTest {


    class DemoPipeline extends AbstractPipeline<String> {

    }


    @Test
    public void testOk() {
        DemoPipeline demoPipeline = new DemoPipeline();
        String output = demoPipeline
                .addPipelineNode(o -> "1: " + o)
                .addPipelineNode(o -> "2: " + o)
                .handle("hello");

        Assert.assertEquals("2: 1: hello", output);
    }
}