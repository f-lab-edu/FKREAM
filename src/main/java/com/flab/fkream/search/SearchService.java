package com.flab.fkream.search;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandService;
import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemService;
import com.flab.fkream.itemImg.ItemImg;
import com.flab.fkream.itemImg.ItemImgService;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SearchService {

    private final ItemSizePriceService itemSizePriceService;
    private final SearchMapper searchMapper;

    public List<SearchItemDto> search(String context) {

        List<SearchItemDto> searchItemDtos = searchMapper.search(context);
        return searchItemDtos;
    }

    public int findCount(String context) {
        return searchMapper.findCount(context);
    }
}
