package com.example.jack.http_01.bean;


import java.util.List;

public class Result {
    private int result;

    private List<Person> personsData;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<Person> getPersonsData() {
        return personsData;
    }

    public void setPersonsData(List<Person> personsData) {
        this.personsData = personsData;
    }
}
