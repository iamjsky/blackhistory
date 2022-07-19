package com.blackhistory.db;

import android.provider.BaseColumns;

public final class DataBases {
    public static final class CreateDB implements BaseColumns {
        public static final String NAME = "name";
        public static final String NUMBER = "number";
        public static final String MEMO = "memo";
        public static final String CREATEDAT = "createdat";
        public static final String COUNT = "count";
        public static final String _TABLENAME0 = "userlist";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +NAME+" text not null , "
                +NUMBER+" text not null , "
                +MEMO+" text , "
                +CREATEDAT+" text not null , "
        +COUNT+" text not null );";
    }


}
