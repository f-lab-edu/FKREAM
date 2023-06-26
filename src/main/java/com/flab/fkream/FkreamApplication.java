package com.flab.fkream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
@EnableMongoRepositories
@ConfigurationPropertiesScan(value = "com.flab.fkream.sharding")
public class FkreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FkreamApplication.class, args);
	}


}
/**
 * GET _search
 * {
 *   "query": {
 *     "match_all": {}
 *   }
 * }
 *
 * PUT movie_kibana_execute/_doc/1
 * {
 *   "message":"helloworld"
 * }
 *
 * GET movie_kibana_execute/_search
 * {
 *   "query": {
 *     "match_all": {}
 *   }
 * }
 *
 * PUT /movie
 * {
 *   "mappings": {
 *     "properties" : {
 *       "movieCd": { "type" : "integer"},
 *       "movieNm": { "type" : "text"},
 *       "movieNmEn": { "type" : "text"},
 *       "prdtYear": { "type" : "integer"},
 *       "openDt": { "type" : "date"},
 *       "typeNm": { "type" : "keyword"},
 *       "prdtStatNm": { "type" : "keyword"},
 *       "nationAlt": { "type" : "keyword"},
 *       "genreAlt": { "type" : "keyword"},
 *       "repNationNm": { "type" : "keyword"},
 *       "repGenreNm": { "type" : "keyword"}
 *     }
 *   }
 * }
 *
 * GET /movie
 * DELETE /movie
 * POST /movie/_doc/1
 * {
 *   "movieCd": "1",
 * 	"movieNm": "살아남은 아이",
 * 	"movieNmEn": "Last Child" ,
 * 	"prdtYear": "2017",
 * 	"openDt": "2017-10-20",
 * 	"typeNm": "장편",
 * 	"prdtStatNm": "기타",
 * 	"nationAlt": "한국",
 * 	"genreAlt": "드라마, 가족",
 * 	"repNationNm": "한국",
 * 	"repGenreNm": "드라마"
 * }
 *
 * GET /movie/_doc/1
 *
 * GET /movie/_doc/_search?q=prdtYear:2017&pretty=true
 *
 * GET /movie/_search?q=장편
 *
 * GET /movie/_search?q=typeNm:장편
 *
 * POST /movie/_doc
 * {
 *   "movieCd": "2",
 *   "movieNm": "시간을 달리는 소녀",
 *   "movieNmEn": "The Girl Who Leapt Through Time",
 *   "prdtYear": "2006",
 *   "openDt": "2006-07-15",
 *   "typeNm": "장편",
 *   "prdtStatNm": "기타",
 *   "nationAlt": "일본",
 *   "genreAlt": "애니메이션, 드라마, 판타지",
 *   "repNationNm": "일본",
 *   "repGenreNm": "애니메이션"
 * }
 *
 * POST /movie/_doc
 * {
 *   "movieCd": "3",
 *   "movieNm": "어벤져스: 엔드게임",
 *   "movieNmEn": "Avengers: Endgame",
 *   "prdtYear": "2019",
 *   "openDt": "2019-04-24",
 *   "typeNm": "장편",
 *   "prdtStatNm": "기타",
 *   "nationAlt": "미국",
 *   "genreAlt": "액션, 모험, 판타지",
 *   "repNationNm": "미국",
 *   "repGenreNm": "액션"
 * }
 *
 * POST /movie/_doc
 * {
 *   "movieCd": "4",
 *   "movieNm": "극한직업",
 *   "movieNmEn": "Extreme Job",
 *   "prdtYear": "2019",
 *   "openDt": "2019-01-23",
 *   "typeNm": "장편",
 *   "prdtStatNm": "기타",
 *   "nationAlt": "한국",
 *   "genreAlt": "코미디, 범죄",
 *   "repNationNm": "한국",
 *   "repGenreNm": "코미디"
 * }
 *
 * POST /movie/_doc
 * {
 *   "movieCd": "5",
 *   "movieNm": "라라랜드",
 *   "movieNmEn": "La La Land",
 *   "prdtYear": "2016",
 *   "openDt": "2016-12-07",
 *   "typeNm": "장편",
 *   "prdtStatNm": "기타",
 *   "nationAlt": "미국",
 *   "genreAlt": "로맨스, 뮤지컬, 드라마",
 *   "repNationNm": "미국",
 *   "repGenreNm": "로맨스"
 * }
 *
 * POST /movie/_doc
 * {
 *   "movieCd": "6",
 *   "movieNm": "해리포터와 마법사의 돌",
 *   "movieNmEn": "Harry Potter and the Philosopher's Stone",
 *   "prdtYear": "2001",
 *   "openDt": "2001-11-04",
 *   "typeNm": "장편",
 *   "prdtStatNm": "기타",
 *   "nationAlt": "영국",
 *   "genreAlt": "어드벤처, 판타지",
 *   "repNationNm": "영국",
 *   "repGenreNm": "어드벤처"
 * }
 *
 * GET /movie/_search
 *
 * POST /movie/_search
 * {
 *   "query": {
 *     "bool": {
 *       "must": [
 *         {
 *           "term": {
 *             "typeNm": "장편"
 *           }
 *         },
 *         {
 *           "match": {
 *             "prdtYear": "2019"
 *           }
 *         }
 *       ],
 *       "filter": {
 *         "term": {
 *           "movieCd": "3"
 *         }
 *       }
 *     }
 *   }
 * }
 *
 * POST /movie/_search?size=0
 * {
 *   "aggs": {
 *     "genre": {
 *       "terms": {
 *         "field": "genreAlt"a
 *       },
 *       "aggs": {
 *         "nation": {
 *           "terms": {
 *             "field": "nationAlt"
 *           }
 *         }
 *       }
 *     }
 *   }
 * }
 */