package com.flab.fkream.interestItemCount;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterestItemCountService {

    private final InterestItemCountRepository interestItemCountRepository;

    public void save(InterestItemCount interestItemCount) {
        interestItemCountRepository.save(interestItemCount);
    }

    public List<InterestItemCount> findAll() {
        return interestItemCountRepository.findAll();
    }

    public InterestItemCount findByItemId(Long itemId) {
        return interestItemCountRepository.findByItemId(itemId);
    }
}
