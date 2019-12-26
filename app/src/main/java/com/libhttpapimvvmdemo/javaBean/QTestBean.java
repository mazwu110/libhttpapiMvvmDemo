package com.libhttpapimvvmdemo.javaBean;

import com.httpapi.BaseResultEntity;

public class QTestBean extends BaseResultEntity<QTestBean> {
    private String id;
    private String name;
    private String age;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
