package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksGetDefaultStateIdTest {

    // Replace this value with the actual expected value for TC_3 if known
    private static final int expectedDefaultStateId = 0;

    @Test
    void TC_1_testBlocksGetDefaultStateId() {
        // Set up input context
        var inputs = new CobolContext().with("LK-BLOCK-ID", 0);

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetDefaultStateId_TC-1", inputs);

        // Assert the output parameters
        assertEquals(1, result.getInt("LK-STATE-ID")); // Assuming default state ID for block 0 is 1
    }

    @Test
    void TC2_testGetDefaultStateIdForLastBlock() {
        // Set up input context
        var inputs = new CobolContext().with("LK-BLOCK-ID", 4); // Assuming BLOCK-COUNT is 5

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetDefaultStateId_TC2", inputs);

        // Assert the output parameters
        assertEquals(2, result.getInt("LK-STATE-ID")); // Assuming default state ID for last block is 2
    }

    @Test
    void TC_3_testBlocksGetDefaultStateId() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext().with("LK-BLOCK-ID", 1); // Assuming block ID 1 has multiple states

        // Run the Main program (note: no file extension, executable name only)
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetDefaultStateId-TC-3", inputs);

        // Assert the output parameters
        assertEquals(expectedDefaultStateId, result.getInt("LK-STATE-ID")); // Replace expectedDefaultStateId with the actual expected value
    }

    @Test
    void TC_4_testGetDefaultStateIdForBlockWithSingleState() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext().with("LK-BLOCK-ID", 0);

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetDefaultStateId_TC-4", inputs);

        // Assert the output parameters
        assertEquals(0, result.getInt("LK-STATE-ID"));
    }

    @Test
    void TC_5_testBlocksGetDefaultStateIdWithInvalidBlockId() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext().with("LK-BLOCK-ID", 10); // Assuming N is 10 for this test case

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetDefaultStateId-TC-5", inputs);

        // Assert the output parameters
        // Expected behavior is implementation-specific, here we assume LK-STATE-ID remains unchanged or set to a sentinel value
        // Replace 0 with the expected sentinel value or unchanged state
        assertEquals(0, result.getInt("LK-STATE-ID"));
    }
}

