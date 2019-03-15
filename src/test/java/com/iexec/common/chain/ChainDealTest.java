package com.iexec.common.chain;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class ChainDealTest {

    @Test
    public void shouldCreateZeroCmdline() {
        List<String> params = ChainDeal.stringParamsToList("{}");
        assertEquals(params.size(), 0);
    }

    @Test
    public void shouldCreateOneCmdlineWithEmptyParam() {
        List<String> params = ChainDeal.stringParamsToList("");
        assertEquals(params.size(), 1);
        assertEquals(params.get(0), "");
    }

    @Test
    public void shouldCreateOneCmdlineWithFullParam() {
        List<String> params = ChainDeal.stringParamsToList("bla");
        assertEquals(params.size(), 1);
        assertEquals(params.get(0), "bla");
    }


    @Test
    public void shouldCreateOneCmdlineWithParamSinceValidJson() {
        List<String> params = ChainDeal.stringParamsToList("{\"0\" : \"bla\" }");
        assertEquals(params.size(), 1);
        assertEquals(params.get(0), "bla");
    }

    @Test
    public void shouldCreateOneCmdlineWithFullParamSinceInValidJson() {
        List<String> params = ChainDeal.stringParamsToList("{\"0\" : \"bla }");
        assertEquals(params.size(), 1);
        assertEquals(params.get(0), "{\"0\" : \"bla }");
    }

    @Test
    public void shouldCreateTwoCmdlinesWithParamSinceValidJson() {
        List<String> params = ChainDeal.stringParamsToList("{\"0\" : \"bla\", \"1\" : \"bli\" }");
        assertEquals(params.size(), 2);
        assertEquals(params.get(0), "bla");
        assertEquals(params.get(1), "bli");
    }

}