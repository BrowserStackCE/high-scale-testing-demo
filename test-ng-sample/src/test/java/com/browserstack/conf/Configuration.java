package com.browserstack.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private String user;
    @JsonProperty("access_key")
    private String accessKey;
    private String project;
    private String build;
    private List<Platform> platforms;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = new ArrayList<>();
        for (Platform platform:platforms){
            for (int i=0;i<platform.getCount();i++){
                this.platforms.add(platform);
            }
        }
    }

    public List<Platform> getActivePlatforms() {
        return platforms;
    }
}