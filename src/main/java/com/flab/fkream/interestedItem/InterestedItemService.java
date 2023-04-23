package com.flab.fkream.interestedItem;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class InterestedItemService {

    private final InterestedItemMapper interestedItemMapper;

    public int save(InterestedItem interestedItem) {
        return interestedItemMapper.save(interestedItem);
    }

    public List<InterestedItem> findAllByUserId(Long userId) {
        return interestedItemMapper.findAllByUserId(userId);
    }

    public int delete(Long id) {
        return interestedItemMapper.deleteById(id);
    }

}
