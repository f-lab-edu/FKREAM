package com.flab.fkream.brand;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandMapperTest {


    @Autowired BrandMapper brandMapper;
    
    Brand brandInfo = Brand.builder()
            .brandName("샤넬")
            .isLuxury(true).build();

    @Test
    void 브랜드_추가() throws Exception {

        assertThat(brandMapper.save(brandInfo)).isEqualTo(1);
    }

    @Test
    void 브랜드_조회() throws Exception {
        brandMapper.save(brandInfo);
        assertThat(brandMapper.findOne(brandInfo.getId())).isEqualTo(brandInfo);
    }

    @Test
    void 브랜드_리스팅() {
        brandMapper.save(brandInfo);
        brandMapper.save(brandInfo);
        List<Brand> all = brandMapper.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void 브랜드_업데이트() {
        brandMapper.save(brandInfo);
        Long id = brandInfo.getId();
        Brand BrandUpdated = Brand.builder()
                .id(id)
                .brandName("나이키")
                .isLuxury(false).build();
        brandMapper.update(BrandUpdated);
        assertThat(brandMapper.findOne(brandInfo.getId())).isEqualTo(BrandUpdated);
    }

    @Test
    void 브랜드_삭제() {
        brandMapper.save(brandInfo);
        brandMapper.delete(brandInfo.getId());
        assertThat(brandMapper.findOne(brandInfo.getId())).isNull();
    }
}