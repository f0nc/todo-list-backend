package com.example.TodoList;

import com.example.TodoList.entry.Entry;
import com.example.TodoList.entry.EntryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EntryControllerTests {
    private static final String EXPECTED_USERNAME = "expectedUser";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntryRepository entryRepository;

    @Test
    @WithMockUser(EXPECTED_USERNAME)
    public void getEntry_ShouldReturnUsersEntries() throws Exception {
        Entry expectedEntry = new Entry(EXPECTED_USERNAME, "foobar");
        Entry notExpectedEntry = new Entry("different-user", "foobar");
        entryRepository.save(expectedEntry);
        entryRepository.save(notExpectedEntry);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(
                List.of(expectedEntry)
        );

        mvc.perform(get("/entry"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }
}
