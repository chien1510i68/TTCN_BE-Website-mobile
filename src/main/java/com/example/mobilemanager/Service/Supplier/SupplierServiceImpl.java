package com.example.mobilemanager.Service.Supplier;

import com.example.mobilemanager.Constant.DateTimeConstant;
import com.example.mobilemanager.Entity.Supplier;
import com.example.mobilemanager.Model.DTO.SupplierDTO;
import com.example.mobilemanager.Repository.Supplier.CustomSupplierRepository;
import com.example.mobilemanager.Repository.Supplier.SupplierRepository;
import com.example.mobilemanager.Request.Supplier.SupplierRequest;
import com.example.mobilemanager.utils.MyUntil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper mapper;

    private final CustomSupplierRepository customSupplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper mapper, CustomSupplierRepository customSupplierRepository) {
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
        this.customSupplierRepository = customSupplierRepository;
    }


    @Override
    public SupplierDTO createSupplier(SupplierRequest sp) throws ParseException {
        Supplier supplier = Supplier.builder()
                .supplierName(sp.getSupplierName())
                .supplierAddr(sp.getSupplierAddr())
                .supplierPhoneNumber(sp.getSupplierPhoneNumber())
                .supplierEmail(sp.getSupplierEmail())
                .supplierWebsite(sp.getSupplierWebsite())
                .supplierAccountNumber(sp.getSupplierAccountNumber())
                .bank(sp.getBank())
                .bankAccountName(sp.getBankAccountName())
//                .importDate(MyUntil.convertDateFromString(sp.getImportDate(), DateTimeConstant.DATE_FORMAT))
                .build();
        supplierRepository.save(supplier);


        return mapper.map(supplier, SupplierDTO.class);
    }

    @Override
    public SupplierDTO getSupplierById(Long id) throws Exception {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (!supplier.isPresent()) {
            throw new Exception("Id is not found");
        }
        return mapper.map(supplier.get(), SupplierDTO.class);
    }

    @Override
    public SupplierDTO deleteSupplier(Long id) throws Exception {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (!supplier.isPresent()) {
            throw new Exception("Id is not found");
        }
        supplierRepository.deleteById(id);
        return mapper.map(supplier.get(), SupplierDTO.class);
    }

    @Override
    public SupplierDTO updateSupplier(SupplierRequest sp, Long id) throws Exception {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (!supplier.isPresent()) {
            throw new Exception("Id is not found");
        }
        supplier.get().setSupplierAddr(sp.getSupplierAddr());
        supplier.get().setSupplierName(sp.getSupplierName());
        supplier.get().setSupplierEmail(sp.getSupplierEmail());
        supplier.get().setSupplierWebsite(sp.getSupplierWebsite());
        supplier.get().setBank(sp.getBank());
        supplier.get().setBankAccountName(sp.getBankAccountName());
        supplier.get().setSupplierPhoneNumber(sp.getSupplierPhoneNumber());
        supplier.get().setSupplierAccountNumber(sp.getSupplierAccountNumber());
//        supplier.get().setImportDate(MyUntil.convertDateFromString(sp.getImportDate(), DateTimeConstant.DATE_FORMAT));
        return mapper.map(supplier.get(), SupplierDTO.class);
    }

    @Override
    public Page<SupplierDTO> getAllSupplier(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Supplier> suppliers = supplierRepository.findAll(pageable);

        List<SupplierDTO> dtoList = suppliers.getContent().stream()
                .map(i -> mapper.map(i, SupplierDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }

    @Override
    public Page<Supplier> getSupplierByCondition(Integer page, Integer size, Date dateFrom, Date dateTo) {
        Specification<Supplier> specification = CustomSupplierRepository.filterSupplierByCondition(dateFrom, dateTo);
        return supplierRepository.findAll(specification, PageRequest.of(page, size));
    }

    @Override
    public Page<SupplierDTO> getSupplierImportDateDESC(int page , int size) {
        Pageable pageable = PageRequest.of(page,size);
//        List<Supplier> supplierList = supplierRepository.findAllByOrderByImportDateDesc();
        List<SupplierDTO> supplierDTOS = supplierRepository.findAllByOrderByImportDateDesc().stream()
                .map(i -> mapper.map(i , SupplierDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(supplierDTOS , pageable , supplierDTOS.size());
    }

    public Page<SupplierDTO> suppliersSort(int page , int size){
       Pageable pageable = PageRequest.of(page, size , Sort.by("importDate"));
       Page<Supplier> suppliers = supplierRepository.findAll(pageable);
       List<SupplierDTO> supplierDTOS = suppliers.getContent().stream()
               .map(i -> mapper.map(i , SupplierDTO.class)).collect(Collectors.toList());
       return new PageImpl<>(supplierDTOS , pageable , supplierDTOS.size());
    }
}
