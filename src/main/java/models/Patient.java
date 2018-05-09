package models;

/**
 * Created by gjx2998 on 15/02/2018.
 */
public class Patient {
    private String username ;
    private String password ;
    private String pid;

    public Patient(String username, String password, String pid){
        this.username = username;
        this.password = password;
        this.pid = pid;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getPid(){
        return pid;
    }

}
