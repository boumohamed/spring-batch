package me.bouzri.firstbatch.mongoBatch;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class PersonDocument {
    private String name;
}
