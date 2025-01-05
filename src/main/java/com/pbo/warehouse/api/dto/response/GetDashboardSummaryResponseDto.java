package com.pbo.warehouse.api.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;

public class GetDashboardSummaryResponseDto {
    private CategorySummary stockPerCategory;
    private CategorySummary itemPerCategory;
    private ProductByCategory topFivePerCategory;

    public GetDashboardSummaryResponseDto() {
    }

    public GetDashboardSummaryResponseDto(CategorySummary stockPerCategory, CategorySummary itemPerCategory,
            ProductByCategory topFivePerCategory) {
        this.stockPerCategory = stockPerCategory;
        this.itemPerCategory = itemPerCategory;
        this.topFivePerCategory = topFivePerCategory;
    }

    public CategorySummary getStockPerCategory() {
        return stockPerCategory;
    }

    public void setStockPerCategory(CategorySummary stockPerCategory) {
        this.stockPerCategory = stockPerCategory;
    }

    public CategorySummary getItemPerCategory() {
        return itemPerCategory;
    }

    public void setItemPerCategory(CategorySummary itemPerCategory) {
        this.itemPerCategory = itemPerCategory;
    }

    public ProductByCategory getTopFivePerCategory() {
        return topFivePerCategory;
    }

    public void setTopFivePerCategory(ProductByCategory topFivePerCategory) {
        this.topFivePerCategory = topFivePerCategory;
    }

    public static class CategorySummary {
        private int electronic;
        private int cosmetic;
        private int fnb;

        public CategorySummary() {
        }

        public CategorySummary(int electronic, int cosmetic, int fnb) {
            this.electronic = electronic;
            this.cosmetic = cosmetic;
            this.fnb = fnb;
        }

        public int getElectronic() {
            return electronic;
        }

        public void setElectronic(int electronic) {
            this.electronic = electronic;
        }

        public int getCosmetic() {
            return cosmetic;
        }

        public void setCosmetic(int cosmetic) {
            this.cosmetic = cosmetic;
        }

        public int getFnb() {
            return fnb;
        }

        public void setFnb(int fnb) {
            this.fnb = fnb;
        }
    }

    public static class ProductByCategory {
        private List<ProductElectronic> electronic = new ArrayList<>();
        private List<ProductCosmetic> cosmetic = new ArrayList<>();
        private List<ProductFnb> fnb = new ArrayList<>();

        public ProductByCategory() {
        }

        public ProductByCategory(List<ProductElectronic> electronic, List<ProductCosmetic> cosmetic,
                List<ProductFnb> fnb) {
            this.electronic = electronic;
            this.cosmetic = cosmetic;
            this.fnb = fnb;
        }

        public List<ProductElectronic> getElectronic() {
            return electronic;
        }

        public void setElectronic(List<ProductElectronic> electronic) {
            this.electronic = electronic;
        }

        public List<ProductCosmetic> getCosmetic() {
            return cosmetic;
        }

        public void setCosmetic(List<ProductCosmetic> cosmetic) {
            this.cosmetic = cosmetic;
        }

        public List<ProductFnb> getFnb() {
            return fnb;
        }

        public void setFnb(List<ProductFnb> fnb) {
            this.fnb = fnb;
        }
    }
}
