package com.mcb.creditfactory.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 17.08.2020.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "ASSESS")
public class Assess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "collateral_type")
    private CollateralType type;

    @Column(name = "collateral_id")
    private UUID collateralId;

    @Column(name = "assess_date")
    private LocalDateTime assessDate;

    @Column(name = "assessed_value")
    private BigDecimal value;

    @Column(name = "approve_status")
    private Boolean status;
}
