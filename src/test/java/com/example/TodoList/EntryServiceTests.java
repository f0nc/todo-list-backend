package com.example.TodoList;

import com.example.TodoList.entry.Entry;
import com.example.TodoList.entry.EntryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EntryServiceTests {
    private static final String EXPECTED_USERNAME = "expectedUser";

    @Autowired
    private EntryService entryService;

    @Test
    @WithMockUser(EXPECTED_USERNAME)
    public void save_ShouldBeAbleToSaveNewEntries() {
        List<Entry> entries = entryService.list();
        assertEquals(0, entries.size());

        Entry newEntry = new Entry("foo", "bar");
        entryService.save(newEntry);

        List<Entry> updatedEntries = entryService.list();
        assertEquals(1, updatedEntries.size());
    }

    @Test
    @WithMockUser(EXPECTED_USERNAME)
    public void listUserEntries_ShouldReturnOnlyAuthenticatedUserEntries() {
        String username = EXPECTED_USERNAME;
        List<Entry> expected = List.of(
                new Entry(username, "bar"),
                new Entry(username, "foo")
        );
        List<Entry> notExpectedEntries = List.of(
                new Entry("different-user", "bar"),
                new Entry("different-user", "foo")
        );

        ArrayList<Entry> allEntries = new ArrayList<>();
        allEntries.addAll(expected);
        allEntries.addAll(notExpectedEntries);

        for (Entry entry : allEntries) {
           entryService.save(entry);
        }

        List<Entry> actual = entryService.listUserEntries();

        for (Entry expectedEntry : expected) {
            assertTrue(actual.contains(expectedEntry));
        }

        for (Entry notExpectedEntry : notExpectedEntries) {
            assertFalse(actual.contains(notExpectedEntry));
        }
    }

    @Test
    @WithMockUser(EXPECTED_USERNAME)
    public void findById_ShouldBeAbleToFindEntryWithId() {
        Entry expected = new Entry("xoo", "bar");
        assertNull(expected.getId());

        entryService.save(expected);
        assertNotNull(expected.getId());

        Optional<Entry> actual = entryService.findById(expected.getId());
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), expected);
    }

    @Test
    @WithMockUser(EXPECTED_USERNAME)
    public void tests_ShouldStartWithEmptyDatabase() {
        // With @Transactional annotation on the class, DB changes
        // should be rolled back after each test, so database should be empty here
        List<Entry> entries = entryService.list();
        assertEquals(0, entries.size());
    }
}
