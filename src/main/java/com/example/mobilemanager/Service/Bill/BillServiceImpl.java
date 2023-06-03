package com.example.mobilemanager.Service.Bill;

import com.example.mobilemanager.Entity.*;
import com.example.mobilemanager.Exception.AlertBadException;
import com.example.mobilemanager.Model.DTO.BillDTO;
import com.example.mobilemanager.Model.DTO.BillItemDTO;
import com.example.mobilemanager.Repository.*;
import com.example.mobilemanager.Repository.Bill.BillItemRepository;
import com.example.mobilemanager.Repository.Bill.BillRepository;
import com.example.mobilemanager.Repository.Product.ProductRepoSitory;
import com.example.mobilemanager.Repository.Supplier.SupplierRepository;
import com.example.mobilemanager.Request.BillRequest.BillReq;
import com.example.mobilemanager.Request.BillRequest.BillRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {
    private final ProductRepoSitory productRepoSitory;

    private final BillRepository billRepository;

    private final BillItemRepository billItemRepository;

    private final ModelMapper mapper;

    private final SupplierRepository supplierRepository;

    private final StoreRepository storeRepository;


    @Autowired
    public BillServiceImpl(ProductRepoSitory productRepoSitory, BillRepository billRepository, BillItemRepository billItemRepository, ModelMapper mapper, SupplierRepository supplierRepository, StoreRepository storeRepository) {
        this.productRepoSitory = productRepoSitory;
        this.billRepository = billRepository;
        this.billItemRepository = billItemRepository;
        this.mapper = mapper;
        this.supplierRepository = supplierRepository;
        this.storeRepository = storeRepository;
    }


//    @Transactional(rollbackOn = {Exception.class})
//    @Override
//    public Bill createBill(BillRequest request) throws Exception {
//        Bill bill = new Bill();
//        bill.setBillCreateDate(new Date());
//        bill.setStatusBill(request.getStatusId());
//        List<Product> productList = new ArrayList<>();
//        float totalPrice = 0;
//        int totalQuantity = 0;
//        Supplier supplier = supplierRepository.findBySupplierPhoneNumber(request.getPhoneNumberSupplier())
//                .orElseThrow(() -> new RuntimeException("Phone number is not exist"));
//        bill.setSupplier(supplier);
//        for (BillReq item : request.getBillReqs()) {
//            Optional<Product> product = productRepoSitory.findById(item.getId());
//            if (product.isPresent()) {
//                BillItem billItem = BillItem.builder()
//                        .id(new BillProductId(product.get(), bill))
//                        .quantity(item.getQuantity())
//                        .totalPrice(product.get().getPrice() * item.getQuantity())
//                        .build();
//
//                productList.add(product.get());
//                billItemRepository.save(billItem);
////
//                totalPrice += billItem.getTotalPrice();
//                totalQuantity += billItem.getQuantity();
//                bill.setTotalValueProducts(totalPrice);
//                bill.setTotalProductQuantity(totalQuantity);
//                bill.setListProductBill(productList);
//            } else {
//                throw new Exception("product is not exist");
//
//            }
//        }
//        try {
//            bill = billRepository.save(bill);
//        } catch (DataIntegrityViolationException e) {
//            throw new RuntimeException("Khong the luu ");
//        }
//        return bill;
//    }


    @Override
    public Bill createBill(BillRequest request) throws Exception {
        List<BillItem> billItemList = new ArrayList<>();
        List<BillItemDTO> billItemDTOList = new ArrayList<>();
        float totalPrice = 0;
        int totalQuantity = 0;
        //làm thế nào để chỉ kiểm tra 1 lần thôi
        // in trong sql --> table product --> where id in (:idProduct)
        for (BillReq item : request.getBillReqs()) {
            Optional<Product> product = productRepoSitory.findById(item.getId());

            if (product.isEmpty()) {
                throw new Exception("Khong tim thay product co id la " + item.getId());
            }
            BillItem billItem = BillItem.builder()
                    .quantity(item.getQuantity())
                    .totalPrice(item.getQuantity() * product.get().getPrice())
                    .billItemProduct(product.get())
                    .build();
            totalPrice += billItem.getTotalPrice();
            totalQuantity += billItem.getQuantity();
            billItemList.add(billItem);

            BillItemDTO billItemDTO = mapper.map(billItem, BillItemDTO.class);
            billItemDTOList.add(billItemDTO);

        }
        billItemRepository.saveAll(billItemList);
        Bill bill = Bill.builder()
                .totalProductQuantity(totalQuantity)

                .totalValueProducts(totalPrice)
                .statusBill(request.getStatusId())
                .billCreateDate(new Date())
                .supplier(null)
                .billItemList(billItemList)
                .build();

        BillDTO billDTO = BillDTO.builder()
                .totalProductQuantity(totalQuantity)

                .totalValueProducts(totalPrice)
                .statusBill(request.getStatusId())
                .billCreateDate(new Date())
                .supplier(null)
                .billItemList(billItemDTOList).build();


        return bill;
    }


    @Override
    public BillDTO createBillDTO(BillRequest request) throws Exception {
        List<BillItem> billItemList = new ArrayList<>();
        List<BillItemDTO> billItemDTOList = new ArrayList<>();
        float totalPrice = 0;
        int totalQuantity = 0;
        for (BillReq item : request.getBillReqs()) {
            Optional<Product> product = productRepoSitory.findById(item.getId());

            if (!product.isPresent()) {
                throw new AlertBadException("Khong tim thay product co id la " + item.getId(), 500);
            }

            BillItem billItem = BillItem.builder()
                    .quantity(item.getQuantity())
                    .totalPrice(item.getQuantity() * product.get().getPrice())
                    .billItemProduct(product.get())
                    .build();
            totalPrice += billItem.getTotalPrice();
            totalQuantity += billItem.getQuantity();
            billItemList.add(billItem);
            product.get().setQuantityInStore(product.get().getQuantityInStore() + item.getQuantity());
            productRepoSitory.save(product.get());
            BillItemDTO billItemDTO = mapper.map(billItem, BillItemDTO.class);
            billItemDTOList.add(billItemDTO);


        }
        billItemRepository.saveAll(billItemList);
//        Bill bill = Bill.builder()
//                .totalProductQuantity(totalQuantity)
//
//                .totalValueProducts(totalPrice)
//                .statusBill(request.getStatusId())
//                .billCreateDate(new Date())
//                .billItemList(billItemList)
//                .build();


        Supplier supplier = supplierRepository.findBySupplierPhoneNumber(request.getPhoneNumberSupplier()).orElseThrow(() -> new RuntimeException("Not exist supplier by phonenumber"));

        BillDTO billDTO = BillDTO.builder()
                .totalProductQuantity(totalQuantity)
                .supplier(supplier)
                .totalValueProducts(totalPrice)
                .statusBill(request.getStatusId())
                .billCreateDate(new Date())
                .billItemList(billItemDTOList).build();

        return billDTO;
    }
}
