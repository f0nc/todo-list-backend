package com.example.TodoList.entry;

import com.example.TodoList.dto.ErrorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class EntryController {
    @Autowired
    private EntryService entryService;

    @GetMapping("entry")
    public List<Entry> entry() {
        return entryService.list();
    }

    @PostMapping("entry")
    @ResponseStatus(HttpStatus.CREATED)
     public Entry saveEntry(@RequestBody Entry entry) {
        return entryService.save(entry);
    }

    @DeleteMapping("/entry/{id}")
    public void deleteEntry(@PathVariable("id") Long id) {
        entryService.delete(id);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handler(ConstraintViolationException exception) {
        HashMap<String, ArrayList<String>> allViolations = new HashMap<>();

        for (ConstraintViolation c : exception.getConstraintViolations()) {
            String path = c.getPropertyPath().toString();

            if (!allViolations.containsKey(path)) {
                allViolations.put(path, new ArrayList<>());
            }

            allViolations.get(path).add(c.getMessage());
        }

        String title = exception.getMessage();
        ErrorDto dto = new ErrorDto(title, allViolations);

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
