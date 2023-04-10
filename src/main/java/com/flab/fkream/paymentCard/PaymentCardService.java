package com.flab.fkream.paymentCard;

import com.flab.fkream.error.exception.NoDataFoundException;
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
        List<PaymentCard> paymentCards = paymentCardMapper.findByUserId(id);
        if (paymentCards.size() == 0) {
            throw new NoDataFoundException();
        }
        return paymentCards;

    }

    public PaymentCard findById(Long id) {
        PaymentCard paymentCard = paymentCardMapper.findById(id);
        if (paymentCard == null) {
            throw new NoDataFoundException();
        }
        return paymentCard;
    }

    public void deleteById(Long id) {
        paymentCardMapper.delete(id);
    }
}
