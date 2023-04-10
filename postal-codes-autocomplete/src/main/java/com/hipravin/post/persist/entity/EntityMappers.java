package com.hipravin.post.persist.entity;

import com.hipravin.post.PostIndex;

public final class EntityMappers {
    private EntityMappers() {
    }

    public static PostIndexEntity from(PostIndex postIndex) {
        PostIndexEntity pie = new PostIndexEntity();
        pie.setIndex(postIndex.index());
        pie.setName(postIndex.name());
        pie.setRegion(postIndex.region());
        pie.setAutonom(postIndex.autonom());
        pie.setArea(postIndex.area());
        pie.setCity(postIndex.city());
        pie.setCity1(postIndex.city1());

        return pie;
    }

}
