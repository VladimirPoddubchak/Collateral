package com.mcb.creditfactory.model;


import lombok.*;

import javax.persistence.*;
import java.time.Year;
import java.util.List;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 17.08.2020.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "AIRPLANE")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "year_of_issue")
    private Short year;
    @Column(name = "fuel_capacity")
    private int fuelCapacity;
    @Column(name = "seats")
    private int seats;

}
