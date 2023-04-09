package com.flab.fkream.paymentCard;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentCardService {

    private final PaymentCardMapper paymentCardMapper;

    public void save(PaymentCard paymentCard) {
        paymentCard.setCreatedAtToNow();
        paymentCard.encryptPassword();
        paymentCardMapper.save(paymentCard);
    }

    public List<PaymentCard> findByUserId(Long id) {
        return paymentCardMapper.findByUserId(id);
    }

    public PaymentCard findById(Long id) {
        return paymentCardMapper.findById(id);
    }

    public void deleteById(Long id) {
        paymentCardMapper.delete(id);
    }
}
