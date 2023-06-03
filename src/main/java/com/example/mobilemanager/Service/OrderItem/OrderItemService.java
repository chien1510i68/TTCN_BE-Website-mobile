package com.example.mobilemanager.Service.OrderItem;

import com.example.mobilemanager.Entity.OrderItem;
import com.example.mobilemanager.Model.DTO.OrderItemDTO;
import com.example.mobilemanager.Repository.Order.OrderItemRepository;
import com.example.mobilemanager.Repository.Product.ProductRepoSitory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository ;

    private final ModelMapper mapper;

    private final ProductRepoSitory productRepoSitory;
    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, ModelMapper mapper, ProductRepoSitory productRepoSitory) {
        this.orderItemRepository = orderItemRepository;
        this.mapper = mapper;
        this.productRepoSitory = productRepoSitory;
    }


    public OrderItemDTO getInforOrderItem(Long id) throws Exception {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if(!orderItem.isPresent()){
            throw  new Exception("Id not found !");
        }
        return mapper.map(orderItem.get() , OrderItemDTO.class);
    }

    public List<OrderItem> itemList(List<Long> ids) {
        List<OrderItem> orderItems = orderItemRepository.findAllById(ids);
        return orderItems;
    }

    public OrderItemDTO getOrderItem(Long id) throws Exception {
        Optional<OrderItem> orderItemEntity = orderItemRepository.findById(id);
        if (!orderItemEntity.isPresent()) {
            throw new Exception("ID not found !");
        }
        return mapper.map(orderItemEntity.get(), OrderItemDTO.class);
    }
}
