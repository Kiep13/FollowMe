package com.cyberapple.followme.records;

public record Invoice(String receiverId, Integer amountOfMoney, String currency, String description) {
}
