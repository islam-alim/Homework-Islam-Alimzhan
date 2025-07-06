package com.andersen.islam.hw1;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class WorkspaceTest {

    @Test
    void testFromString() {
        Workspace ws = Workspace.fromString("1,Private Room,100,true");
        assertEquals(1, ws.id);
        assertEquals("Private Room", ws.type);
        assertEquals(BigDecimal.valueOf(100), ws.price);
        assertTrue(ws.available);
    }

    @Test
    void testToString() {
        Workspace ws = new Workspace(1, "Community Room", BigDecimal.valueOf(300));
        assertEquals("ID: 1, Type: Community Room, Price: 300, Available: true", ws.toString());
    }

}