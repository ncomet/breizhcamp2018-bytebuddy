package domain;

import frameworks.security.annotation.Lock;

/**
 * LECTRA
 * Bank class
 * @author n.comet
 */
class Bank {

    private static final Double bankAccountTotal = 9215.32;

    @Lock(key = "4b26e049-14f8-4f40-b742-aab87f0f560f")
    Double bankAccountTotal() {
        return bankAccountTotal;
    }

}
