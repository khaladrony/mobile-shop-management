package com.rony.erpsoft.inventory.itemmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponse {
    private Map<String, List<MenuItemDTO>> itemsByBrand;
    private List<String> categories;
    private List<String> brands;
}
