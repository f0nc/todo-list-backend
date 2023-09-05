package com.example.TodoList.entry;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@PreAuthorize("isAuthenticated()")
public class EntryService {
    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private Validator validator;

    public List<Entry> list() {
        String username = getUsername();

        return entryRepository.findByUsername(username);
    }

    public Entry save(Entry entry) throws ConstraintViolationException {
        Set<ConstraintViolation<Entry>> violations = validator.validate(entry);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Entry validation error", violations);
        }

        String username = getUsername();

        entry.setUsername(username);

        return entryRepository.save(entry);
    }

    private String getUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }
}
