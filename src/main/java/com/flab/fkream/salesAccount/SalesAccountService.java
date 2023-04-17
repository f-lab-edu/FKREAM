package com.flab.fkream.salesAccount;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class SalesAccountService {

    private final SalesAccountMapper salesAccountMapper;

    public void save(SalesAccount salesAccount) {
        salesAccount.setCreatedAtToNow();

        SalesAccount existingAccount = salesAccountMapper.findByUserId(salesAccount.getUserId());

        if (existingAccount != null) {
            return;
        }

        salesAccountMapper.save(salesAccount);
    }

    public SalesAccount findById(Long id) {
        SalesAccount salesAccount = salesAccountMapper.findByUserId(id);
        if (salesAccount == null) {
            throw new NoDataFoundException();
        }
        return salesAccount;
    }

    public List<SalesAccount> findAll() {
        return salesAccountMapper.findAll();
    }

    public void update(SalesAccount salesAccount) {
        salesAccountMapper.update(salesAccount);
    }

    public void deleteById(Long id) {
        salesAccountMapper.deleteByUserId(id);
    }
}
