package ru.tuxoft.secure;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Principal {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

}
