package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksParseTest {
    private static int asciiSum(String s) {
        return s.chars().sum();
    }

    @Test
    void TC_1_testBlocksParseValid() {
        String json = "{\"minecraft:stone\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"texture\":[\"stone\",\"cobblestone\"]},\"states\":[{\"id\":0,\"default\":true},{\"id\":1}]}}";
        var inputs = new CobolContext()
                .with("LK-JSON", json)
                .with("LK-JSON-LEN", 130)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-Parse_TC-1", inputs);

        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(1, result.getInt("BLOCK-COUNT"));

        assertEquals(asciiSum("minecraft:stone"), result.getInt("BLOCK-ENTRY-NAME-1-HASH"));
        assertEquals(asciiSum("solid"), result.getInt("BLOCK-ENTRY-TYPE-1-HASH"));

        assertEquals(1, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT-1"));
        assertEquals(asciiSum("texture"), result.getInt("BLOCK-ENTRY-PROPERTY-NAME-1-1-HASH"));
        assertEquals(2, result.getInt("BLOCK-ENTRY-PROPERTY-VALUE-COUNT-1-1"));
        assertEquals(asciiSum("stone"), result.getInt("BLOCK-ENTRY-PROPERTY-VALUE-1-1-1-HASH"));
        assertEquals(asciiSum("cobblestone"), result.getInt("BLOCK-ENTRY-PROPERTY-VALUE-1-1-2-HASH"));

        assertEquals(0, result.getInt("BLOCK-ENTRY-MINIMUM-STATE-ID-1"));
        assertEquals(1, result.getInt("BLOCK-ENTRY-MAXIMUM-STATE-ID-1"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-DEFAULT-STATE-ID-1"));
        assertEquals(1, result.getInt("BLOCKS-MAXIMUM-STATE-ID"));

        assertEquals(asciiSum("minecraft:stone"), result.getInt("BLOCK-NAMES-ENTRY-NAME-1-HASH"));
        assertEquals(1, result.getInt("BLOCK-NAMES-ENTRY-INDEX-1"));
    }

    @Test
    void TC_2_testBlocksParseMultipleBlocks() {
        String json = "{\"minecraft:stone\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"texture\":[\"stone\"]},\"states\":[{\"id\":0,\"default\":true}]},\"minecraft:dirt\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"variant\":[\"dirt\",\"coarse_dirt\",\"podzol\"]},\"states\":[{\"id\":1,\"default\":true},{\"id\":2},{\"id\":3}]}}";

        var inputs = new CobolContext()
                .with("LK-JSON", json)
                .with("LK-JSON-LEN", 268)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC-2", inputs);

        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(2, result.getInt("BLOCK-COUNT"));

        assertEquals(1, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT-1"));
        assertEquals(1, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT-2"));

        assertEquals(0, result.getInt("BLOCK-ENTRY-MAXIMUM-STATE-ID-1"));
        assertEquals(3, result.getInt("BLOCK-ENTRY-MAXIMUM-STATE-ID-2"));

        assertEquals(3, result.getInt("BLOCKS-MAXIMUM-STATE-ID"));

        // Sorted names: "minecraft:dirt" < "minecraft:stone"
        // So by-name entries should map to indices 2 then 1
        assertEquals(2, result.getInt("BLOCK-NAMES-ENTRY-INDEX-1"));
        assertEquals(1, result.getInt("BLOCK-NAMES-ENTRY-INDEX-2"));
    }

    @Test
    void TC_3_testBlocksParseMissingFields() {
        var json = "{\"minecraft:air\":{\"definition\":{},\"states\":[{\"id\":0,\"default\":true}]}}";

        var inputs = new CobolContext()
                .with("LK-JSON", json)
                .with("LK-JSON-LEN", 63)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC-3", inputs);

        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(1, result.getInt("BLOCK-COUNT"));
        assertEquals(1, result.getInt("BLOCK-ENTRY-NAME-IS-AIR"));
        assertEquals(1, result.getInt("BLOCK-ENTRY-TYPE-IS-SPACES"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-MINIMUM-STATE-ID"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-MAXIMUM-STATE-ID"));
        assertEquals(0, result.getInt("BLOCK-ENTRY-DEFAULT-STATE-ID"));
        assertEquals(0, result.getInt("BLOCKS-MAXIMUM-STATE-ID"));
    }

    @Test
    void TC_4_BlocksParseInvalidJSON() {
        var inputs = new CobolContext()
                .with("LK-JSON", "{\"minecraft:stone\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"texture\":[\"stone\"]},\"states\":[{\"id\":0,\"default\":true}]")
                .with("LK-JSON-LEN", 104)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParse_TC-4", inputs);

        assertEquals(1, result.getInt("LK-FAILURE"));
    }

    @Test
    void TC_5_testBlocksParseEmptyInput() {
        var inputs = new CobolContext()
                .with("LK-JSON", "")
                .with("LK-JSON-LEN", 0)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-Parse_TC-5", inputs);

        assertEquals(1, result.getInt("LK-FAILURE"));
        assertEquals(0, result.getInt("BLOCK-COUNT"));
        assertEquals(0, result.getInt("BLOCKS-MAXIMUM-STATE-ID"));
    }

    @Test
    void TC_6_testBlocksParseMaximumProperties() {
        String json = "{\"minecraft:complex\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"p1\":[\"v1\"],\"p2\":[\"v2\"],\"p3\":[\"v3\"],\"p4\":[\"v4\"],\"p5\":[\"v5\"],\"p6\":[\"v6\"],\"p7\":[\"v7\"],\"p8\":[\"v8\"],\"p9\":[\"v9\"],\"p10\":[\"v10\"],\"p11\":[\"v11\"],\"p12\":[\"v12\"],\"p13\":[\"v13\"],\"p14\":[\"v14\"],\"p15\":[\"v15\"],\"p16\":[\"v16\"]},\"states\":[{\"id\":0,\"default\":true}]}}";

        var inputs = new CobolContext()
                .with("LK-JSON", json)
                .with("LK-JSON-LEN", 298)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/Main-Blocks-Parse-TC-6", inputs);

        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(1, result.getInt("BLOCK-COUNT"));
        assertEquals(16, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT-1"));
        assertEquals(1, result.getInt("NAME-MATCH"));
    }

    @Test
    void TC_7_testBlocksParseExceedMaximumProperties() {
        String json = "{\"minecraft:toomany\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"p1\":[\"v1\"],\"p2\":[\"v2\"],\"p3\":[\"v3\"],\"p4\":[\"v4\"],\"p5\":[\"v5\"],\"p6\":[\"v6\"],\"p7\":[\"v7\"],\"p8\":[\"v8\"],\"p9\":[\"v9\"],\"p10\":[\"v10\"],\"p11\":[\"v11\"],\"p12\":[\"v12\"],\"p13\":[\"v13\"],\"p14\":[\"v14\"],\"p15\":[\"v15\"],\"p16\":[\"v16\"],\"p17\":[\"v17\"]},\"states\":[{\"id\":0,\"default\":true}]}}";

        var inputs = new CobolContext()
                .with("LK-JSON", json)
                .with("LK-JSON-LEN", json.length())
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-Parse_TC-7", inputs);

        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(1, result.getInt("BLOCK-COUNT"));
        assertEquals(1, result.getInt("CHECK-NAME-OK"));
        assertEquals(16, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT-1"));
    }

    @Test
    void TC_8_BlocksParseMaximumBlocks() {
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-Parse_TC-8", new CobolContext());

        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(result.getInt("BLOCKS-CAPACITY"), result.getInt("BLOCK-COUNT"));
    }

    @Test
    void TC_9_testBlocksParseExceedMaximumBlocks() {
        var inputs = new CobolContext()
                .with("LK-JSON", "{}")
                .with("LK-JSON-LEN", 2)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-Parse_TC-9", inputs);

        assertEquals(1, result.getInt("LK-FAILURE"));
        assertEquals(result.getInt("BLOCKS-CAPACITY"), result.getInt("BLOCK-COUNT"));
    }

    @Test
    void TC_10_testBlocksParseDuplicateBlockNames() {
        String json = "{\"minecraft:stone\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"texture\":[\"stone\"]},\"states\":[{\"id\":0,\"default\":true}]},\"minecraft:stone\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"texture\":[\"granite\"]},\"states\":[{\"id\":1,\"default\":true}]}}";

        var inputs = new CobolContext()
                .with("LK-JSON", json)
                .with("LK-JSON-LEN", 212)
                .with("LK-FAILURE", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-Parse_TC-10", inputs);

        assertEquals(1, result.getInt("LK-FAILURE"));
        // BLOCKS structure should remain unchanged
        assertEquals(0, result.getInt("BLOCK-COUNT"));
    }
}

