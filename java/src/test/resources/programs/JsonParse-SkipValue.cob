IDENTIFICATION DIVISION.
PROGRAM-ID. JsonParse-SkipValue.

DATA DIVISION.
WORKING-STORAGE SECTION.
    77 CH PIC X.
    77 DEPTH BINARY-LONG UNSIGNED.
LINKAGE SECTION.
    01 LK-JSON    PIC X ANY LENGTH.
    01 LK-OFFSET  BINARY-LONG UNSIGNED.
    01 LK-FAILURE BINARY-CHAR UNSIGNED.

PROCEDURE DIVISION USING LK-JSON LK-OFFSET LK-FAILURE.
    MOVE 0 TO LK-FAILURE.
    PERFORM SKIP-SPACES.
    MOVE LK-JSON(LK-OFFSET:1) TO CH.
    EVALUATE TRUE
        WHEN CH = '"'
            ADD 1 TO LK-OFFSET
            PERFORM UNTIL LK-JSON(LK-OFFSET:1) = '"'
                ADD 1 TO LK-OFFSET
            END-PERFORM
            ADD 1 TO LK-OFFSET
        WHEN CH = '{'
            MOVE 1 TO DEPTH
            ADD 1 TO LK-OFFSET
            PERFORM UNTIL DEPTH = 0
                MOVE LK-JSON(LK-OFFSET:1) TO CH
                IF CH = '"'
                    ADD 1 TO LK-OFFSET
                    PERFORM UNTIL LK-JSON(LK-OFFSET:1) = '"'
                        ADD 1 TO LK-OFFSET
                    END-PERFORM
                ELSE
                    IF CH = '{'
                        ADD 1 TO DEPTH
                    ELSE
                        IF CH = '}'
                            SUBTRACT 1 FROM DEPTH
                        END-IF
                    END-IF
                END-IF
                ADD 1 TO LK-OFFSET
            END-PERFORM
        WHEN CH = '['
            MOVE 1 TO DEPTH
            ADD 1 TO LK-OFFSET
            PERFORM UNTIL DEPTH = 0
                MOVE LK-JSON(LK-OFFSET:1) TO CH
                IF CH = '"'
                    ADD 1 TO LK-OFFSET
                    PERFORM UNTIL LK-JSON(LK-OFFSET:1) = '"'
                        ADD 1 TO LK-OFFSET
                    END-PERFORM
                ELSE
                    IF CH = '['
                        ADD 1 TO DEPTH
                    ELSE
                        IF CH = ']'
                            SUBTRACT 1 FROM DEPTH
                        END-IF
                    END-IF
                END-IF
                ADD 1 TO LK-OFFSET
            END-PERFORM
        WHEN OTHER
            *> number, boolean, or null - just advance until delimiter
            PERFORM UNTIL LK-OFFSET > FUNCTION LENGTH(LK-JSON)
                MOVE LK-JSON(LK-OFFSET:1) TO CH
                IF CH = ',' OR CH = '}' OR CH = ']' OR CH = SPACE OR CH = X"09" OR CH = X"0A" OR CH = X"0D"
                    EXIT PERFORM
                END-IF
                ADD 1 TO LK-OFFSET
            END-PERFORM
    END-EVALUATE.
    GOBACK.

SKIP-SPACES.
    PERFORM UNTIL LK-OFFSET > FUNCTION LENGTH(LK-JSON)
        MOVE LK-JSON(LK-OFFSET:1) TO CH
        IF CH NOT = SPACE AND CH NOT = X"09" AND CH NOT = X"0A" AND CH NOT = X"0D"
            EXIT PERFORM
        END-IF
        ADD 1 TO LK-OFFSET
    END-PERFORM.
    EXIT.

END PROGRAM JsonParse-SkipValue.

