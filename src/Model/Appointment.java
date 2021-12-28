package Model;

import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {
    public int appId;
    public String appTitle;
    public String appDescription;
    public String appLocation;
    public String appType;
    public String appStart;
    public String appEnd;
    public String appCreateDate;
    public String appCreatedBy;
    public String appLastUpdate;
    public String appLastUpdatedBy;
    public int appCustId;
    public int appUserId;
    public int appContactId;

    public static int appointmentId = 3;


    public Appointment(int appId, String appTitle, String appDescription, String appLocation, String appType, String appStart, String appEnd, String appCreateDate,
                       String appCreatedBy, String appLastUpdate, String appLastUpdatedBy, int appCustId, int appUserId, int appContactId) {

        this.appId = appId;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.appCreateDate = appCreateDate;
        this.appCreatedBy = appCreatedBy;
        this.appLastUpdate = appLastUpdate;
        this.appLastUpdatedBy = appLastUpdatedBy;
        this.appCustId = appCustId;
        this.appUserId = appUserId;
        this.appContactId = appContactId;

    }


    public int getAppId(){return this.appId;}
    public String getAppTitle(){return this.appTitle;}
    public String getAppDescription(){return this.appDescription;}
    public String getAppLocation(){return this.appLocation;}
    public String getAppType(){return this.appType;}
    public String getAppStart(){return this.appStart;}
    public String getAppEnd(){return this.appEnd;}
    public String getAppCreateDate(){return this.appCreateDate;}
    public String getAppCreatedBy(){return this.appCreatedBy;}
    public String getAppLastUpdate(){return this.appLastUpdate;}
    public String getAppLastUpdatedBy(){return this.appLastUpdatedBy;}
    public int getAppCustId(){return this.appCustId;}
    public int getAppUserId(){return this.appUserId;}
    public int getAppContactId(){return this.appContactId;}


    public void setAppId(int appId){
        this.appId= appId;
    }
    public void setAppTitle(String appTitle){
        this.appTitle = appTitle;
    }
    public void setAppDescription(String appDescription){
        this.appDescription = appDescription;
    }
    public void setAppLocation(String appLocation){
        this.appLocation = appLocation;
    }
    public void setAppType(String appType){
        this.appType = appType;
    }
    public void setAppStart(String appStart){
        this.appStart = appStart;
    }
    public void setAppEnd(String appEnd){
        this.appEnd = appEnd;
    }
    public void setAppCreateDate(String appCreateDate){
        this.appCreateDate = appCreateDate;
    }
    public void setAppCreatedBy(String appCreatedBy){
        this.appCreatedBy = appCreatedBy;
    }
    public void setAppLastUpdate(String appLastUpdate){
        this.appLastUpdate = appLastUpdate;
    }
    public void setAppLastUpdatedBy(String appLastUpdatedBy){
        this.appLastUpdatedBy = appLastUpdatedBy;
    }
    public void setAppCustId(int appCustId){
        this.appCustId= appCustId;
    }
    public void setAppUserId(int appUserId){
        this.appUserId= appUserId;
    }
    public void setAppContactId(int appContactId){
        this.appContactId= appContactId;
    }


    public static int generateNextId(){
        ObservableList<Appointment> allAppointments = DBQuery.getAllAppointments();

        for (int i = 0; i < allAppointments.size(); i++) {
            appointmentId = allAppointments.get(i).getAppId();
        }
        return appointmentId += 1;
    }
}
