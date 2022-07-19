package com.blackhistory.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserListModel {

   private long id;
    private String name;
    private String memo;
    private String number;
    private String createdat;

    public UserListModel(long id, String name, String memo, String number, String createdat) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.number = number;
        this.createdat = createdat;
    }
}
