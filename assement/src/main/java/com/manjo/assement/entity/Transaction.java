package com.manjo.assement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "transactions",
        indexes = {
                @Index(
                        name = "idx_reference_number",
                        columnList = "reference_number",
                        unique = true
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "trx_id", nullable = false, unique = true)
    private String trxId;

    @Column(name = "partner_reference_number", nullable = false)
    private String partnerReferenceNumber;

    @Column(name = "reference_number", nullable = false, unique = true)
    private String referenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "paid_date")
    private LocalDateTime paidDate;
}