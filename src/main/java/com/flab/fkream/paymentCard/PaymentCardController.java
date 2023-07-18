package com.flab.fkream.paymentCard;

import com.flab.fkream.error.exception.NotOwnedDataException;
import com.flab.fkream.salesAccount.SalesAccount;
import com.flab.fkream.utils.HttpRequestUtils;
import com.flab.fkream.utils.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping()
    public PageInfo<PaymentCard> findByUserId(@RequestParam int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(paymentCardService.findByUserId());
    }

    @GetMapping("/{id}")
    public PaymentCard findOne(@PathVariable Long id) {
        PaymentCard paymentCard = paymentCardService.findById(id);
        if (paymentCard.getUserId() == SessionUtil.getLoginUserId()) {
            return paymentCard;
        }
        throw new NotOwnedDataException();
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentCardService.deleteById(id);
    }
}
