package ru.sfedu.studentsystem.model;

import java.util.HashMap;
import java.util.Map;

public class RecordBook {
    private long id;
    private Map<Discipline, PracticalMaterial> scores;
    private String typeSemester;

    public RecordBook(){
        scores = new HashMap<>();
    }
    public RecordBook(String typeSemester){
        scores = new HashMap<>();
        this.typeSemester = typeSemester;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getTypeSemester() {
        return typeSemester;
    }

    public void setTypeSemester(String typeSemester) {
        this.typeSemester = typeSemester;
    }

    @Override
    public String toString() {
        return "RecordBook{" +
                "id=" + id +
                ", typeSemester='" + typeSemester + '\'' +
                '}';
    }
}
