package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksCountTest {

    @Test
    void TC_1_testBlocksCountSubprogram() {
        // Set up input context - environment variables for the Main program
        var inputs = new CobolContext().with("BLOCK-COUNT", 5);

        // Run the Main program (note: no file extension, executable name only)
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount", inputs);

        // Assert the output parameters
        assertEquals(5, result.getInt("LK-COUNT"));
    }

    @Test
    void TC_2_testEmptyBlocksStructure() {
        // Set up input context - no input parameters needed for this test
        var inputs = new CobolContext();

        // Run the Main program (note: no file extension, executable name only)
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount-TC-2", inputs);

        // Assert the output parameters
        assertEquals(0, result.getInt("LK-COUNT"));
    }

    @Test
    void TC_3_testMaximumBlocksCount() {
        // Set up input context - no inputs needed for this test case
        var inputs = new CobolContext();

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-3", inputs);

        // Assert the output parameters
        assertEquals(1000, result.getInt("LK-COUNT")); // Assuming BLOCKS-CAPACITY is 1000
    }

    @Test
    void TC4_testBlockCountAfterAddingEntries() {
        // No input parameters needed for this test
        var inputs = new CobolContext();

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC4", inputs);

        // Assert the output parameters
        assertEquals(5, result.getInt("LK-COUNT"));
    }

    @Test
    void TC_5_testBlocksCountAfterRemovingEntries() {
        // Set up input context - no inputs needed for this test
        var inputs = new CobolContext();

        // Run the Main program
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-5", inputs);

        // Assert the output parameters
        assertEquals(7, result.getInt("LK-COUNT"));
    }
}

