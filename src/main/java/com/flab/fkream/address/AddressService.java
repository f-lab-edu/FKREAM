package com.flab.fkream.address;

import com.flab.fkream.error.exception.MapperException;
import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AddressService {

    private final AddressMapper addressMapper;

    public void addAddress(Address address) {
        addressMapper.save(address);
    }

    public Address findOne(Long id) {
        Address address = addressMapper.findOne(id);
        if (address == null) {
            throw new NoDataFoundException();
        }
        return address;
    }

    public List<Address> findByUserId(Long userId) {
        List<Address> addresses = addressMapper.findByUserId(userId);
        if (addresses.size() == 0) {
            throw new NoDataFoundException();
        }
        return addresses;
    }

    public void update(Address addressInfo) {
        addressMapper.update(addressInfo);
    }

    public void delete(Long id) {
        addressMapper.delete(id);
    }
}
