package com.canteen.canteenapp.models;

import java.util.Date;

public class RegistrationFoodSellerDTO {
    private String fName;
    private String lName;
    private String college;
    private String nameStall;
    private Long timeClose;
    private Long timeOpen;
    private Long oprtimeClose;
    private Long oprtimeOpen;



    public RegistrationFoodSellerDTO(){}


    public RegistrationFoodSellerDTO(String fName, String lName, String college, String nameStall, Long timeClose, Long timeOpen , Long oprtimeClose, Long oprtimeOpen) {
        this.fName = fName;
        this.lName = lName;
        this.college = college;
        this.nameStall = nameStall;
        this.timeClose = timeClose;
        this.timeOpen = timeOpen;
        this.oprtimeClose = oprtimeClose;
        this.oprtimeOpen = oprtimeOpen;
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
    public String getNameStall(){
        return this.nameStall;
    }


    public Long getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(Long timeClose) {
        this.timeClose = timeClose;
    }

    public Long getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(Long timeOpen) {
        this.timeOpen = timeOpen;
    }

    public Long getOprtimeClose() {
        return oprtimeClose;
    }

    public void setOprtimeClose(Long oprtimeClose) {
        this.oprtimeClose = oprtimeClose;
    }

    public Long getOprtimeOpen() {
        return oprtimeOpen;
    }

    public void setOprtimeOpen(Long oprtimeOpen) {
        this.oprtimeOpen = oprtimeOpen;
    }
}
