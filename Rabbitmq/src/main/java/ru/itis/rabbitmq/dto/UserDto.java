package ru.itis.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String first_name;
    private String last_name;
    private String numberOfpassport;
    private String age;
    private String date;

    public UserDto(ArrayList<String> data) {
        first_name = data.get(0);
        last_name = data.get(1);
        numberOfpassport = data.get(2);
        age = data.get(3);
        date = data.get(4);
    }
}
