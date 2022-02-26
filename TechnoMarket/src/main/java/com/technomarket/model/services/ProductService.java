package com.technomarket.model.services;


import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.exceptions.BadRequestException;
import com.technomarket.exceptions.NotFoundException;
import com.technomarket.model.compositekeys.ProductAttributeKey;
import com.technomarket.model.dao.ProductFilterDao;
import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.ProductImageDTO;
import com.technomarket.model.dtos.attribute.AttributeAddValueToProductDTO;
import com.technomarket.model.dtos.attribute.AttributeFilterDTO;
import com.technomarket.model.dtos.product.ProductAddDTO;
import com.technomarket.model.dtos.product.ProductFilterDTO;
import com.technomarket.model.dtos.product.ProductKeywordsDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.event.OnProductDiscountEvent;
import com.technomarket.model.event.OnRegistrationCompleteEvent;
import com.technomarket.model.pojos.Attributes;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.pojos.ProductImage;
import com.technomarket.model.relationentity.ProductAttribute;
import com.technomarket.model.repositories.AttributeRepository;
import com.technomarket.model.repositories.ProductAttributeRepository;
import com.technomarket.model.repositories.ProductImageRepository;
import com.technomarket.model.repositories.ProductRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ProductFilterDao productFilterDao;

    @Transactional
    public ProductResponseDTO addProduct(ProductAddDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new BadRequestException("Product with that name already exists");
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setSubcategory(subcategoryService.getWholeById(productDTO.getSubcategoryId()));
        product.setPrice(productDTO.getPrice());
        product.setInfo(productDTO.getInfo());
        if (productDTO.getDiscountId() == 0) {
            product.setDiscount(null);
        } else {
            product.setDiscount(discountService.getWholeDiscountById(productDTO.getDiscountId()));
        }


        productRepository.save(product);

        return new ProductResponseDTO(product);
    }

    public ProductResponseDTO getById(int id) {
        if (!productRepository.existsById(id)) {
            throw new BadRequestException("Product with this id doesn't exist");
        }
        return new ProductResponseDTO(productRepository.getById(id));
    }

    public ProductResponseDTO addDiscountToProduct(int productId, int discountId) {
        if (!productRepository.existsById(productId)) {
            throw new BadRequestException("Product with this id doesn't exist");
        }
        Product product = productRepository.getById(productId);
        product.setDiscount(discountService.getWholeDiscountById(discountId));
        productRepository.save(product);

        eventPublisher.publishEvent(new OnProductDiscountEvent(product));


        return new ProductResponseDTO(product);
    }

    public MessageDTO deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new BadRequestException("Product with this id doesn't exist");
        }
        Product product = productRepository.getById(id);
        productRepository.delete(product);
        return new MessageDTO("Delete successful", LocalDateTime.now());
    }

    @Transactional
    public ProductResponseDTO addAttributeToProduct(AttributeAddValueToProductDTO dto, int productId, int attId) {
        if (!productRepository.existsById(productId)) {
            throw new BadRequestException("Product with this id doesn't exist");
        }
        if (!attributeRepository.existsById(attId)) {
            throw new BadRequestException("Attribute with this id doesn't exist");
        }
        Product product = productRepository.getById(productId);
        Attributes attribute = attributeRepository.getById(attId);
        if (!product.getSubcategory().getAllowedAttributes().stream().anyMatch(x -> x.equals(attribute))) {
            throw new AuthorizationException("Subcategory doesn't allow this attribute");
        }

        ProductAttribute productAttribute = new ProductAttribute();

        ProductAttributeKey key = new ProductAttributeKey();

        key.setAttributeId(attId);
        key.setProductId(productId);

        productAttribute.setId(key);
        productAttribute.setProduct(product);
        productAttribute.setAttribute(attribute);
        productAttribute.setValue(dto.getValue());

        product.getProductAttribute().add(productAttribute);
        attribute.getProductAttribute().add(productAttribute);

        productAttributeRepository.save(productAttribute);

        return new ProductResponseDTO(product);
    }

    public MessageDTO deleteAttributeFromProduct(int productId, int attId) {

        ProductAttributeKey key = new ProductAttributeKey();

        key.setAttributeId(attId);
        key.setProductId(productId);

        if (!productAttributeRepository.existsById(key)) {
            throw new BadRequestException("Product with this id doens't have this attribute");
        }

        ProductAttribute toDelete = productAttributeRepository.getById(key);
        productAttributeRepository.delete(toDelete);

        return new MessageDTO("Attribute removed from product", LocalDateTime.now());
    }

    @SneakyThrows
    public MessageDTO uploadImageToProduct(MultipartFile file, int productId) {

        String name = String.valueOf(System.nanoTime());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        File f = new File("Technomarket_FinalProject\\TechnoMarket\\uploads" + File.separator + name + "." + extension);
        Files.copy(file.getInputStream(), Path.of(f.toURI()), StandardCopyOption.REPLACE_EXISTING);

        if (!productRepository.existsById(productId)) {
            throw new BadRequestException("Product with this id doesn't exist");
        }


        Product product = productRepository.getById(productId);

        ProductImage productImage = new ProductImage();
        productImage.setUrl(f.getName());
        productImage.setProduct(product);
        productImageRepository.save(productImage);
        return new MessageDTO("Image was uploaded", LocalDateTime.now());
    }

    public ProductImageDTO getImageById(int id) {

        if (!productImageRepository.existsById(id)) {
            throw new NotFoundException("Product image not found");
        }
        return new ProductImageDTO(productImageRepository.getById(id));
    }

    public List<ProductResponseDTO> searchWithFilters(ProductFilterDTO dto) {
        return productFilterDao.findAllByFilter(dto);
    }

    public List<ProductResponseDTO> searchWithKeywords(ProductKeywordsDTO dto) {
        if(dto.getKeywords()==null|| dto.getKeywords().isEmpty()){
            throw new BadRequestException("Enter words to search by");
        }

        List<Product> products = productRepository.findAll();
        Map<Product,Integer> map = new HashMap<>();

        for (Product product : products) {
            int value = calculateSimilarValue(product,dto.getKeywords());
            if(value==0) continue;
            map.put(product,value);
        }

        SortedSet<Map.Entry<Product,Integer>> sortedMap;
        sortedMap = new TreeSet<>((o1, o2) -> {
            if(o1.getValue()==o2.getValue()) return 1;
            return o2.getValue()-o1.getValue();
        });
        sortedMap.addAll(map.entrySet());

        List<ProductResponseDTO> response = new ArrayList<>();
        for (Map.Entry<Product,Integer> entry : sortedMap) {
            Product product = entry.getKey();
            response.add(new ProductResponseDTO(product));
        }
        return response;
    }

    private int calculateSimilarValue(Product product, List<String> keywords) {
        int count = 0;
        if(keywords.stream().anyMatch(x->x.equalsIgnoreCase(product.getBrand()))){
            count++;
        }
        for (String prod : product.getName().split(" ")) {
            for (String keyword : keywords) {
                if(prod.equalsIgnoreCase(keyword)){
                    count++;
                }
            }
        }
        return count;
    }
}
