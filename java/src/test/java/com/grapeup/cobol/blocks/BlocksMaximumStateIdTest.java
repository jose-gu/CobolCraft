package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksMaximumStateIdTest {
    @Test
    void TC_1_testBlocksMaximumStateId() {
        // Set up input context - no inputs needed for this test
        var inputs = new CobolContext();

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksMaximumStateId_TC-1", inputs);

        // Assert the output parameters
        assertEquals(1000, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC2_testBlocksMaximumStateIdSubprogram() {
        // Set up input context - no inputs needed for this test case
        var inputs = new CobolContext();

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksMaximumStateId_TC2", inputs);

        // Assert the output parameters
        assertEquals(0, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC3_testRetrieveMaximumStateIdAfterAddingBlocks() {
        // Set up input context - no inputs needed for this test
        var inputs = new CobolContext();

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksMaximumStateId_TC3", inputs);

        // Assert the output parameters
        assertEquals(2500, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC4_testBlocksMaximumStateId() {
        // No input parameters needed for this test
        var inputs = new CobolContext();

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksMaximumStateId-TC4", inputs);

        // Assert the output parameter
        assertEquals(Integer.MAX_VALUE, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC5_testRetrieveMaximumStateIdAfterBlockRemoval() {
        // Set up input context - no inputs needed for this test
        var inputs = new CobolContext();

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksMaximumStateId_TC5", inputs);

        // Assert the output parameters
        assertEquals(1200, result.getInt("LK-MAXIMUM-ID"));
    }
}

