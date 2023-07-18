package com.flab.fkream.salesAccount;

import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    public SalesAccount findByUserId() {
        SalesAccount salesAccount = salesAccountMapper.findByUserId(SessionUtil.getLoginUserId());
        if (salesAccount == null) {
            throw new NoDataFoundException();
        }
        return salesAccount;
    }

    public void update(SalesAccount salesAccount) {
        salesAccountMapper.update(salesAccount);
    }

    public void deleteById() {
        salesAccountMapper.deleteByUserId(SessionUtil.getLoginUserId());
    }
}
