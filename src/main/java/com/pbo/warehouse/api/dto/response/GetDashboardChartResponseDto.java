package com.pbo.warehouse.api.dto.response;

public class GetDashboardChartResponseDto {
    private int year;
    private int month;
    private int daysCount;
    private int[] totalStock;
    private ChartDetail details;

    public static class ChartDetail {
        private ChartData electronic;
        private ChartData cosmetic;
        private ChartData fnb;

        public ChartDetail() {
        }

        public ChartDetail(ChartData electronic, ChartData cosmetic, ChartData fnb) {
            this.electronic = electronic;
            this.cosmetic = cosmetic;
            this.fnb = fnb;
        }

        public ChartData getElectronic() {
            return electronic;
        }

        public void setElectronic(ChartData electronic) {
            this.electronic = electronic;
        }

        public ChartData getCosmetic() {
            return cosmetic;
        }

        public void setCosmetic(ChartData cosmetic) {
            this.cosmetic = cosmetic;
        }

        public ChartData getFnb() {
            return fnb;
        }

        public void setFnb(ChartData fnb) {
            this.fnb = fnb;
        }
    }

    public static class ChartData {
        private int[] stock;
        private int[] inbound;
        private int[] outbound;

        public ChartData() {
        }

        public ChartData(int[] stock, int[] inbound, int[] outbound) {
            this.stock = stock;
            this.inbound = inbound;
            this.outbound = outbound;
        }

        public int[] getStock() {
            return stock;
        }

        public int[] getInbound() {
            return inbound;
        }

        public int[] getOutbound() {
            return outbound;
        }
    }

    public GetDashboardChartResponseDto() {
    }

    public GetDashboardChartResponseDto(int year, int month, int daysCount, int[] totalStock, ChartDetail details) {
        this.year = year;
        this.month = month;
        this.daysCount = daysCount;
        this.setTotalStock(totalStock);
        this.details = details;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public int[] getTotalStock() {
        return totalStock;
    }

    public ChartDetail getDetails() {
        return details;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public void setTotalStock(int[] totalStock) {
        if (totalStock == null) {
            this.totalStock = new int[0];
        } else if (totalStock.length != this.daysCount) {
            this.totalStock = new int[this.daysCount];
            for (int i = 0; i < this.daysCount; i++) {
                if (i < totalStock.length) {
                    this.totalStock[i] = totalStock[i];
                } else {
                    this.totalStock[i] = totalStock[totalStock.length - 1];
                }
            }
        }
    }

    public void setDetails(ChartDetail details) {
        this.details = details;
    }
}
