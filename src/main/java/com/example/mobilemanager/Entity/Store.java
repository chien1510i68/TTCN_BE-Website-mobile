package com.example.mobilemanager.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {
    @Id
    private int store_id = 1;
    @OneToMany(mappedBy = "store" ,cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<>();

    @Column(name = "total_quatity")
    private long totalQuantity ;

}
