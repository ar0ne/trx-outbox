package org.example.consumer.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name="Order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    private LocalDateTime created;

    private OrderStatus status;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "Order_Product",
        joinColumns = { @JoinColumn(name = "order_id") },
        inverseJoinColumns = { @JoinColumn(name = "product_id") }

    )
    private Set<Product> products;

}
