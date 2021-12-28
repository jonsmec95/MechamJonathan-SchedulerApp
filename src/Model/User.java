package Model;

public class User {
    public int userId;
    public String userName;
    public String userPassword;
    public String createDate;
    public String createdBy;
    public String lastUpdate;
    public String lastUpdatedBy;

    public User(int userId, String userName, String userPassword, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy){
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }


    public int getUserId() {
        return this.userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public String getUserPassword() {
        return this.userPassword;
    }
    public String getCreateDate() {
        return this.createDate;
    }
    public String getCreatedBy() {
        return this.createdBy;
    }
    public String getLastUpdate() {
        return this.lastUpdate;
    }
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }


    public void setUserId(int userId){
        this.userId = userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserPassword(String password) {
        this.userPassword = password;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }


    //for the combo box
    @Override
    public String toString(){
        return (getUserName());
    }

}
