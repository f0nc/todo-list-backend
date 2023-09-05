package com.example.TodoList.dto;

import java.util.ArrayList;
import java.util.HashMap;

public record ErrorDto(String title, HashMap<String, ArrayList<String>> errors) { }
