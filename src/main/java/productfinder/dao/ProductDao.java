package productfinder.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;
    @Autowired
    private RowMapper<Product> productMapper;

    public void create(Product product) {
        String SQL = "insert into Product (id, price, currency, site, description, productType, postedat) values (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplateObject.update(SQL, product.getID(),
                product.getPrice(),
                product.getCurrency(),
                product.getSite(),
                product.getDescription(),
                product.getProductType(),
                product.getPostedAt());
    }

    public boolean isIdInDb(String id) {
        String SQL = "select count(*) from product where id=?";
        Integer count = jdbcTemplateObject.queryForObject(
                SQL, Integer.class, id);
        return count != null && count > 0;
    }
}
