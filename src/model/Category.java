package model;

import java.util.ArrayList;
import java.util.Objects;

public class Category implements ICategory, Comparable<Category> {

    private ArrayList<ProgramData> programs;
    private String name;

    public Category() {
        programs = new ArrayList<>();
    }

    @Override
    public void addProgram(ProgramData programData) {
        programs.add(programData);
    }

    @Override
    public ArrayList<ProgramData> getPrograms() {
        return programs;
    }

    @Override
    public int getSize() {
        return programs.size();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Category category) {
        return this.name.compareTo(category.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj.getClass() != this.getClass())
            return false;

        final Category other = (Category) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

}
