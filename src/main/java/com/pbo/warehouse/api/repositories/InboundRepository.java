package com.pbo.warehouse.api.repositories;

import java.sql.Date;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.repositories.interfaces.InboundRepositoryIf;

public class InboundRepository implements InboundRepositoryIf {

    @Override
    public int getTotalData(String productCategory) {
        /*
         * TODO: implement this logics
         * - query hint: use count, join in_out with product table, and group by
         * - if productCategory is null, return total data of all products
         * - if productCategory is either 'electronic', 'cosmetic', or 'fnb', return total data of products with the specified category
         * - if productCategory is not null and not 'electronic', 'cosmetic', or 'fnb', return 0
         * - if there is an exception, return -1
         */

        throw new UnsupportedOperationException("Unimplemented method 'getTotalData'");
    }

    @Override
    public List<InOutRecord> getAllRecords(GetAllInOutRequestDto params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRecords'");
    }

    @Override
    public InOutRecord getRecordById(int id) {
        /*
         * TODO: implement this logics
         * - query hint: join in_out_records, products, stock_records, and users table
         * - return InOutRecord
         */
        throw new UnsupportedOperationException("Unimplemented method 'getRecordById'");
    }

    @Override
    public InOutRecord getRecordByDateAndProductId(Date date, String productId) {
        /*
         * TODO: implement this logics
         * - query hint: select * from in_out_records where record_date = ? and product_id = ?
         * - return InOutRecord
         */
        throw new UnsupportedOperationException("Unimplemented method 'getRecordByDateAndProductId'");
    }

    @Override
    public void insertRecord(InOutRecord record) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertRecord'");
    }

    @Override
    public void updateRecord(InOutRecord record) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRecord'");
    }

    @Override
    public boolean deleteRecord(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRecord'");
    }

}
