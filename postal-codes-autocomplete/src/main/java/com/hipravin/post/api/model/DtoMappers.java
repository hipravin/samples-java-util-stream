package com.hipravin.post.api.model;

import com.hipravin.post.PostIndex;
import com.hipravin.post.persist.entity.PostIndexEntity;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DtoMappers {
    private DtoMappers() {
    }

    public static PostIndexDto fromEntity(PostIndexEntity pie) {
        String description =  Stream.of(pie.getRegion(), pie.getAutonom(), pie.getArea(), pie.getCity(), pie.getCity1())
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(", "));

        return new PostIndexDto(pie.getIndex(), description);
    }

    public static PostIndexDto fromDomain(PostIndex pi) {
        String description =  Stream.of(pi.region(), pi.autonom(), pi.area(), pi.city(), pi.city1())
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(", "));

        return new PostIndexDto(pi.index(), description);
    }
}
