package ru.kata.spring_boot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String lastName;
    private Byte age;
}
