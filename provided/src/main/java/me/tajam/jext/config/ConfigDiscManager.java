package me.tajam.jext.config;

import me.tajam.jext.DiscContainer;

public interface ConfigDiscManager {
    static ConfigDiscManager getInstance() {
        return null;
    }

    DiscContainer getDisc(String n);

}
