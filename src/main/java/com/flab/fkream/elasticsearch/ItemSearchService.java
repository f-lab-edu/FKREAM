package com.flab.fkream.elasticsearch;

import static com.flab.fkream.search.SortCriteria.POPULAR;

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
public class ItemSearchService {

    private final ItemSearchRepository itemSearchRepository;
    private final RestHighLevelClient client;

    private static final int PAGE_SIZE = 5;

    public void saveItem(ItemDocument itemDocument) {
        itemSearchRepository.save(itemDocument);
    }

    @Transactional
    public void saveAllItem(List<ItemDocument> itemDocumentList) {
        itemSearchRepository.saveAll(itemDocumentList);
    }

    public ItemDocumentsDto findBySearchCriteria(ElasticSearchCriteria searchCriteria) throws IOException {

        SearchRequest searchRequest = new SearchRequest("item");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (searchCriteria.getContext() != null && !searchCriteria.getContext().isEmpty()) {
            String context = searchCriteria.getContext();
            context += "*";
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("itemName", context));
            boolQueryBuilder.should(QueryBuilders.nestedQuery("brand",
                QueryBuilders.wildcardQuery("brand.brandName", context), ScoreMode.None));
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("modelNumber", context));
        }

        if (searchCriteria.getGender() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("gender",
                searchCriteria.getGender().toString()));
        }

        if (searchCriteria.getBrandId() != null) {
            boolQueryBuilder.filter(QueryBuilders.nestedQuery("brand",
                QueryBuilders.termQuery("brand.id", searchCriteria.getBrandId().toString()),
                ScoreMode.None));
        }

        if (searchCriteria.getSize() != null && searchCriteria.getSize().length > 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("size",
                searchCriteria.getSize()));
        }

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

        sourceBuilder.query(boolQueryBuilder);

        switch (searchCriteria.getSort() != null ? searchCriteria.getSort() : POPULAR) {
            case PREMIUM_DESC:
                sourceBuilder.sort(SortBuilders.fieldSort("premiumRate").order(SortOrder.DESC));
                break;
            case PREMIUM_ASC:
                sourceBuilder.sort(SortBuilders.fieldSort("premiumRate").order(SortOrder.ASC));
                break;
            case RELEASE_DATE:
                sourceBuilder.sort(SortBuilders.fieldSort("releaseDate").order(SortOrder.DESC));
                break;
            default:
                sourceBuilder.sort(SortBuilders.fieldSort("dealCount").order(SortOrder.DESC));
                break;
        }
        sourceBuilder.sort("id", SortOrder.DESC);

        sourceBuilder.timeout(TimeValue.timeValueSeconds(10));

        if (searchCriteria.getSortValue() != null) {
            sourceBuilder.searchAfter(searchCriteria.getSortValue());
        }
        sourceBuilder.size(PAGE_SIZE);

        searchRequest.source(sourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

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
