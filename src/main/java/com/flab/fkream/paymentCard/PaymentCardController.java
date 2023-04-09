package com.flab.fkream.paymentCard;

import com.flab.fkream.salesAccount.SalesAccount;
import java.util.List;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-cards")
public class PaymentCardController {

    private final PaymentCardService paymentCardService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody @NonNull PaymentCard paymentCard) {

        paymentCardService.save(paymentCard);
    }

    // salesAccount 리스트 조회
    @GetMapping("/users/{id}")
    public List<PaymentCard> findByUserId(@PathVariable Long id) {
        return paymentCardService.findByUserId(id);
    }

    // salesAccount 단건 조회
    @GetMapping("/{id}")
    public PaymentCard findOne(@PathVariable Long id) {
        return paymentCardService.findById(id);
    }


    // salesAccount 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentCardService.deleteById(id);
    }
}
