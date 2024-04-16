package com.bsuir.service.impl;

import com.bsuir.dto.ChargeRequest;
import com.bsuir.entity.Transaction;
import com.bsuir.entity.User;
import com.bsuir.exception.UserNotFoundException;
import com.bsuir.repository.PropertyRepository;
import com.bsuir.repository.TransactionRepository;
import com.bsuir.repository.UserRepository;
import com.bsuir.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static com.bsuir.constant.RentalPropertiesConstants.DefaultValue.PRICE_FOR_PUBLISH;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    @SneakyThrows
    @Transactional
    public void charge(ChargeRequest chargeRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Transaction transaction = Transaction.builder()
                .user(user)
                .amount(PRICE_FOR_PUBLISH)
                .build();
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("source", chargeRequest.getStripeToken());
        Charge.create(chargeParams);
        transactionRepository.save(transaction);
        user.setAllowPropertyCount(user.getAllowPropertyCount() + 1);
    }

    @Override
    public BigDecimal getRevenue() {
        BigDecimal amount = transactionRepository.findAllAmount();
        if(amount == null) {
            amount = BigDecimal.ZERO;
        }
        return amount;
    }

    public Map<String, BigDecimal> getStaticsData() {
        Map<String, BigDecimal> data = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        for(int i=0; i<6; i++) {
            LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endOfMonth = now.withDayOfMonth(now.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59);
            data.put(
                    now.getMonth().getDisplayName(TextStyle.SHORT, new Locale("ru", "RU")),
                    transactionRepository.findAllByCreatedAtBetween(startOfMonth, endOfMonth)
                            .stream()
                            .map(Transaction::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            );
            now = now.minusMonths(1);
        }
        return data;
    }
}