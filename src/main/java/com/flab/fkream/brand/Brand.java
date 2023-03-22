package com.flab.fkream.brand;

import java.util.List;

import com.flab.fkream.item.Item;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
public class Brand {
	private Long id;
	private final String brandName;
	private final String luxury;
}
