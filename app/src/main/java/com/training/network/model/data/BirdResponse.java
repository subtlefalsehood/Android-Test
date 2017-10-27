package com.training.network.model.data;

import java.io.Serializable;

/**
 * Created by chenqiuyi on 16/12/19.
 */

public class BirdResponse implements Serializable{


    /**
     * resourceKey : no1
     * conditionKey : ecsmcm553pntivm2
     * guidingKey :
     * content : 主人，眼蜜在此恭候多时！
     */

    private String resourceKey;
    private String conditionKey;
    private String guidingKey;
    private String content;

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getConditionKey() {
        return conditionKey;
    }

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey;
    }

    public String getGuidingKey() {
        return guidingKey;
    }

    public void setGuidingKey(String guidingKey) {
        this.guidingKey = guidingKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
