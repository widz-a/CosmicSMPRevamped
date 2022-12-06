package me.tajam.jext.config;

import me.tajam.jext.DiscContainer;

public class ConfigDiscManager {
    public static ConfigDiscManager getInstance() {
        return new ConfigDiscManager();
    }

    public DiscContainer getDisc(String n){
        return new DiscContainer();
    }

}
