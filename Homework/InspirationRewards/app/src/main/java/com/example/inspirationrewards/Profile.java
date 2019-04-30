package com.example.inspirationrewards;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Profile implements Serializable {
    private String username = "";
    private String password = "";
    private String firstname = "";
    private String lastname = "";
    private String department = "";
    private String position = "";
    private String story = "";
    private boolean admin = false;
    private int points = 0;
    private List<Reward> rewardList;
    private byte[] photo;

    public Profile(
        String un,
        String pw,
        String fn,
        String ln,
        String dpt,
        String pos,
        String sty,
        boolean a,
        int pts,
        byte[] pho
    ){
        username = un;
        password = pw;
        firstname = fn;
        lastname = ln;
        department = dpt;
        position = pos;
        story = sty;
        admin = a;
        points = pts;
        photo = pho;

    }

    //----GET----
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public String getStory() {
        return story;
    }

    public boolean isAdmin(){
        return admin;
    }

    public int getPoints(){
        return points;
    }

    public List<Reward> getRewardList(){
        return rewardList;
    }

    public byte[] getPhoto(){
        return photo;
    }

    //--SET----
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public void setPhoto(byte[] photo){
        this.photo = photo;
    }
    //--Reward Class Helpers----

    public void sendReward(int amount){
        //need more methods here to actually do sending
        this.setPoints(this.getPoints() - amount);
    }

    public void receiveReward(int amount, String sender, String comment){
        //First calculate total points
        this.setPoints(this.getPoints() + amount);

        //We can send amount, sender (sender is fName and lName), and comment okay
        //Now we need to initialize the date sent
        Date D = Calendar.getInstance().getTime();
        SimpleDateFormat SDF = new SimpleDateFormat("MM/d/YYYY");
        String date = SDF.format(D).toString();

        //Make reward and add to this profile's reward list
        //Might have to do more in terms of updating json,db, online etc
        rewardList.add(new Reward(amount, sender, comment, date));
    }
}
