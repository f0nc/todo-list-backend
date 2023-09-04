package com.example.TodoList.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@PreAuthorize("isAuthenticated()")
public class EntryService {
    @Autowired
    private EntryRepository entryRepository;

    public List<Entry> listUserEntries() {
        String username = getUsername();

        return entryRepository.findByUsername(username);
    }

    public void save(Entry entry) {
        entryRepository.save(entry);
    }

    public Optional<Entry> findById(Long id) {
        return entryRepository.findById(id);
    }

    private String getUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }
}
