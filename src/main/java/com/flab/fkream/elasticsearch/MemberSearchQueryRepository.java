package com.flab.fkream.elasticsearch;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class MemberSearchQueryRepository {

    private final ElasticsearchOperations operations;

    public List<MemberEntity> findByCriteria(SearchCondition searchCondition, Pageable pageable) {
        CriteriaQuery query = createConditionCriteriaQuery(searchCondition).setPageable(pageable);
        SearchHits<MemberEntity> search = operations.search(query, MemberEntity.class);
        return search
            .stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());
    }

    private CriteriaQuery createConditionCriteriaQuery(SearchCondition searchCondition) {
        CriteriaQuery query = new CriteriaQuery(new Criteria());

        if (searchCondition == null)
            return query;

        if (searchCondition.getId() != null)
            query.addCriteria(Criteria.where("id").is(searchCondition.getId()));

        if(searchCondition.getAge() > 0)
            query.addCriteria(Criteria.where("age").is(searchCondition.getAge()));

        if(StringUtils.hasText(searchCondition.getName()))
            query.addCriteria(Criteria.where("name").is(searchCondition.getName()));

        if(StringUtils.hasText(searchCondition.getNickname()))
            query.addCriteria(Criteria.where("nickname").is(searchCondition.getNickname()));

        if(searchCondition.getStatus() != null)
            query.addCriteria(Criteria.where("status").is(searchCondition.getStatus()));

        if(searchCondition.getSize() == null)
            query.addCriteria(Criteria.where("size").contains("260"));
        return query;
    }

}
