package Model;

import java.sql.Date;

public class FirstLevelDivision {

    public int divisionID;
    public String divisionName;
    public String createDate;
    public String createdBy;
    public String lastUpdate;
    public String lastUpdatedBy;
    public int countryId;

    public FirstLevelDivision(int divisionID, String divisionName,String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }


    public int getDivisionID(){ return divisionID;}

    public String getDivisionName(){ return divisionName;}

    public String getCreateDate(){ return createDate;}

    public String getCreatedBy(){ return createdBy;}

    public String getLastUpdate(){ return lastUpdate;}

    public String getLastUpdatedBy(){return lastUpdatedBy;}

    public int getCountryId(){return countryId;}




    public void setDivisionID(int divisionID){
        this.divisionID = divisionID;
    }
    public void setDivisionName(String name){
        this.divisionName = name;
    }
    public void setCreateDate(String date){
        this.createDate = date;
    }
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }
    public void setLastUpdate(String update){
        this.lastUpdate = update;
    }
    public void setLastUpdatedBy(String name){
        this.lastUpdatedBy = name;
    }
    public void setCountryId(int countryId){
        this.countryId = countryId;
    }


    @Override
    public String toString(){
        return (getDivisionName());
    }

}
