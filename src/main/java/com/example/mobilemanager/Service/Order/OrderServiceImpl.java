package com.example.mobilemanager.Service.Order;

import com.example.mobilemanager.Entity.Order;
import com.example.mobilemanager.Entity.OrderItem;
import com.example.mobilemanager.Entity.Product;
import com.example.mobilemanager.Entity.Promotion;
import com.example.mobilemanager.Model.DTO.OrderDTO;
import com.example.mobilemanager.Model.DTO.OrderItemDTO;
import com.example.mobilemanager.Model.Revenue;
import com.example.mobilemanager.Repository.Order.CustomOrderRepository;
import com.example.mobilemanager.Repository.Order.OrderItemRepository;
import com.example.mobilemanager.Repository.Order.OrderRepository;
import com.example.mobilemanager.Repository.Product.ProductRepoSitory;
import com.example.mobilemanager.Repository.Promotion.PromotionRepository;
import com.example.mobilemanager.Repository.StoreRepository;
import com.example.mobilemanager.Request.OrderRequest.*;
import com.example.mobilemanager.Request.ProductRequest.PaginationRequest;
import com.example.mobilemanager.Service.Product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper mapper;

    private final ProductService productService;
    private final ProductRepoSitory productRepoSitory;

    private final StoreRepository storeRepository;

    private final PromotionRepository promotionRepository;


    @Autowired

    public OrderServiceImpl(OrderItemRepository orderItemRepository,
                            ProductRepoSitory productRepoSitory,
                            ModelMapper mapper, ProductService productService, OrderRepository orderRepository, StoreRepository storeRepository, PromotionRepository promotionRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.mapper = mapper;
        this.productRepoSitory = productRepoSitory;
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.promotionRepository = promotionRepository;
    }


//    public OrderDTO createOrder(OrderRequest request) throws Exception {
//        List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
//        float totalPrice = 0;
//        int totalQuantity = 0;
//
//        for (OrderItemReq item : request.getItemReqList()) {
//            Optional<Product> product = productRepoSitory.findById(item.getId());
//            if (!product.isPresent()) {
//                throw new Exception("Khong tin thay san pham co ma " + item.getId());
//            }else {
//                if (product.get().getQuantityInStore() > item.getQuantity()) {
//                    OrderItemDTO itemDTO = OrderItemDTO.builder()
//                            .productDTO(mapper.map(product.get(), ProductDTO.class))
//                            .quantityProductItem(item.getQuantity())
//                            .totalValueOrderItem(item.getQuantity() * product.get().getPrice())
//                            .build();
//                    orderItemsDTO.add(itemDTO);
//                    product.get().setQuantityInStore(product.get().getQuantityInStore() - item.getQuantity());
//                    productRepoSitory.save(product.get());
//                    totalPrice += itemDTO.getTotalValueOrderItem();
//                    totalQuantity += itemDTO.getQuantityProductItem();
//                }else {
//                    throw new Exception("So luong dat vuot qua so luong trong kho");
//                }
//
//            }
//        }
//        OrderDTO orderDTO = OrderDTO.builder()
//                .addr(request.getAddr())
//                .quantity(totalQuantity)
//                .phoneNumber(request.getPhoneNumber())
//                .orderCreationDate(new Date())
//                .totalValueOrder(totalPrice)
//                .statusId(request.getStatusId())
//                .orderItems(orderItemsDTO).build();
//
//
//        return orderDTO;
//    }

    public OrderDTO createOrder(CreateOrderRequest request) throws Exception {
        List<OrderItem> orderItems = new ArrayList<>();
        float totalPrice = 0;
        int totalQuantity = 0;
        Order order = Order.builder()
                .email(request.getEmail())
                .name(request.getName())
                .orderItems(new ArrayList<>())
                .addr(request.getAddr())
                .phoneNumber(request.getPhoneNumber())
                .orderCreationDate(new Date())
                .statusId(request.getStatusId()).build();

        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        for (OrderItemReq item : request.getItemReqList()) {


            Optional<Product> product = productRepoSitory.findById(item.getId());
            if (!product.isPresent()) {
                throw new Exception("Khong tin thay san pham co ma " + item.getId());
            } else {


//                int soldQuantity = (product.get().getSold()!= null) ? product.get().setSold(item.getQuantity()) : 0 ;

//                product.get().setSold(product.get().getSold() + item.getQuantity());
                if (product.get().getQuantityInStore() < item.getQuantity()) {
                    throw new Exception("So luong vuot qua trong kho ");
                } else {
                    orderRepository.save(order);
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderProduct(product.get());
                    orderItem.setOrder(order);

                    order.getOrderItems().add(orderItem);
                    orderItem.setQuantityProductItem(item.getQuantity());
                    orderItemRepository.save(orderItem);
                    float q1 = item.getQuantity() * product.get().getPrice();
                    Optional<Promotion> promotion = promotionRepository.findById(product
                            .get().getPromotion().getPromotionID());
                    if (!promotion.isPresent()) {
                        if (product.get().getQuantityInStore() > item.getQuantity()) {

                            OrderItem itemDTO = OrderItem.builder()
                                    .orderProduct(product.get())
                                    .quantityProductItem(item.getQuantity())
                                    .totalValueOrderItem(q1)
                                    .build();

                            orderItems.add(itemDTO);
//                        product.get().setQuantityInStore(product.get().getQuantityInStore() - item.getQuantity());
                            productRepoSitory.save(product.get());
                            totalPrice += itemDTO.getTotalValueOrderItem();
                            totalQuantity += itemDTO.getQuantityProductItem();
                        } else {
                            throw new Exception("So luong dat vuot qua so luong trong kho");
                        }

                    } else {
                        if (product.get().getQuantityInStore() > item.getQuantity()) {
                            if (q1 >= promotion.get().getMinimumPurchaseAmount()) {
                                OrderItem itemDTO = OrderItem.builder()
                                        .orderProduct(product.get())
                                        .quantityProductItem(item.getQuantity())
                                        .totalValueOrderItem(
                                                q1 - (q1 * (promotion.get().getPromotionPercentage() / 100))
                                        )
                                        .build();
                                orderItems.add(itemDTO);
//                            product.get().setQuantityInStore(product.get().getQuantityInStore() - item.getQuantity());
                                productRepoSitory.save(product.get());
                                totalPrice += itemDTO.getTotalValueOrderItem();
                                totalQuantity += itemDTO.getQuantityProductItem();
                            } else {
                                OrderItem itemDTO = OrderItem.builder()
                                        .orderProduct(product.get())
                                        .quantityProductItem(item.getQuantity())
                                        .totalValueOrderItem(q1)
                                        .build();
                                orderItems.add(itemDTO);
                                product.get().setQuantityInStore(product.get().getQuantityInStore() - item.getQuantity());
                                productRepoSitory.save(product.get());
                                totalPrice += itemDTO.getTotalValueOrderItem();
                                totalQuantity += itemDTO.getQuantityProductItem();
                            }
                        }
                    }

                }
            }
        }

        order.setQuantity(totalQuantity);
        order.setTotalValueOrder(totalPrice);
        orderRepository.save(order);


        OrderDTO orderDTO = OrderDTO.builder()
                .email(request.getEmail())
                .name(order.getName())
//                .infor(request.getInfor())
                .orderId(order.getOrderId())
                .orderItems(orderItemDTOS)
                .quantity(order.getQuantity())
                .addr(request.getAddr())
                .phoneNumber(order.getPhoneNumber())
                .totalValueOrder(order.getTotalValueOrder())
                .orderCreationDate(new Date())
                .statusId(request.getStatusId()).build();


        return orderDTO;
    }

    public List<OrderDTO> getOrderByPhoneNumber(String phoneNumber) {
        List<Order> orders = orderRepository.findAllByPhoneNumber(phoneNumber);
        return orders.stream().map(i -> mapper.map(i, OrderDTO.class)).collect(Collectors.toList());
    }

//    public OrderDTO updateOrder(UpdateOrderRequest request) {
//        Order order = orderRepository.findAllByOrderId(request.getOrderId());
//        List<OrderItem> orderItems = order.getOrderItems();
//        if(request.getStatusId() == 3){
//
//        for(OrderItem orderItem : orderItems){
//            Product product = orderItem.getOrderProduct();
//            product.setQuantityInStore(product.getQuantityInStore() - orderItem.getQuantityProductItem());
//            productRepoSitory.save(product);
//        }
//        }
//
////        order.setQuantity(request.getQuantity());
//        order.setAddr(request.getAddr());
//        order.setPhoneNumber(request.getPhoneNumber());
//        order.setStatusId(request.getStatusId());
//        orderRepository.save(order);
//
//        return mapper.map(order, OrderDTO.class);
//
//    }


    /*
    public OrderDTO updateOrder(UpdateOrderRequest request) {
        Order order = orderRepository.findAllByOrderId(request.getOrderId());
        List<OrderItem> orderItems = orderItemRepository.findAll();
        if (request.getStatusId() == 3) {
            for (OrderItem orderItem : orderItems) {
                Product product = orderItem.getOrderProduct();
                int orderedQuantity = orderItem.getQuantityProductItem();
                long currentQuantity = product.getQuantityInStore();
                if (currentQuantity >= orderedQuantity) {
                    product.setQuantityInStore(currentQuantity - orderedQuantity);
                    productRepoSitory.save(product);
                } else {
                    // Xử lý khi số lượng trong kho không đủ
                    // ...
                }
            }
        }

        order.setAddr(request.getAddr());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setStatusId(request.getStatusId());
        orderRepository.save(order);

        return mapper.map(order, OrderDTO.class);
    }
*/

    public OrderDTO updateOrder(UpdateOrderRequest request){
        Order order = orderRepository.findAllByOrderId(request.getOrderId());
        if(order.getStatusId() == 3){
            throw new RuntimeException("Không thể cập nhật đơn hàng");
        }else {

            if (request.getStatusId() == 3) {
                order.setStartDateWarranty(new Date());
                List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);
                for (OrderItem orderItem : orderItems) {
                    Product product = orderItem.getOrderProduct();
                    product.setSold(product.getSold() + orderItem.getQuantityProductItem());
                    product.setQuantityInStore(product.getQuantityInStore() - orderItem.getQuantityProductItem());

                    productRepoSitory.save(product);
                }
            }
        }
        order.setAddr(request.getAddr());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setQuantity(request.getQuantity());
        order.setStatusId(request.getStatusId());
        orderRepository.save(order);

        return mapper.map(order , OrderDTO.class);
    }
    public OrderDTO findOrderById(Long id) {
        Order order = orderRepository.findAllByOrderId(id);
        return mapper.map(order, OrderDTO.class);
    }


    public Page<OrderDTO> getAllOrder(PaginationRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        if (request.getKey() == 1) {
            List<Order> orders = orderRepository.findAllByOrderByOrderIdAsc();
            List<OrderDTO> orderDTOS = orders.stream().map(i -> mapper.map(i, OrderDTO.class)).collect(Collectors.toList());
            return new PageImpl<>(orderDTOS, pageable, orders.size());
        } else if (request.getKey() == 2) {
            List<Order> orders = orderRepository.findAllByOrderByOrderCreationDateDesc();
            List<OrderDTO> orderDTOS = orders.stream().map(i -> mapper.map(i, OrderDTO.class)).collect(Collectors.toList());
            return new PageImpl<>(orderDTOS, pageable, orders.size());
        } else {
            List<Order> orderDTOS = orderRepository.findAll();
            List<OrderDTO> listOrder = orderDTOS.stream().map(i -> mapper.map(i, OrderDTO.class)).collect(Collectors.toList());
            return new PageImpl<>(listOrder, pageable, listOrder.size());
        }
    }

    public boolean deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return true;
    }

    public List<Revenue> getRevenue() {
        Map<Integer, Float> revenueList = new HashMap<>();
        List<Revenue> revenueList1 = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            Float total = revenueList.getOrDefault(order.getOrderCreationDate().getMonth(), 0.0f);
            total += order.getTotalValueOrder();
            revenueList.put(order.getOrderCreationDate().getMonth(), total);
        }
        for (Map.Entry<Integer, Float> item : revenueList.entrySet()) {
            Revenue revenue = new Revenue();
            revenue.setMonth(item.getKey() + 1);
            revenue.setValue(item.getValue());
            revenueList1.add(revenue);

        }
        return revenueList1;
    }

    public List<Revenue> getRevenueByCondition(Date start, Date end) {
        Map<Integer, Float> revenueList = new HashMap<>();
        List<Revenue> revenueList1 = new ArrayList<>();
        for (Order order : orderRepository.findAllByOrderCreationDateBetween(start, end)) {
            Float total = revenueList.getOrDefault(order.getOrderCreationDate().getMonth(), 0.0f);
            total += order.getTotalValueOrder();
            revenueList.put(order.getOrderCreationDate().getMonth(), total);
        }
        for (Map.Entry<Integer, Float> item : revenueList.entrySet()) {
            Revenue revenue = new Revenue();
            revenue.setMonth(item.getKey() + 1);
            revenue.setValue(item.getValue());
            revenueList1.add(revenue);

        }
        return revenueList1;
    }


    public List<Revenue> revennueByQuantity(Date start, Date end) {
        Map<Integer, Float> revenueList = new HashMap<>();
        List<Revenue> revenueList1 = new ArrayList<>();
        for (Order order : orderRepository.findAllByOrderCreationDateBetween(start, end)) {
            Float total = revenueList.getOrDefault(order.getOrderCreationDate().getMonth(), 0.0f);
            total += order.getQuantity();
            revenueList.put(order.getOrderCreationDate().getMonth(), total);
        }
        for (Map.Entry<Integer, Float> item : revenueList.entrySet()) {
            Revenue revenue = new Revenue();
            revenue.setMonth(item.getKey() + 1);
            revenue.setValue(item.getValue());
            revenueList1.add(revenue);

        }
        return revenueList1;
    }

    public List<Revenue> getOrderRevenueByMonthAndYear(Date start, Date end) {
        List<Revenue> revenueList = orderRepository.getOrderRevenueByMonthAndYear(start, end);
        return revenueList;
    }

    public List<OrderDTO> getListOrderByEmail(String email) {
        List<Order> orders = orderRepository.findAllByEmail(email);

        return orders.stream().map(i -> mapper.map(i, OrderDTO.class)).collect(Collectors.toList());
    }


//


}
