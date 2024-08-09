package com.lavendersheep.githubexplorer;

public class BranchHandler {
    private String name;
    private String commitSHA;

    public String getBranchName(){
        return name;
    }

    public void setBranchName(String newName){
        this.name = newName;
    }

    public String getBranchCommit(){
        return commitSHA;
    }

    public void setCommitSHA(String newSHA){
        this.commitSHA = newSHA;
    }

    public BranchHandler(String newName, String newSHA){
        this.name = newName;
        this.commitSHA = newSHA;
    }
    
}
