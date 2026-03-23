package bai5.model;

public class Patient {
    private int id;
    private String name;
    private int age;
    private String department;
    private String disease;
    private int daysAdmitted;

    public Patient() {}

    public Patient(String name, int age, String department, String disease, int daysAdmitted) {
        this.name = name;
        this.age = age;
        this.department = department;
        this.disease = disease;
        this.daysAdmitted = daysAdmitted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDepartment() { return department; }
    public String getDisease() { return disease; }
    public int getDaysAdmitted() { return daysAdmitted; }


}
