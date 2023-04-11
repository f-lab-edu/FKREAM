package com.flab.fkream.search;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandService;
import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.item.Item;
import com.flab.fkream.item.ItemService;
import com.flab.fkream.itemImg.ItemImg;
import com.flab.fkream.itemImg.ItemImgService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SearchService {

    private final ItemImgService itemImgService;

    private final ItemService itemService;

    private final BrandService brandService;

    public List<ItemImg> searchBrand(String context) {
        Brand brand = brandService.findByBrandName(context);
        List<Item> items = itemService.findByBrand(brand);

    }


}
