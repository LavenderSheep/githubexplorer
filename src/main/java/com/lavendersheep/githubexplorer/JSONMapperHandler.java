package com.lavendersheep.githubexplorer;

import java.util.List;

import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.databind.*;


public class JSONMapperHandler {
    @Order(1)
    private String ownerLogin;
    @Order(2)
    private String repsitoryName;
    @Order(3)
    private List<BranchHandler> branches;

    public String getRepositoryName(){
        return repsitoryName;
    }

    public void setRepositoryName(String newRepositoryName){
        this.repsitoryName = newRepositoryName;
    }

    public String getOwnerLogin(){
        return ownerLogin;
    }

    public void setOwnerLogin(String newOwnerLogin){
        this.ownerLogin = newOwnerLogin;
    }  
    
    public List<BranchHandler> getBranches(){
        return branches;
    }

    public void setBranches(List<BranchHandler> newBranchList){
        this.branches = newBranchList;
    }

    public JSONMapperHandler(String newRepositoryName, String newOwnerLogin, List<BranchHandler> newBranchList){
        this.repsitoryName = newRepositoryName;
        this.ownerLogin = newOwnerLogin;
        this.branches = newBranchList;
    }

    public JSONMapperHandler(JsonNode node){
        this.repsitoryName = node.get("name").asText();
        this.ownerLogin = node.get("owner").get("login").asText();
    }
}
