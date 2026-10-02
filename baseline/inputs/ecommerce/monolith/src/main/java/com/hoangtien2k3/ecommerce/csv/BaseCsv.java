package com.hoangtien2k3.ecommerce.csv;

import com.hoangtien2k3.ecommerce.csv.anotation.CsvColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class BaseCsv {
    @CsvColumn(columnName = "Id")
    private Long id;
}
