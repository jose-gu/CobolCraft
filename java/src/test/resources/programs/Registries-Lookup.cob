IDENTIFICATION DIVISION.
       PROGRAM-ID. Registries-Lookup.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-REGISTRY-NAME   PIC X(64).
       01 WS-ENTRY-NAME      PIC X(64).
       LINKAGE SECTION.
       01 LK-REGISTRY        PIC X(64).
       01 LK-NAME            PIC X(64).
       01 LK-ID              BINARY-LONG.
       PROCEDURE DIVISION USING LK-REGISTRY LK-NAME LK-ID.
           MOVE LK-REGISTRY TO WS-REGISTRY-NAME.
           MOVE LK-NAME     TO WS-ENTRY-NAME.

           IF WS-REGISTRY-NAME = "minecraft:block"
              EVALUATE WS-ENTRY-NAME
                 WHEN "minecraft:stone"
                    MOVE 0 TO LK-ID
                 WHEN "minecraft:dirt"
                    MOVE 1 TO LK-ID
                 WHEN OTHER
                    MOVE -1 TO LK-ID
              END-EVALUATE
           ELSE
              MOVE -1 TO LK-ID
           END-IF.

           GOBACK.
       END PROGRAM Registries-Lookup.

