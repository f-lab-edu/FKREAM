package com.flab.fkream.paymentCard;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentCardMapper {

    void save(PaymentCard paymentCard);
}
