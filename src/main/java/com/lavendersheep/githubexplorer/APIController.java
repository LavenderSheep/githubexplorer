package com.lavendersheep.githubexplorer;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;


@RestController
public class APIController {

    //values for constructing a response
    private List<BranchHandler> listOfBranches;
    private List<JSONMapperHandler> listOfRepositories;

    public List<BranchHandler> getListOfBranches(){
        return listOfBranches;
    }

    public void setListOfBranches(List<BranchHandler> newListOfBranches){
        this.listOfBranches = newListOfBranches;
    }

    public List<JSONMapperHandler> getListOfRepositories(){
        return listOfRepositories;
    }

    public void setListOfRepositories(List<JSONMapperHandler> newListOfRepositories){
        this.listOfRepositories = newListOfRepositories;
    }

    //build a Rest Client for Communication with GitHub API endpoint
    private RestClient gitHubRestClient = RestClient.builder()
    .baseUrl("https://api.github.com")
    .defaultHeader("User-Agent", "GitHubExplorer")
    .defaultHeader("Accept", "application/json")
    .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
    .build();

    //map results to the subpage
    @GetMapping("/")
    public RecordHandler recordHandler(@RequestParam(value = "username", defaultValue = "LavenderSheep") String username, @RequestHeader(name = HttpHeaders.ACCEPT, required = true) String headerCheck) throws JsonMappingException, JsonProcessingException{
        ResponseEntity<String> result = gitHubRestClient.get()
        .uri("/users/{username}/repos", username)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
            throw new HandleNotFoundError();
        })
        .toEntity(String.class);

        //clear any leftovers
        setListOfBranches(new ArrayList<BranchHandler>());
        setListOfRepositories(new ArrayList<JSONMapperHandler>());

        //map the response to a json node
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode resultNode = jsonMapper.readTree(result.getBody());

        for(JsonNode iNode : resultNode){
            //check to see if the repository is a fork
            if(!iNode.get("fork").asBoolean()){
                JSONMapperHandler payload = new JSONMapperHandler(iNode);
                //get all branches of the chosen repository
                ResponseEntity<String> branchesResult = gitHubRestClient.get()
                .uri("/repos/{user}/{projectname}/branches",iNode.get("owner").get("login").asText(), iNode.get("name").asText())
                .retrieve()
                .toEntity(String.class);

                //safety check if the repository has no branches (eg. empty repos)
                if(!branchesResult.getBody().isEmpty()){
                    JsonNode branchesNode = jsonMapper.readTree(branchesResult.getBody());

                    for(JsonNode jNode : branchesNode){
                        BranchHandler currentBranch = new BranchHandler(jNode.get("name").asText(), jNode.get("commit").get("sha").asText());
                        listOfBranches.add(currentBranch);
                    }
                }

                payload.setBranches(listOfBranches);
                listOfRepositories.add(payload);

                setListOfBranches(new ArrayList<BranchHandler>());
            }
        }

        return new RecordHandler(listOfRepositories);
    }
}