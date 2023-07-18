package com.flab.fkream.sharding;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserHolder {

    private static final ThreadLocal<Context> userCotext = new ThreadLocal<>();

    public static Context getUserContext() {
        if (userCotext.get() == null) {
            Context context = new Context();
            context.setSharding(null);
            userCotext.set(context);
            return userCotext.get();
        }
        return userCotext.get();
    }

    public static void setSharding(ShardingTarget target, long shardKey) {
        getUserContext().setSharding(new Sharding(target, shardKey));
    }

    public static void setShardingWithShardNo(int shardNo) {
        Sharding sharding = new Sharding();
        sharding.setShardNo(shardNo);
        getUserContext().setSharding(sharding);
    }

    public static void clearSharding() {
        getUserContext().setSharding(null);
    }

    public static Sharding getSharding() {
        return getUserContext().getSharding();
    }


    @Getter
    @Setter
    public static class Context {

        private Sharding sharding;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Sharding {

        private ShardingTarget target;
        private long shardKey;
        private Integer shardNo;

        public Sharding(ShardingTarget target, long shardKey) {
            this.target = target;
            this.shardKey = shardKey;
        }
    }
}

