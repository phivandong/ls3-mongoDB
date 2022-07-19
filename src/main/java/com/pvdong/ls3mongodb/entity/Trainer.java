package com.pvdong.ls3mongodb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "trainer")
@Getter
@Setter
@NoArgsConstructor
public class Trainer {
    @Id
    @Field(name = "id")
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "account_id")
    private String account_id;
}
