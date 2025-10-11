package com.example.warehouse;

import java.time.LocalDate;

public interface Perishable {
    LocalDate expirationDate();
    //Addera default isExpired, baserat på LocalDate.now()

}
