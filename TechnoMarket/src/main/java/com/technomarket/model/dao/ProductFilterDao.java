package com.technomarket.model.dao;

import com.technomarket.model.dtos.attribute.AttributeFilterDTO;
import com.technomarket.model.dtos.product.ProductFilterDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.pojos.Product;
import io.swagger.models.auth.In;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductFilterDao {
    @Autowired
    private DataBaseConnector connector;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductResponseDTO> findAllByFilter(ProductFilterDTO filter) {
        try {
            List<Integer> filterIds = findAllIdsByFilter(filter);
            List<Product> products = entityManager
                    .unwrap(Session.class)
                    .byMultipleIds(Product.class)
                    .multiLoad(filterIds);
            List<ProductResponseDTO> response = new ArrayList<>();
            for (Product product : products) {
                response.add(new ProductResponseDTO(product));
            }
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<Integer> findAllIdsByFilter(ProductFilterDTO filter) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT p.id,p.price FROM products AS p\n" +
                        "JOIN sub_categories AS sc \n" +
                        "ON p.sub_category_id = sc.id\n"
        );

        if (filter.getAttributeFilterDTOList() != null && !filter.getAttributeFilterDTOList().isEmpty()) {
            int i = 1;
            for (AttributeFilterDTO attributeFilterDTO : filter.getAttributeFilterDTOList()) {
                String name = '\'' + attributeFilterDTO.getName() + '\'';
                String value = '\'' + attributeFilterDTO.getValue() + '\'';
                String as = "f" + i;
                i++;
                sql.append("INNER JOIN (\n" +
                        "SELECT pha.product_id AS id FROM products_have_attributes AS pha\n" +
                        "JOIN attributes AS a\n" +
                        "ON pha.attribute_id=a.id\n");
                sql.append("WHERE a.name=" + name + " AND pha.value=" + value);
                sql.append(") AS " + as + " ON p.id=" + as + ".id\n");
            }
        }

        sql.append("WHERE sc.id=" + filter.getSubcategoryId() + "\n" +
                "AND p.price BETWEEN " + filter.getMin() + " AND " + filter.getMax() + "\n");
        if (filter.isOnDiscount()) {
            sql.append("AND p.discount_id IS NOT NULL\n");
        }
        if (filter.getListOfBrands() != null && !filter.getListOfBrands().isEmpty()) {
            sql.append("AND p.brand IN (");
            String prefix = "";
            for (String brand : filter.getListOfBrands()) {
                sql.append(prefix);
                prefix = ", ";
                sql.append('\'' + brand + '\'');
            }
            sql.append(")\n");
        }

        if("DESC".equals(filter.getOrderBy())){
            sql.append("ORDER BY p.price DESC\n");
        } else if("ASC".equals(filter.getOrderBy())){
            sql.append("ORDER BY p.price ASC\n");
        }

        List<Integer> ids = new ArrayList<>();
        System.out.println(sql.toString());

        PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql.toString());

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ids.add(resultSet.getInt("id"));
        }
        return ids;
    }
}
