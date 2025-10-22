package com.grapeup.cobol.blocks;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlocksGetStateIdsTest {

    @Test
    void TC_1_testBlocksGetStateIdsSubprogram() {
        var inputs = new CobolContext().with("LK-BLOCK-ID", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetStateIds_TC-1", inputs);

        assertEquals(123, result.getInt("LK-MINIMUM-ID"));
        assertEquals(456, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC_2_testGetStateIdsForLastBlock() {
        var inputs = new CobolContext()
                .with("LK-BLOCK-ID", 2); // last block (0-based) for our initialized 3 entries

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetStateIds_TC-2", inputs);

        assertEquals(30, result.getInt("LK-MINIMUM-ID"));
        assertEquals(39, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC_3_testGetStateIdsForBlockWithLargeRange() {
        int blockId = 7; // index used in Main to set large range
        var inputs = new CobolContext().with("LK-BLOCK-ID", blockId);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-GetStateIds_TC-3", inputs);

        assertEquals(123456789, result.getInt("LK-MINIMUM-ID"));
        assertEquals(2000000000, result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC_4_getStateIdsForSingleStateBlock() {
        var inputs = new CobolContext().with("LK-BLOCK-ID", 0);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocks-GetStateIds_TC-4", inputs);

        assertEquals(777, result.getInt("LK-MINIMUM-ID"));
        assertEquals(777, result.getInt("LK-MAXIMUM-ID"));
        assertEquals(result.getInt("LK-MINIMUM-ID"), result.getInt("LK-MAXIMUM-ID"));
    }

    @Test
    void TC_5_testGetStateIdsInvalidBlockId() {
        var inputs = new CobolContext()
                .with("LK-BLOCK-ID", 5);

        var result = CobolProgramRunner.runBuiltProgram("blocks/MainBlocksGetStateIds_TC-5", inputs);

        // Expect default/unchanged values (implementation-specific handling assumed as zeros)
        assertEquals(0, result.getInt("LK-MINIMUM-ID"));
        assertEquals(0, result.getInt("LK-MAXIMUM-ID"));
    }
}

