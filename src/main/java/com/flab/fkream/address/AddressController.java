package com.flab.fkream.address;


import com.flab.fkream.utils.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("")
    public void addAddress(@Valid @RequestBody Address address) {
        addressService.addAddress(address);
    }

    @GetMapping()
    public PageInfo<Address> findOneByUserID(@RequestParam int pageNum,
        @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = SessionUtil.getLoginUserId();
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(addressService.findByUserId(userId));
    }

    @PatchMapping("/{id}")
    public void update(@Valid @RequestBody Address address) {
        addressService.update(address);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }
}
