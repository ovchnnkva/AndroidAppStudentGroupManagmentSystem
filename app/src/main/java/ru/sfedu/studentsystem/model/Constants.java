package ru.sfedu.studentsystem.model;

public class Constants {


    public static final String AUTH_FILE_NAME = "STUDENT_SYSTEM_AUTH";
    public static final String LOGIN_FROM_AUTH_FILE = "EMAIL_USER";
    public static final String PASS_FROM_AUTH_FILE = "PASS_USER";
    public static final String UID_USER_AUTH_FILE = "UID_USER";
    public static final String ROLE_USER_AUTH_FILE = "ROLE_USER";
    public enum ROLES {
        STUDENT, TEACHER, ADMIN
    }
    public enum TypeSchedule {
        SESSION, EVENTS, CLASSES;
    }


}
