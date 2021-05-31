package com.example.application.backend.slack;


import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter
@Setter
public class Message implements Serializable {
    private String username;
    private String text;
}