package com.hipravin.post;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record PostIndex(
        String index,
        String name,
        String region,
        String autonom,
        String area,
        String city,
        String city1
) {
    public static final Comparator<PostIndex> BY_INDEX_THEN_OTHER_COMPARATOR =
            Comparator.comparing(PostIndex::index)
                    .thenComparing(PostIndex::region)
                    .thenComparing(PostIndex::autonom)
                    .thenComparing(PostIndex::area)
                    .thenComparing(PostIndex::city)
                    .thenComparing(PostIndex::city1);

    public String toString() {
        return Stream.of(index, region, autonom, area, city, city1)
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" / "));
    }
}


//index,CHARACTER(6),YES,"",NULL
//opsname,CHARACTER(60),YES,"",NULL
//opstype,CHARACTER(50),YES,"",NULL
//opssubm,CHARACTER(6),YES,"",NULL
//region,CHARACTER(60),YES,"",NULL
//autonom,CHARACTER(60),YES,"",NULL
//area,CHARACTER(60),YES,"",NULL
//city,CHARACTER(60),YES,"",NULL
//city_1,CHARACTER(60),YES,"",NULL
//actdate,DATE,YES,"",NULL
//indexold,CHARACTER(6),YES,"",NULL
