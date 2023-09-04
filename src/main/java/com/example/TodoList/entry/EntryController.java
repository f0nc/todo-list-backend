package com.example.TodoList.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntryController {
    @Autowired
    private EntryService entryService;

    @GetMapping("entry")
    public List<Entry> entry() {
        return entryService.list();
    }
}
