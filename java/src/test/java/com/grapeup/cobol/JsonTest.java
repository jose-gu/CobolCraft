package com.grapeup.cobol;

import com.grapeup.cobol.support.CobolContext;
import com.grapeup.cobol.support.CobolProgramRunner;
import com.grapeup.cobol.support.CobolProgramRunnerJson;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTest {

    @Test
    void testBlocksCountSubprogram() {
        var inputs = new CobolContext().with("BLOCK-COUNT",  valueOf(5));

        var result = CobolProgramRunnerJson.runBuiltProgram("MainJson2", inputs);

        assertEquals(5, result.getInt("LK-COUNT"));
    }
}
