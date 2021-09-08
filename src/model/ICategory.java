package model;

import model.ProgramData;

import java.util.ArrayList;

public interface ICategory {

    void addProgram(ProgramData programData);

    ArrayList<ProgramData> getPrograms();

    int getSize();

    String getName();

    void setName(String name);

}
