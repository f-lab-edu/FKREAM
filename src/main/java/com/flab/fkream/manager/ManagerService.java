package com.flab.fkream.manager;

import com.flab.fkream.error.exception.NoDataFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerMapper managerMapper;

    public void addManager(Manager managerInfo) {
        managerMapper.save(managerInfo);
    }

    public Manager findOne(Long managerId) {
        Manager manager = managerMapper.findOne(managerId);
        if (manager == null) {
            throw new NoDataFoundException();
        }
        return manager;
    }

    public List<Manager> findAll() {
        List<Manager> managers = managerMapper.findAll();
        return managers;
    }
}
