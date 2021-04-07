package com.example.midterm_makeup;

import java.io.Serializable;
import java.util.ArrayList;

public class Source implements Serializable{

        String name, desc, id;

        public Source(String name, String desc, String id) {
            this.name = name;
            this.desc = desc;
            this.id = id;
        }

    public Source() {
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Source{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

