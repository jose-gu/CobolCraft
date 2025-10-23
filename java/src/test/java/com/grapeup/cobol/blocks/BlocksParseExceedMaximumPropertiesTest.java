package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksParseExceedMaximumPropertiesTest {
    @Test
    void TC7_testBlocksParseExceedMaximumProperties() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext()
            .with("LK-JSON", "{\"minecraft:toomany\":{\"definition\":{\"type\":\"solid\"},\"properties\":{\"p1\":[\"v1\"],\"p2\":[\"v2\"],\"p3\":[\"v3\"],\"p4\":[\"v4\"],\"p5\":[\"v5\"],\"p6\":[\"v6\"],\"p7\":[\"v7\"],\"p8\":[\"v8\"],\"p9\":[\"v9\"],\"p10\":[\"v10\"],\"p11\":[\"v11\"],\"p12\":[\"v12\"],\"p13\":[\"v13\"],\"p14\":[\"v14\"],\"p15\":[\"v15\"],\"p16\":[\"v16\"],\"p17\":[\"v17\"]},\"states\":[{\"id\":0,\"default\":true}]}}")
            .with("LK-JSON-LEN", 316)
            .with("LK-FAILURE", 0);

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksParseExceedMaximumProperties-TC7", inputs);

        // Assert the output parameters
        assertEquals(0, result.getInt("LK-FAILURE"));
        assertEquals(1, result.getInt("BLOCK-COUNT"));
        assertEquals("minecraft:toomany", result.get("BLOCK-ENTRY-NAME(1)"));
        assertEquals(16, result.getInt("BLOCK-ENTRY-PROPERTY-COUNT(1)"));
    }
}

