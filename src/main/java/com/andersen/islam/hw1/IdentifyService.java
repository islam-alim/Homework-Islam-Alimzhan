package com.andersen.islam.hw1;

import java.util.Optional;

public class IdentifyService {

    private final CustomerRepositorySql customerRepositorySql;

    public IdentifyService(CustomerRepositorySql customerRepositorySql) {
        this.customerRepositorySql = customerRepositorySql;
    }

    public Optional<String> getName(int customerId) {
        return checkIsAnExternalCustomer(customerId)
                ? Optional.of("External customer")
                : customerRepositorySql.getNameById(customerId);
    }

    private boolean checkIsAnExternalCustomer(int customerId) {
        return customerId>=100;
    }
}
