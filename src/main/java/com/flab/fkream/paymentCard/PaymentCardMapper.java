package com.flab.fkream.paymentCard;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentCardMapper {

    int save(PaymentCard paymentCard);

    List<PaymentCard> findByUserId(Long id);

    PaymentCard findById(Long id);

    int delete(Long id);
}
