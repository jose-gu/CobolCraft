IDENTIFICATION DIVISION.
PROGRAM-ID. Main-JsonParse.

DATA DIVISION.
WORKING-STORAGE SECTION.
01 SUBPROGRAM-NAME      PIC X(32).
01 INPUT-STR            PIC X(256).
01 OFFSET               BINARY-LONG UNSIGNED.
01 FLAG                 BINARY-CHAR UNSIGNED.
01 VALUE-LONG           BINARY-LONG.
01 VALUE-FLOAT          FLOAT-LONG.
01 VALUE-BOOL           BINARY-CHAR UNSIGNED.
01 VALUE-STR            PIC X(256).

PROCEDURE DIVISION.
    DISPLAY "Main-JsonParse started".
    DISPLAY "Enter subprogram name (e.g. JsonParse-Integer, JsonParse-String):"
    ACCEPT SUBPROGRAM-NAME

    DISPLAY "Enter input string:"
    ACCEPT INPUT-STR

    MOVE 1 TO OFFSET
    MOVE 0 TO FLAG

    EVALUATE SUBPROGRAM-NAME
        WHEN "JsonParse-Integer"
            CALL "JsonParse-Integer" USING INPUT-STR OFFSET FLAG VALUE-LONG
            DISPLAY "FLAG=" FLAG " VALUE=" VALUE-LONG
        WHEN "JsonParse-Float"
            CALL "JsonParse-Float" USING INPUT-STR OFFSET FLAG VALUE-FLOAT
            DISPLAY "FLAG=" FLAG " VALUE=" VALUE-FLOAT
        WHEN "JsonParse-Boolean"
            CALL "JsonParse-Boolean" USING INPUT-STR OFFSET FLAG VALUE-BOOL
            DISPLAY "FLAG=" FLAG " VALUE=" VALUE-BOOL
        WHEN "JsonParse-String"
            CALL "JsonParse-String"
            DISPLAY "Called JsonParse-String"
        WHEN "JsonParse-Null"
            CALL "JsonParse-Null" USING INPUT-STR OFFSET FLAG
            DISPLAY "FLAG=" FLAG
        WHEN OTHER
            DISPLAY "Unknown subprogram: " SUBPROGRAM-NAME
    END-EVALUATE

    GOBACK.
END PROGRAM Main-JsonParse.
