package com.flab.fkream.interestItemCount;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestItemCountRepository extends MongoRepository<InterestItemCount, String> {

    InterestItemCount findByItemId(Long itemId);

}
