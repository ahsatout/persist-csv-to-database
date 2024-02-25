package com.ahsatout.csvupload.user;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class UserCsvRepresentation {

    @CsvBindByName(column = "firstname")
    private String fname;
    @CsvBindByName(column = "lastname")
    private String lname;
    @CsvBindByName(column = "age")
    private int age;
    @CsvBindByName(column = "phone")
    private String phone;
    @CsvBindByName(column = "email")
    private String email;
}
