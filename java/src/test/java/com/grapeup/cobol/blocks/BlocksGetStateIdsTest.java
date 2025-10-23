package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksGetStateIdsTest {

    @Test
    void TC_1_testBlocksGetStateIdsSubprogram() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext().with("LK-BLOCK-ID", 0);

        // Run the Main program (note: no file extension, executable name only)
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetStateIds-TC-1", inputs);

        // Assert the output parameters
        assertEquals(result.getInt("LK-MINIMUM-ID"), 1);  // Expected value for BLOCK-ENTRY-MINIMUM-STATE-ID(1)
        assertEquals(result.getInt("LK-MAXIMUM-ID"), 1);  // Expected value for BLOCK-ENTRY-MAXIMUM-STATE-ID(1)
    }

    @Test
    void TC_2_testBlocksGetStateIds() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext().with("LK-BLOCK-ID", 4); // Assuming BLOCK-COUNT is 5

        // Run the Main program (note: no file extension, executable name only)
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetStateIds-TC-2", inputs);

        // Assert the output parameters
        assertEquals(100, result.getInt("LK-MINIMUM-ID")); // Expected minimum state ID for last block
        assertEquals(200, result.getInt("LK-MAXIMUM-ID")); // Expected maximum state ID for last block
    }

    @Test
    void TC_3_testBlocksGetStateIdsWithLargeRange() {
        // Set up input context
        var inputs = new CobolContext().with("LK-BLOCK-ID", 1);

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetStateIds-TC-3", inputs);

        // Assert the output parameters
        assertEquals(100000, result.getInt("LK-MINIMUM-ID"));
        assertEquals(200000, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC_4_testBlocksGetStateIdsWithSingleState() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext().with("LK-BLOCK-ID", 0);

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetStateIds_TC-4", inputs);

        // Assert the output parameters
        assertEquals(result.getInt("LK-MINIMUM-ID"), result.getInt("LK-MAXIMUM-ID"));
        // Assuming the single state ID is known, replace 1 with the expected state ID
        assertEquals(1, result.getInt("LK-MINIMUM-ID"));
    }

    @Test
    void TC_5_testInvalidBlockIdHandling() {
        // Set up input context with invalid block ID
        var inputs = new CobolContext().with("LK-BLOCK-ID", 9999); // Assuming 9999 is invalid

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetStateIds-TC-5", inputs);

        // Assert the output parameters - implementation-specific
        // Example: Check if the output parameters remain unchanged or set to default values
        assertEquals(0, result.getInt("LK-MINIMUM-ID"));
        assertEquals(0, result.getInt("LK-MAXIMUM-ID"));
    }
}

