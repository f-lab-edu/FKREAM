package com.flab.fkream.search.elasticsearch;

import static com.flab.fkream.search.SortCriteria.POPULAR;

import com.flab.fkream.search.SearchCriteria;
import com.flab.fkream.search.SearchService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItemSearchService implements SearchService {

    private static final int PAGE_SIZE = 5;
    private final ItemSearchRepository itemSearchRepository;
    private final RestHighLevelClient client;


    public void saveItem(ItemDocument itemDocument) {
        itemSearchRepository.save(itemDocument);
    }

    @Transactional
    public void saveAllItem(List<ItemDocument> itemDocumentList) {
        itemSearchRepository.saveAll(itemDocumentList);
    }

    @Override
    public ItemDocumentsDto search(SearchCriteria searchCriteria) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        buildQuery(searchCriteria, sourceBuilder);
        sortResults(searchCriteria, sourceBuilder);

        sourceBuilder.timeout(TimeValue.timeValueSeconds(10));
        sourceBuilder.size(PAGE_SIZE);

        if (searchCriteria.getSortValue() != null) {
            sourceBuilder.searchAfter(searchCriteria.getSortValue());
        }

        SearchRequest searchRequest = new SearchRequest("item").source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return processSearchHits(searchResponse.getHits());
    }
    @Override
    public int findCount(SearchCriteria searchCriteria) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        buildQuery(searchCriteria, sourceBuilder);

        sourceBuilder.timeout(TimeValue.timeValueSeconds(10));

        CountRequest countRequest = new CountRequest("item").source(sourceBuilder);
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);

        return (int) countResponse.getCount();
    }

    private void buildQuery(SearchCriteria searchCriteria,
        SearchSourceBuilder sourceBuilder) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        applyContextSearch(searchCriteria, boolQueryBuilder);
        applyFilters(searchCriteria, boolQueryBuilder);
        sourceBuilder.query(boolQueryBuilder);
    }

    private void applyContextSearch(SearchCriteria searchCriteria,
        BoolQueryBuilder boolQueryBuilder) {
        if (searchCriteria.getContext() != null && !searchCriteria.getContext().isEmpty()) {
            String context = searchCriteria.getContext() + "*";
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("itemName", context));
            boolQueryBuilder.should(QueryBuilders.nestedQuery("brand",
                QueryBuilders.wildcardQuery("brand.brandName", context), ScoreMode.None));
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("modelNumber", context));
        }
    }

    private void applyFilters(SearchCriteria searchCriteria,
        BoolQueryBuilder boolQueryBuilder) {
        applyGenderFilter(searchCriteria, boolQueryBuilder);
        applyBrandFilter(searchCriteria, boolQueryBuilder);
        applySizeFilter(searchCriteria, boolQueryBuilder);
        applyCategoryFilter(searchCriteria, boolQueryBuilder);
    }

    private void applySizeFilter(SearchCriteria searchCriteria,
        BoolQueryBuilder boolQueryBuilder) {
        if (searchCriteria.getSize() != null && searchCriteria.getSize().length > 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("size",
                searchCriteria.getSize()));
        }
    }

    private void applyBrandFilter(SearchCriteria searchCriteria,
        BoolQueryBuilder boolQueryBuilder) {
        if (searchCriteria.getBrandId() != null) {
            boolQueryBuilder.filter(QueryBuilders.nestedQuery("brand",
                QueryBuilders.termQuery("brand.id", searchCriteria.getBrandId().toString()),
                ScoreMode.None));
        }

    }

    private void applyGenderFilter(SearchCriteria searchCriteria,
        BoolQueryBuilder boolQueryBuilder) {
        if (searchCriteria.getGender() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("gender",
                searchCriteria.getGender().toString()));
        }
    }


    private void applyCategoryFilter(SearchCriteria searchCriteria,
        BoolQueryBuilder boolQueryBuilder) {
        if (searchCriteria.getCategoryId() != null && searchCriteria.getCategoryId().length > 0) {
            BoolQueryBuilder filterBoolQuery = QueryBuilders.boolQuery();
            TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("categoryId",
                searchCriteria.getCategoryId());
            filterBoolQuery.should(termsQueryBuilder);

            if (searchCriteria.getDetailedCategoryId() != null
                && searchCriteria.getDetailedCategoryId().length > 0) {
                filterBoolQuery.should(QueryBuilders.termsQuery("detailedCategoryId",
                    searchCriteria.getDetailedCategoryId()));
            }
            boolQueryBuilder.filter(filterBoolQuery);
        }
    }

    private void sortResults(SearchCriteria searchCriteria,
        SearchSourceBuilder sourceBuilder) {
        switch (searchCriteria.getSort() != null ? searchCriteria.getSort() : POPULAR) {
            case PREMIUM_DESC:
                if (searchCriteria.getSize() == null || searchCriteria.getSize().length > 1) {
                    sourceBuilder.sort(
                        SortBuilders.fieldSort("minPremiumRate").order(SortOrder.DESC));

                } else if (searchCriteria.getSize() != null
                    && searchCriteria.getSize().length == 1) {
                    String size = searchCriteria.getSize()[0];
                    sourceBuilder.sort(
                        SortBuilders.fieldSort("premiumRateBySize." + size).order(SortOrder.DESC));
                }
                break;
            case PREMIUM_ASC:
                if (searchCriteria.getSize() == null || searchCriteria.getSize().length > 1) {
                    sourceBuilder.sort(
                        SortBuilders.fieldSort("minPremiumRate").order(SortOrder.ASC));

                } else if (searchCriteria.getSize() != null
                    && searchCriteria.getSize().length == 1) {
                    String size = searchCriteria.getSize()[0];
                    sourceBuilder.sort(
                        SortBuilders.fieldSort("premiumRateBySize." + size).order(SortOrder.ASC));
                }
                break;
            case RELEASE_DATE:
                sourceBuilder.sort(SortBuilders.fieldSort("releaseDate").order(SortOrder.DESC));
                break;
            default:
                sourceBuilder.sort(SortBuilders.fieldSort("dealCount").order(SortOrder.DESC));
                break;
        }
        sourceBuilder.sort("id", SortOrder.DESC);
    }

    private ItemDocumentsDto processSearchHits(SearchHits hits) {
        List<ItemDocument> itemDocuments = new ArrayList<>();
        ItemDocumentsDto itemDocumentsDto = new ItemDocumentsDto();

        for (SearchHit hit : hits) {
            itemDocuments.add(ItemDocument.of(hit));
            itemDocumentsDto.setSortValues(hit.getSortValues());
        }

        itemDocumentsDto.setItemDocumentList(itemDocuments);
        return itemDocumentsDto;
    }
}
