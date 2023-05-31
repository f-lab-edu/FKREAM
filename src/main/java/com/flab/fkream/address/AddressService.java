package com.flab.fkream.address;

import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.redis.RedisService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AddressService {

    private final AddressMapper addressMapper;
    private final RedisService redisService;

    private final AddressRepository addressRepository;

    public void addAddress(Address address) {
        Long addressId = redisService.getAddressId();
        addressRepository.addAddress(addressId, address);
    }

    public Address findOne(Long id) {
        Address address = addressMapper.findOne(id);
        if (address == null) {
            throw new NoDataFoundException();
        }
        return address;
    }

    public List<Address> findByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        if (addresses.size() == 0) {
            throw new NoDataFoundException();
        }
        return addresses;
    }

    public void update(Address addressInfo) {
        addressRepository.update(addressInfo.getId(), addressInfo);
    }

    public void delete(Long id) {
        addressMapper.delete(id);
    }
}
