package com.pvdong.ls3mongodb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @Field(name = "id")
    private String id;

    @Field(name = "username")
    @Indexed(unique = true)
    private String username;

    @Field(name = "password")
    private String password;

    @Field(name = "status")
    private Boolean status;
}
