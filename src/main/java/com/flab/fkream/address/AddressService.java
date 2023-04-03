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
        try {
            addressMapper.save(address);
        } catch (DataAccessException e) {
            log.error("[AddressService.addAddress] insert address error! addressInfo : {}",
                address);
            throw new MapperException(e);
        }
    }

    public Address findOne(Long id) {
        try {
            Address address = addressMapper.findOne(id);
            if (address == null) {
                throw new NoDataFoundException();
            }
            return address;
        } catch (DataAccessException e) {
            log.error("[AddressService.findOne] find address error!");
            throw new MapperException(e);
        }
    }

    public List<Address> findByUserId(Long userId) {
        try {
            List<Address> addresses = addressMapper.findByUserId(userId);
            if(addresses.size() == 0){
                throw new NoDataFoundException();
            }
            return addresses;
        } catch (DataAccessException e) {
            log.error("[AddressService.findOneByUserId] find address by userId error! ");
            throw new MapperException(e);
        }
    }

    public void update(Address addressInfo) {
        try {
            addressMapper.update(addressInfo);
        } catch (DataAccessException e) {
            log.error("[AddressService.update] update address error!");
            throw new MapperException(e);
        }
    }

    public void delete(Long id) {
        try {
            addressMapper.delete(id);
        } catch (DataAccessException e) {
            log.error("[AddressService.delete] delete address error! ");
            throw new MapperException(e);
        }
    }
}
