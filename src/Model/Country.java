package Model;

public class Country {
    public int countryId;
    public String countryName;
    public String createDate;
    public String createdBy;
    public String lastUpdate;
    public String lastUpdatedBy;

    public Country(int countryId, String countryName, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryID(){ return countryId;}

    public String getCountryName(){ return countryName;}

    public String getCreatedBy(){ return createdBy;}

    public String getCreateDate(){ return createDate;}

    public String getLastUpdate(){ return lastUpdate;}

    public String getLastUpdatedBy(){return lastUpdatedBy;}



    public void setCountryId(int countryId){
        this.countryId = countryId;
    }
    public void setCountryName(String name){
        this.countryName = name;
    }
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }
    public void setCreateDate(String date){
        this.createDate = date;
    }
    public void setLastUpdate(String update){
        this.lastUpdate = update;
    }
    public void setLastUpdatedBy(String name){
        this.lastUpdatedBy = name;
    }


    //for the combo box
    @Override
    public String toString(){
        return (getCountryName());
    }

}
