package model;

public class ProgramData implements Comparable<ProgramData>{

    private String name, path, category;
    private int position;

    public ProgramData(String name, String path, String category, int position) {
        this.name = name;
        this.path = path;
        this.category = category;
        this.position = position;
    }

    public ProgramData(String name, String path) {
        this(name, path, "DEFAULT", 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(ProgramData otherProgramData) {
        return this.position - otherProgramData.position;
    }
}
