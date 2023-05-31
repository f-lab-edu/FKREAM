package com.flab.fkream.address;


import com.flab.fkream.sharding.DataSourceRouter;
import com.flab.fkream.sharding.Sharding;
import com.flab.fkream.sharding.ShardingTarget;
import com.flab.fkream.sharding.UserHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Sharding 어노테이션을 이용하여 첫번째 파라미터를 샤딩 키로 사용 하지만, mybatis는 첫번째 파라미터에 id 값을 넣을 객체가 있어야 한다. 따라서,
 * Repository 클래스를 만들어, Repository 클래스의 메소드는 id값이 첫번째 파라미터로, Mapper 클래스에는 첫번째 파라미터가 객체를 되도록 하였다.
 */

@Component
@RequiredArgsConstructor
public class AddressRepository {

    private final AddressMapper addressMapper;
    private final DataSourceRouter dataSourceRouter;


    @Sharding(target = ShardingTarget.ADDRESS)
    public void addAddress(Long id, Address address) {
        addressMapper.save(address);
    }

    @Sharding(target = ShardingTarget.ADDRESS)
    public Address findOne(Long id) {
        return addressMapper.findOne(id);
    }

    public List<Address> findByUserId(Long userId) {
        Map<Object, DataSource> dataSources = dataSourceRouter.getResolvedDataSources();
        int shardSize = dataSources.size() / 2;
        List<Address> result = new ArrayList<>();
        for (int i = 0; i < shardSize; i++) {
            UserHolder.setShardingWithShardNo(ShardingTarget.ADDRESS, i);
            result.addAll(addressMapper.findByUserId(userId));
        }
        UserHolder.clearSharding();
        return result;
    }

    @Sharding(target = ShardingTarget.ADDRESS)
    public void update(Long id, Address addressInfo) {
        addressMapper.update(addressInfo);
    }
}
