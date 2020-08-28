package com.mcb.creditfactory.dto;

import com.mcb.creditfactory.external.CollateralType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 18.08.2020.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AssessDto {
    private Long id;
    private UUID collateralId;
    private CollateralType collateralType;
    private LocalDateTime assessDate;
    private BigDecimal value;
    private boolean status;
}
