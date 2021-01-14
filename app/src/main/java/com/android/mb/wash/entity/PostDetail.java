package com.android.mb.wash.entity;

import java.io.Serializable;

public class PostDetail implements Serializable {
    private PostBean dynamic;

    public PostBean getDynamic() {
        return dynamic;
    }

    public void setDynamic(PostBean dynamic) {
        this.dynamic = dynamic;
    }
}
