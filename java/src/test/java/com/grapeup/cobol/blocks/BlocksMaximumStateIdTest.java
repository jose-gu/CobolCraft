package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksMaximumStateIdTest {
    @Test
    void TC_1_testRetrieveMaximumStateId() {
        var inputs = new CobolContext().with("BLOCKS-MAXIMUM-STATE-ID", 1000);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksMaximumStateId-TC-1", inputs);

        assertEquals(1000, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC_2_RetrieveMaximumStateIdAfterInitialization() {
        var inputs = new CobolContext();

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-MaximumStateId_TC-2", inputs);

        assertEquals(0, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC_3_testRetrieveMaximumStateIdAfterAddingBlocks() {
        var inputs = new CobolContext();

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-MaximumStateId_TC-3", inputs);

        assertEquals(2500, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC_4_testBlocksMaximumStateId() {
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksMaximumStateId_TC-4", new CobolContext());
        assertEquals(2147483647, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC_5_retrieveMaximumStateIdAfterRemoval() {
        var inputs = new CobolContext();

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksMaximumStateId_TC-5", inputs);

        assertEquals(1200, result.getInt("LK-MAXIMUM-ID"));
    }
}

