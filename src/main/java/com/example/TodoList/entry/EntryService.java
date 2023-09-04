package com.example.TodoList.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PreAuthorize("isAuthenticated()")
public class EntryService {
    @Autowired
    private EntryRepository entryRepository;

    public List<Entry> list() {
        String username = getUsername();

        return entryRepository.findByUsername(username);
    }

    public void save(Entry entry) {
        entryRepository.save(entry);
    }

    private String getUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }
}
