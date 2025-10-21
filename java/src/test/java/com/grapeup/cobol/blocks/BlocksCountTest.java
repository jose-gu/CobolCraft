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
        var inputs = new CobolContext().with("BLOCK-COUNT", 0);
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-2", inputs);
        assertEquals(0, result.getInt("LK-COUNT"));
    }

    @Test
    void TC_3_testMaximumBlocksCount() {
        var inputs = new CobolContext();
        var result = CobolProgramRunner.runBuiltProgram("programs/blocks/MainBlocksCount_TC-3", inputs);
        assertEquals(result.getInt("BLOCKS-CAPACITY"), result.getInt("LK-COUNT"));
    }

    @Test
    void TC_4_testBlockCountAfterAddingEntries() {
        var inputs = new CobolContext()
                .with("INITIAL-BLOCK-COUNT", 3)
                .with("ADD-BLOCKS", 2);
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-4", inputs);
        assertEquals(5, result.getInt("LK-COUNT"));
    }

    @Test
    void TC_5_testBlockCountAfterRemovingEntries() {
        var inputs = new CobolContext();
        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksCount_TC-5", inputs);
        assertEquals(7, result.getInt("LK-COUNT"));
    }
}