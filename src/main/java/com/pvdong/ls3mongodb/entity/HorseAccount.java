package com.pvdong.ls3mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "horse_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HorseAccount {
    @Id
    @Field(name = "id")
    private String id;

    @Field(name = "account_id")
    private String account_id;

    @Field(name = "horse_id")
    private String horse_id;

    @Field(name = "archive")
    private Boolean archive;
}
