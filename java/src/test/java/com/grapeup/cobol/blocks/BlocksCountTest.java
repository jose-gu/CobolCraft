package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksCountTest {

    @Test
    void TC_1_testBlocksCountSubprogram() {
        var inputs = new CobolContext().with("BLOCK-COUNT", 5);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-1", inputs);

        assertEquals(5, result.getInt("LK-COUNT"));
    }

    @Test
    void TC_2_testEmptyBlocksStructure() {
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-2", new CobolContext());
        assertEquals(0, result.getInt("LK-COUNT"));
    }

    @Test
    void TC_3_testMaximumBlocksCount() {
        // Maximum capacity for a 16x16x16 block structure
        int blocksCapacity = 4096;

        var inputs = new CobolContext()
                .with("BLOCK-COUNT", blocksCapacity);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-3", inputs);

        assertEquals(blocksCapacity, result.getInt("LK-COUNT"));
    }

    @Test
    void TC_4_testBlockCountAfterAddingEntries() {
        var inputs = new CobolContext()
                .with("BLOCK-COUNT", 3)
                .with("ADDED-BLOCKS", 2);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-4", inputs);

        assertEquals(5, result.getInt("LK-COUNT"));
    }

    @Test
    void TC_5_testBlockCountAfterRemovingEntries() {
        // After removing 3 from initial 10, the resulting BLOCK-COUNT should be 7
        var inputs = new CobolContext()
                .with("BLOCK-COUNT", 7);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-5", inputs);

        assertEquals(7, result.getInt("LK-COUNT"));
    }
}

