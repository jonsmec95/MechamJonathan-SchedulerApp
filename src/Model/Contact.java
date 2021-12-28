package Model;

public class Contact {
    public int contactId;
    public String contactName;
    public String contactEmail;

    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getContactId(){
        return this.contactId;
    }
    public String getContactName() {
        return this.contactName;
    }
    public String getContactEmail(){
        return this.contactEmail;
    }


    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    //for the combo box
    @Override
    public String toString(){
        return (getContactName());
    }



}
