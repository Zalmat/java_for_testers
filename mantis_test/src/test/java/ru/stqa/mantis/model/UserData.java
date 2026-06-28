package ru.stqa.mantis.model;

public record UserData(String userName, String password, String email, String accessLevel) {

    public UserData(){
        this("", "", "", "");
    }

    public UserData withUserName(String userName){
        return new UserData(userName, this.password, this.email, this.accessLevel);
    }

    public UserData withPassword(String password){
        return new UserData(this.userName, password, this.email, this.accessLevel);
    }

    public UserData withEmail(String email){
        return new UserData(this.userName, this.password, email, this.accessLevel);
    }

    public UserData withAccessLevel(String accessLevel){
        return new UserData(this.userName, this.password, this.email, accessLevel);
    }
}