package com.canteen.canteenapp.models;

public class RegistrationStudentDTO {
    private String fName;
    private String lName;
    private String college;
    private String matric;


    public RegistrationStudentDTO(){}


    public RegistrationStudentDTO(String fName, String lName, String college, String matric) {
        this.fName = fName;
        this.lName = lName;
        this.college = college;
        this.matric = matric;
    }

    public String getfName(){
        return this.fName;
    }
    public String getlName(){
        return this.lName;
    }
    public String getCollege(){
        return this.college;
    }
    public String getMatric(){
        return this.matric;
    }

  }




