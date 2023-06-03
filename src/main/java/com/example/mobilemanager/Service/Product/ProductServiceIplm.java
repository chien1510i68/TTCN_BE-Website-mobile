package com.example.mobilemanager.Service.Product;

import com.example.mobilemanager.Entity.Product;
import com.example.mobilemanager.Model.DTO.ProductDTO;
import com.example.mobilemanager.Repository.Product.CustomProductRepository;
import com.example.mobilemanager.Repository.Product.ProductRepoSitory;
import com.example.mobilemanager.Request.ProductRequest.*;
import com.example.mobilemanager.Response.ResponseSuccess.SoldResponse;
import com.example.mobilemanager.Response.ResponseSuccess.StatisticalPhoneResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceIplm implements ProductService {
    private final ModelMapper mapper;
    private final ProductRepoSitory productRepo;


    @Autowired
    public ProductServiceIplm(ModelMapper mapper, ProductRepoSitory productRepo) {
        this.mapper = mapper;
        this.productRepo = productRepo;
    }


    @Override
    public ProductDTO addMobile(ProductRequest productRequest) {
        Product entity = Product.builder()
                .price(productRequest.getPrice())
                .image(productRequest.getImage())
                .productName((productRequest.getProductName()))
                .memoryStick(productRequest.getMemoryStick())
                .camera(productRequest.getCamera())
                .memory(productRequest.getMemory())
                .operatingSystem(productRequest.getOperatingSystem())
                .battery(productRequest.getBattery())
                .size(productRequest.getSize())
                .color(productRequest.getColor())
                .manufacturer(productRequest.getManufacturer())
                .warrantyPeriod(productRequest.getWarrantyPeriod())
//                .quantityInStore(productRequest.getQuantityInStore())
//                .promotionID(productRequest.getPromotionID())
                .build();


        productRepo.save(entity);
        return mapper.map(entity, ProductDTO.class);

//        return mapper.convertProdToDTO(entity);
    }


    @Override
    public ProductDTO getByID(long id) throws Exception {
        Optional<Product> entity = productRepo.findById(id);
        Product product = null;
        if (!entity.isPresent()) {
            throw new Exception("ID is invalid");
        } else {
            product = entity.get();
        }
//        ProdDTO prd =  ProductMapper.INSTANCE.toProdentityToProductDTO(productEntity);
//        return prd;
//        return mapper.convertProdToDTO(productEntity);
        return mapper.map(product, ProductDTO.class);


    }

    @Override
    public List<ProductDTO> getAllMobile() {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product entity : productRepo.findAll()) {
            dtoList.add(mapper.map(entity, ProductDTO.class));
        }
        return dtoList;
    }

    @Override
    public ProductDTO updateMobile(ProductRequest pe) throws Exception {
        Optional<Product> productEntity = productRepo.findById(pe.getProductId());
        if (!productEntity.isPresent()) {
            throw new Exception("ID is invalid");
        } else {
            Product entity = productEntity.get();
            entity.setPrice(pe.getPrice());
            entity.setImage(pe.getImage());
            entity.setQuantityInStore(pe.getQuantityInStore());
            entity.setProductName(pe.getProductName());
            entity.setMemoryStick(pe.getMemoryStick());
            entity.setCamera(pe.getCamera());
            entity.setMemory(pe.getMemory());
            entity.setOperatingSystem(pe.getOperatingSystem());
            entity.setBattery(pe.getBattery());
            entity.setSize(pe.getSize());
            entity.setColor(pe.getColor());
            entity.setWarrantyPeriod(pe.getWarrantyPeriod());
//            entity.setPromotionID(pe.getPromotionID());
            productRepo.save(entity);

//            return mapper.convertProdToDTO(entity);
            return mapper.map(entity, ProductDTO.class);
        }
    }

    @Override
    public ProductDTO deleteByID(Long id) throws Exception {
        Optional<Product> entity = productRepo.findById(id);
        if (!entity.isPresent()) {
            throw new Exception("ID is invalid");
        } else {
            ProductDTO productDTO = mapper.map(entity.get(), ProductDTO.class);
            productRepo.deleteById(id);
            return productDTO;

        }
    }

    @Override

    public List<ProductDTO> deleteList(List<Long> ids) {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product entity : productRepo.findAllById(ids)) {
//           dtoList.add(ProductMapper.INSTANCE.toProdentityToProductDTO(entity));
            dtoList.add(mapper.map(entity, ProductDTO.class));
        }
        productRepo.deleteAllByIdInBatch(ids);
        return dtoList;

    }

    @Override
    public Page<ProductDTO> getAllProduct(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> products = productRepo.findAll(pageable);
        List<ProductDTO> dtoList = products.getContent().stream().map(i -> mapper.map(i, ProductDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }

    //    @Override
//    public Page<Product> filterListProduct(FilterProductRequest request) {
//        Specification<Product> specification = CustomProductRepository.filterProductByCondition(
//                request.getPriceFrom(),
//                request.getPriceTo(),
//                request.getManufacturer(),
//                request.getColor(),
//                request.getMemory(),
//                request.getProductId()
//        );
//        return productRepo.findAll(specification, PageRequest.of(request.getPage(), request.getSize()));
//    }
    @Override
    public Page<Product> filterListProduct(FilterProductRequest request) {
        Specification<Product> specification = CustomProductRepository.filterProductByCondition(
                request.getPriceFrom(),
                request.getPriceTo(),
                request.getManufacturer(),
                request.getColor(),
                request.getMemory(),
                request.getProductId(),
                request.getProductName()
        );
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        if (request.getKeySort() == 1) {
            List<Product> products = productRepo.findAllByOrderByProductIdAsc();
            return new PageImpl<>(products, pageable, products.size());
        } else if (request.getKeySort() == 2) {
            List<Product> products = productRepo.findAllByOrderByQuantityInStoreDesc();
            return new PageImpl<>(products, pageable, products.size());
        }

        return productRepo.findAll(specification, pageable);
    }



    @Override
    public Page<ProductDTO> sortProduct(SortRequest request) {

        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        if (request.getKey() == 1) {
            List<Product> products = productRepo.findAllByOrderByProductIdAsc();

            List<ProductDTO> dtoList = products.stream()
                    .map(i -> mapper.map(i, ProductDTO.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(dtoList, pageable, dtoList.size());
        } else if (request.getKey() == 2) {
            List<Product> products = productRepo.findAllByOrderByQuantityInStoreDesc();
            List<ProductDTO> dtoList = products.stream()
                    .map(i -> mapper.map(i, ProductDTO.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(dtoList, pageable, dtoList.size());
        }
        return null;

    }

    @Override
    public List<ProductDTO> getProductByListId(List<Long> ids) {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product entity : productRepo.findAllById(ids)) {
//           dtoList.add(ProductMapper.INSTANCE.toProdentityToProductDTO(entity));
            dtoList.add(mapper.map(entity, ProductDTO.class));
        }

        return dtoList;


    }


    @Override
    public List<StatisticalPhoneResponse> statisticalPhoneByManufacturer(StatisticalPhoneReq req) {
        List<StatisticalPhoneResponse> productList = new ArrayList<>();
        for (Product product : productRepo.findAll()) {
            if (product.getManufacturer().equals(req.getManufacturer())) {
                StatisticalPhoneResponse response = new StatisticalPhoneResponse();
                response.setPhoneName(product.getProductName());
                response.setQuantity(product.getQuantityInStore());
                productList.add(response);
            }
        }


        return productList;
    }

    @Override
    public List<StatisticalPhoneResponse> InventoryLowQuantity(StatisticalPhoneReq req) {
        List<StatisticalPhoneResponse> phoneResponses = new ArrayList<>();
        for (Product product : productRepo.findAll()) {
            if (product.getManufacturer().equals(req.getManufacturer()) && product.getQuantityInStore() < req.getQuantity()) {
                StatisticalPhoneResponse response = new StatisticalPhoneResponse();
                response.setQuantity(product.getQuantityInStore());
                response.setPhoneName(product.getProductName());
                phoneResponses.add(response);
            }
        }

        return phoneResponses;
    }
    public List<SoldResponse> statisticalSold() {
        List<SoldResponse> soldResponses = new ArrayList<>();

        List<Object[]> manufacturerAndSoldCount = productRepo.findManufacturerAndSoldCount();
        for (Object[] result : manufacturerAndSoldCount) {
            SoldResponse response = new SoldResponse();
            response.setManufacturer(result[0].toString());
            response.setSold((Long)result[1]);
            soldResponses.add(response);
        }
        return soldResponses;
    }

}




