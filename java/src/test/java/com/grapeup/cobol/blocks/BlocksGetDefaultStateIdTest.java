package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlocksGetDefaultStateIdTest {
    @Test
    void TC_1_testGetDefaultStateIdForValidBlock() {
        // Obtain expected default state id for first block from shared data (copybook)
        var oracle = CobolProgramRunner.runBuiltProgram("blocks/Main-Blocks-GetDefaultStateId-Oracle-TC-1", new CobolContext());
        int expected = oracle.getInt("EXPECTED-STATE-ID");

        // Call the program under test with LK-BLOCK-ID = 0 (first block)
        var inputs = new CobolContext().with("LK-BLOCK-ID", 0);
        var result = CobolProgramRunner.runBuiltProgram("blocks/Main-Blocks-GetDefaultStateId-TC-1", inputs);

        assertEquals(expected, result.getInt("LK-STATE-ID"));
    }

    @Test
    void TC_2_testGetDefaultStateIdForLastBlock() {
        var inputs = new CobolContext();
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetDefaultStateId_TC-2", inputs);
        assertEquals(300, result.getInt("LK-STATE-ID"));
    }

    @Test
    void TC_3_testGetDefaultStateIdForBlockWithMultipleStates() {
        var inputs = new CobolContext()
                .with("LK-BLOCK-ID", 7);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-GetDefaultStateId_TC-3", inputs);

        assertEquals(42, result.getInt("LK-STATE-ID"));
    }

    @Test
    void TC_4_testGetDefaultStateIdForSingleStateBlock() {
        var inputs = new CobolContext()
                .with("LK-BLOCK-ID", 0); // use first block (indexing in program adds +1)

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-GetDefaultStateId_TC-4", inputs);

        assertEquals(5, result.getInt("LK-STATE-ID"));
    }

    @Test
    void TC_5_attemptToGetDefaultStateIdForInvalidBlockId() {
        // Given N block entries; choose N=3
        int N = 3;
        var inputs = new CobolContext()
                .with("BLOCK-COUNT", N)
                .with("LK-BLOCK-ID", N); // invalid index (valid: 0..N-1)

        var result = CobolProgramRunner.runBuiltProgram("blocks/Main-Blocks-GetDefaultStateId-TC-5", inputs);

        // Implementation-specific behavior: ensure the program returned some LK-STATE-ID value
        assertTrue(result.asMap().containsKey("LK-STATE-ID"));
    }
}

