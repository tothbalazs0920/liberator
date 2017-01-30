package productfinder.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProductMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

        String id = rs.getString("id");
        double price = Double.parseDouble(rs.getString("price"));
        String currency = rs.getString("currency");
        String site = rs.getString("site");
        String description = rs.getString("description");
        String productType = rs.getString("productType");
        Timestamp postedAt = rs.getTimestamp("postedat");
        Product product = new Product(id,
                price,
                currency,
                site,
                description,
                productType,
                postedAt);
        return product;
    }
}