package com.decathlon.alert.mechanism.system.constants;

import lombok.Value;

public class CodeConstants {
    public static final Code SUCCESS = new Code("00000", "SUCCESS", "Successful Request");
    public static final Code PARAM_ILLEGAL = new Code("00001", "PARAM_ILLEGAL", "Illegal parameters. For example, non-numeric input, invalid date");
    public static final Code BAD_REQUEST = new Code("00002", "BAD_REQUEST", "Bad request");
    public static final Code SYSTEM_ERROR = new Code("00003", "SYSTEM_ERROR", "System error");
    public static final Code TEAM_CREATION_ERROR = new Code("00004", "TEAM_CREATION_ERROR", "Something went wrong while creating team..");
    public static final Code DEV_CREATION_ERROR = new Code("00005", "DEV_CREATION_ERROR", "Something went wrong while creating developer details..");
    public static final Code DEV_NOT_FOUND = new Code("00006", "DEV_NOT_FOUND", "Developer details are empty for a requested team");

    @Value
    public static class Code {
        String id;
        String code;
        String message;
    }
}
