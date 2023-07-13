package com.yongkonhahn.homefood.model;
import java.text.DecimalFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name ="items")
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @Column(length = 50, nullable = false)
    String name;

    @Column(length = 100, name="img_path")
    String imgPath;

    @Column
    double price;

    @Column
    int discountPer;

    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    public String getFormattedPrice() {
        return String.format("$%.2f", price);
    }
}
