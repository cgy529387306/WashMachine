package com.android.mb.wash.entity;

import java.io.Serializable;

public class CodeBean implements Serializable {

    /**
     * code : 223742
     */

    private String code;

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
