package com.flab.fkream.address;


import com.flab.fkream.sharding.AllShardQuery;
import com.flab.fkream.sharding.Sharding;
import com.flab.fkream.sharding.ShardingTarget;
import java.util.List;
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


    @Sharding(target = ShardingTarget.ADDRESS)
    public void addAddress(Long id, Address address) {
        addressMapper.save(address);
    }

    @Sharding(target = ShardingTarget.ADDRESS)
    public Address findOne(Long id) {
        return addressMapper.findOne(id);
    }

    @AllShardQuery
    public List<Address> findByUserId(Long userId) {
        return addressMapper.findByUserId(userId);
    }

    @Sharding(target = ShardingTarget.ADDRESS)
    public void update(Long id, Address addressInfo) {
        addressMapper.update(addressInfo);
    }
}
