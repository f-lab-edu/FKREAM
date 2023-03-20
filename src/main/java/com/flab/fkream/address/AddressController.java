package com.flab.fkream.address;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
@RequiredArgsConstructor
public class AddressController {


    @GetMapping("/default/{userId}")
    public List<Address> findByAll(@PathVariable("userId") Long userId){
        return null;
    }

}
