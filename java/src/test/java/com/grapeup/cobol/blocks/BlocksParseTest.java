package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksParseTest {

    // Helper constant used in TC8 to match the original test reference.
    // Adjust as appropriate for your environment if needed.
    private static final int BLOCKS_CAPACITY = 10000;

    @Test
    void TC1_testBlocksParseValid() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext()
                .with("LK-JSON", "{'minecraft:stone':{'definition':{'type':'solid'},'properties':{'texture':['stone','cobblestone']},'states':[{'id':0,'default':true},{'id':1}]}}");

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC1", inputs);

        // Assert the output parameters
        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(1, result.getInt("BLOCK-COUNT"));
        assertEquals("minecraft:stone", result.get("BLOCK-ENTRY-NAME(1)"));
        assertEquals("solid", result.get("BLOCK-ENTRY-TYPE(1)"));
        assertEquals(1, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT(1)"));
        assertEquals("texture", result.get("BLOCK-ENTRY-PROPERTY-NAME(1, 1)"));
        assertEquals(2, result.getInt("BLOCK-ENTRY-PROPERTY-VALUE-COUNT(1, 1)"));
        assertEquals("stone", result.get("BLOCK-ENTRY-PROPERTY-VALUE(1, 1, 1)"));
        assertEquals("cobblestone", result.get("BLOCK-ENTRY-PROPERTY-VALUE(1, 1, 2)"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-MINIMUM-STATE-ID(1)"));
        assertEquals(1, result.getInt("BLOCK-ENTRY-MAXIMUM-STATE-ID(1)"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-DEFAULT-STATE-ID(1)"));
        assertEquals(1, result.getInt("BLOCKS-MAXIMUM-STATE-ID"));
        assertEquals("minecraft:stone", result.get("BLOCK-NAMES-ENTRY-NAME(1)"));
        assertEquals(1, result.getInt("BLOCK-NAMES-ENTRY-INDEX(1)"));
    }

    @Test
    void TC2_testBlocksParseMultipleBlocks() {
        var inputs = new CobolContext()
            .with("LK-JSON", "{\"minecraft:stone\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"texture\":[\"stone\"]},\"states\":[{\"id\":0,\"default\":true}]},\"minecraft:dirt\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"variant\":[\"dirt\",\"coarse_dirt\",\"podzol\"]},\"states\":[{\"id\":1,\"default\":true},{\"id\":2},{\"id\":3}]}}");

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC2", inputs);

        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(2, result.getInt("BLOCK-COUNT"));
        assertEquals("minecraft:stone", result.get("BLOCK-ENTRY-NAME(1)"));
        assertEquals("minecraft:dirt", result.get("BLOCK-ENTRY-NAME(2)"));
        assertEquals(1, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT(1)"));
        assertEquals(1, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT(2)"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-MAXIMUM-STATE-ID(1)"));
        assertEquals(3, result.getInt("BLOCK-ENTRY-MAXIMUM-STATE-ID(2)"));
        assertEquals(3, result.getInt("BLOCKS-MAXIMUM-STATE-ID"));
    }

    @Test
    void TC3_testBlocksParseMissingFields() {
        // Set up input context
        var inputs = new CobolContext()
                .with("LK-JSON", "{\"minecraft:air\":{\"definition\":{},\"states\":[{\"id\":0,\"default\":true}]}}");

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC3", inputs);

        // Assert the output parameters
        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(1, result.getInt("BLOCK-COUNT"));
        assertEquals("minecraft:air", result.get("BLOCK-ENTRY-NAME(1)"));
        assertEquals("", result.get("BLOCK-ENTRY-TYPE(1)"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT(1)"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-MINIMUM-STATE-ID(1)"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-MAXIMUM-STATE-ID(1)"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-DEFAULT-STATE-ID(1)"));
        assertEquals(0, result.getInt("BLOCKS-MAXIMUM-STATE-ID"));
    }

    @Test
    void TC_4_testBlocksParseInvalidJSON() {
        var inputs = new CobolContext()
                .with("LK-JSON", "{\"minecraft:stone\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"texture\":[\"stone\"]},\"states\":[{\"id\":0,\"default\":true}]")
                .with("LK-JSON-LEN", 104)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC-4", inputs);

        assertEquals(1, result.getInt("LK-FAILURE"));
    }

    @Test
    void TC5_testBlocksParseEmptyInput() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext()
                .with("LK-JSON", "")
                .with("LK-JSON-LEN", 0)
                .with("LK-FAILURE", 0);

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC5", inputs);

        // Assert the output parameters
        assertEquals(1, result.getInt("LK-FAILURE"));
    }

    @Test
    void TC6_testBlocksParseMaximumProperties() {
        var inputs = new CobolContext()
                .with("LK-JSON", "{\"minecraft:complex\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"p1\":[\"v1\"],\"p2\":[\"v2\"],\"p3\":[\"v3\"],\"p4\":[\"v4\"],\"p5\":[\"v5\"],\"p6\":[\"v6\"],\"p7\":[\"v7\"],\"p8\":[\"v8\"],\"p9\":[\"v9\"],\"p10\":[\"v10\"],\"p11\":[\"v11\"],\"p12\":[\"v12\"],\"p13\":[\"v13\"],\"p14\":[\"v14\"],\"p15\":[\"v15\"],\"p16\":[\"v16\"]},\"states\":[{\"id\":0,\"default\":true}]}}")
                .with("LK-JSON-LEN", 298)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse-TC6", inputs);

        assertEquals(0, result.getInt("LK-FAILURE"));
        // Additional assertions for BLOCK-COUNT and BLOCK-ENTRY-NAME can be added here
    }

    @Test
    void TC8_testBlocksParseMaximumBlocks() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext()
            .with("LK-JSON", "{...}")  // Replace with actual JSON string
            .with("LK-JSON-LEN", 10000)  // Replace with actual length
            .with("LK-FAILURE", 0);

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC8", inputs);

        // Assert the output parameters
        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(BLOCKS_CAPACITY, result.getInt("BLOCK-COUNT"));
    }

    @Test
    void TC9_testBlocksParseExceedMaximumBlocks() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext()
                .with("LK-JSON", "{...}") // JSON string with BLOCKS-CAPACITY + 1 blocks
                .with("LK-JSON-LEN", 1000) // Length of the JSON string
                .with("LK-FAILURE", 0);

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParseExceedMaximumBlocks_TC9", inputs);

        // Assert the output parameters
        assertEquals(1, result.getInt("LK-FAILURE"));
    }

    @Test
    void TC_10_testBlocksParseDuplicateBlockNames() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext()
                .with("LK-JSON", "{'minecraft:stone':{'definition':{'type':'solid'},'properties':{'texture':['stone']},'states':[{'id':0,'default':true}]},'minecraft:stone':{'definition':{'type':'solid'},'properties':{'texture':['granite']},'states':[{'id':1,'default':true}]}}")
                .with("LK-JSON-LEN", 212)
                .with("LK-FAILURE", 0);

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC-10", inputs);

        // Assert the output parameters
        assertEquals(1, result.getInt("LK-FAILURE"));
    }
}

