/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package familytree;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Person implements Serializable{
    String FName,Surname,Gender,LifeDesc,StreetName,Suburb;
    int ID,StreetNo,Postcode;
    Person Spouse;
    ArrayList<Person> parentList, childrenList,grandChildList;
    
    public Person(){
    }
    
    public Person(String fname, String surname, String gender, String lifedesc, String sname, String suburb, int Id, int sno,int postcode){
        FName = fname;
        Surname = surname;
        Gender = gender;
        LifeDesc =lifedesc;
        StreetName = sname;
        StreetNo = sno;
        Suburb = suburb;
        ID = Id;
        Postcode = postcode;
    }
    
    public String getFullName(){
        return Surname+" "+FName;
    }
    
    public void setID(int Id){
        this.ID = Id;
    }
    public int getID(){
        return ID;
    }
    
    public void setSurname(String surname){
        this.Surname = surname;
    }
    
    public String getSurname(){
        return Surname;
    }
    
    public void setFName(String fname){
        this.FName = fname;
    }
    
    public String getFName(){
        return FName;
    }
    
    public void setGender(String gender){
        this.Gender = gender;
    }
    
    public String getGender(){
        return Gender;
    }
    
    public void setSName(String sname){
        this.StreetName = sname;
    }
    
    public String getSName (){
        return StreetName;
    }
    
    public void setSNo(int sno){
        this.StreetNo = sno;
    }
    
    public int getSNo (){
        return StreetNo;
    }
    
    public void setSuburb(String suburb){
        this.Suburb = suburb;
    }
    
    public String getSuburb(){
        return Suburb;
    }
    
    public void setPostcode(int postcode){
        this.Postcode = postcode;
    }
    
    public int getPostcode(){
        return Postcode;
    }
    
    public void setChildList(ArrayList<Person> childlist){
        this.childrenList = childlist;
    }
    public ArrayList<Person> getChildrenList(){
        return childrenList;
    }
    
    public void setParentList(ArrayList<Person> parentlist){
        this.parentList = parentlist;
    }
    public ArrayList<Person> getParentList(){
        return parentList;
    }
    
    public void setGrandchildList(ArrayList<Person> grandchildlist){
        this.grandChildList = grandchildlist;
    }
    
    public ArrayList<Person> getGrandchildList(){
        return grandChildList;
    }
    
    public void setSpouse(Person spouse){
        this.Spouse = spouse;
    }
    
    public Person getSpouse(){
        return Spouse;
    }
}
