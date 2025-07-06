package com.andersen.islam.hw1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceServiceTest {

    WorkspaceService service = new WorkspaceService();

    @Test
    void testAddWorkspace() {

        service.addWorkspace("Private Room", 100, true);

        ArrayList<Workspace> list = service.getWorkspaces();
        assertEquals(1, list.size());

        Workspace w = list.getFirst();
        assertEquals("Private Room", w.type);
        assertEquals(BigDecimal.valueOf(100.0), w.price);
    }

    @Test
    void testRemoveWorkspace() {
        service.addWorkspace("Meeting Room", 75, true);
        int id = service.getWorkspaces().get(0).id;

        boolean result = service.removeWorkspaceById(id);

        assertTrue(result);
        assertEquals(0, service.getWorkspaces().size());
    }

    @Test
    void testShowAvailableWorkspaces() {
        service.addWorkspace("Booth", 40, true);
        service.addWorkspace("Cabin", 90, true);
        service.setAvailability(1, false);  // mark first as unavailable

        // Capture printed output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        service.showAvailableWorkspaces();

        String output = out.toString();
        assertTrue(output.contains("Cabin"));
        assertFalse(output.contains("Booth"));
    }

    @Test
    void testGetWorkspaceById() throws WorkspaceNotFoundException {
        service.addWorkspace("Focus Pod", 60, true);
        Workspace ws = service.getWorkspaces().getFirst();

        Workspace found = service.getWorkspaceById(ws.id);
        assertEquals(ws.id, found.id);
        assertEquals("Focus Pod", found.type);
    }

    @Test
    void testGetWorkspaceByIdThrows() {
        assertThrows(WorkspaceNotFoundException.class, () -> {
            service.getWorkspaceById(999);
        });
    }

    @Test
    void testSetAvailability() throws WorkspaceNotFoundException {
        service.addWorkspace("Open Space", 30, true);
        Workspace ws = service.getWorkspaces().getFirst();

        service.setAvailability(ws.id, false);
        assertThrows(WorkspaceNotFoundException.class, () -> {
            service.getWorkspaceById(ws.id);
        });

        service.setAvailability(ws.id, true);
        assertEquals(ws.id, service.getWorkspaceById(ws.id).id);
    }

    @Test
    void testSaveAndLoadFromFile() {
        String testFile = "workspaces.txt";

        service.addWorkspace("Lounge", 55, true);
        service.saveToFile();

        WorkspaceService loadedService = new WorkspaceService();
        loadedService.loadFromFile();
        List<Workspace> loaded = loadedService.getWorkspaces();

        assertEquals(1, loaded.size());
        assertEquals("Lounge", loaded.getFirst().type);

        try {
            Files.deleteIfExists(Paths.get(testFile));
        } catch (IOException ignored) {}
    }
}