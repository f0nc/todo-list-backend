package com.example.TodoList;

import com.example.TodoList.entry.Entry;
import com.example.TodoList.entry.EntryRepository;
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
    private EntryService target;

    @Autowired
    private EntryRepository entryRepository;

    @Test
    @WithMockUser(EXPECTED_USERNAME)
    public void save_ShouldBeAbleToSaveNewEntries() {
        List<Entry> entries = entryRepository.findAll();
        assertEquals(0, entries.size());

        Entry newEntry = new Entry("foo", "bar");
        target.save(newEntry);

        List<Entry> updatedEntries = entryRepository.findAll();
        assertEquals(1, updatedEntries.size());
    }

    @Test
    @WithMockUser(EXPECTED_USERNAME)
    public void save_ShouldSetEntryUsernameAsCurrentUser() {
        Entry entry = new Entry("description");

        target.save(entry);
        assertNotNull(entry.getId());

        Optional<Entry> queryResult = entryRepository.findById(entry.getId());
        assertTrue(queryResult.isPresent());

        String actualUsername = queryResult.get().getUsername();
        assertEquals(EXPECTED_USERNAME, actualUsername);
    }

    @Test
    @WithMockUser(EXPECTED_USERNAME)
    public void save_ShouldReplaceEntryUsernameWithCurrentUser() {
        Entry entryWithMaliciousUsername = new Entry("someone-else", "description");

        target.save(entryWithMaliciousUsername);
        assertNotNull(entryWithMaliciousUsername.getId());

        Optional<Entry> queryResult = entryRepository.findById(entryWithMaliciousUsername.getId());
        assertTrue(queryResult.isPresent());

        String actualUsername = queryResult.get().getUsername();
        assertEquals(EXPECTED_USERNAME, actualUsername);
    }

    @Test
    @WithMockUser(EXPECTED_USERNAME)
    public void list_ShouldReturnOnlyAuthenticatedUserEntries() {
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
           entryRepository.save(entry);
        }

        List<Entry> actual = target.list();

        for (Entry expectedEntry : expected) {
            assertTrue(actual.contains(expectedEntry));
        }

        for (Entry notExpectedEntry : notExpectedEntries) {
            assertFalse(actual.contains(notExpectedEntry));
        }
    }

    @Test
    public void tests_ShouldStartWithEmptyDatabase() {
        // With @Transactional annotation on the class, DB changes
        // should be rolled back after each test, so database should be empty here
        List<Entry> entries = entryRepository.findAll();
        assertEquals(0, entries.size());
    }
}
