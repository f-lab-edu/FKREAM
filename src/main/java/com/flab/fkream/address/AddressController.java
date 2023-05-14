package com.flab.fkream.address;


import com.flab.fkream.aop.Paging;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
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

    @GetMapping("/{id}")
    public Address findOne(@PathVariable Long id) {
        return addressService.findOne(id);
    }

    @GetMapping("/user/{userId}")
    public PageInfo<Address> findOneByUserID(@PathVariable Long userId, @RequestParam int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
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
