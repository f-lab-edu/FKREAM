package com.flab.fkream.sharding;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShardingDataSourceProperty {

    private List<Shard> shards;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Shard {

        private Property master;
        private Property slave;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Property {

        private String url;
        private String username;
        private String password;
        private String name;

    }

}
