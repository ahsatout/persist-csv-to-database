package com.ahsatout.csvupload.user;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public Integer uploadUsers(MultipartFile file) throws IOException {
        Set<User> users = parseCsv(file);
        userRepository.saveAll(users);
        return users.size();
    }

    private Set<User> parseCsv(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<UserCsvRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(UserCsvRepresentation.class);
            CsvToBean<UserCsvRepresentation> csvToBean =
                    new CsvToBeanBuilder<UserCsvRepresentation>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> User.builder()
                            .firstName(csvLine.getFname())
                            .lastName(csvLine.getLname())
                            .age(csvLine.getAge())
                            .phone(csvLine.getPhone())
                            .email(csvLine.getEmail())
                            .build()
                    )
                    .collect(Collectors.toSet());
        }
    }
}
