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

    public Product isIdInDb(String id) {
        String SQL = "select exists(select 1 from product where id=?)";
        Product message = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{id.toString()}, productMapper);
        return message;
    }
}
