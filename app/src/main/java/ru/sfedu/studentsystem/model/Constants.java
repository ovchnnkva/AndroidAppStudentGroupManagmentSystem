package ru.sfedu.studentsystem.model;

public class Constants {
    public static final String URL_DATA_BASE = "jdbc:postgresql://us-east-2.aws.neon.tech/neondb";
    public static final String USER_DATA_BASE = "ovchnnkva";
    public static final String PASS_DATA_BASE = "Wrs13GnChRim";
    public static final String TEACHER_TABLE_DB = "teachers";
    public  static final String DISCIPLINE_TABLE_DB = "disciplines";
    public static final String SCHEDULE_TABLE_DB = "schedules";
    public static final String EVENT_TABLE_DB = "events";
    public static final String STUDY_GROUP_DB = "studyGroups";
    public static final String DISCIPLINE_IN_GROUPS_DB="disciplinesInStudyGroups";
    public static final String PRACTICAL_MATERIAL_DB = "practicalMaterials";
    public static final String LECTION_MATERIAL_DB = "lectionMaterials";
    public static final String STUDENT_DB = "students";
    public static final String SCORES_DB = "studentsScores";


    public enum TypeSchedule {
        SESSION, EVENTS, CLASSES;
    }

}
