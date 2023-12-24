package last.project;


public class users {

    String art, first, last, date, museum, address;

    public void setArt(String art){
        this.art = art;
    }

    public void setFirst(String first){
        this.first = first;
    }

    public void setLast(String last){
        this.last = last;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setMuseum(String museum){
        this.museum = museum;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getArt() {
        return art;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getDate() {
        return date;
    }

    public String getMuseum() {
        return museum;
    }

    public String getAddress() {
        return address;
    }

    public users(String art, String first, String last, String date, String museum, String address) {
        this.art = art;
        this.first = first;
        this.last = last;
        this.date = date;
        this.museum = museum;
        this.address = address;

    }
}