package com.sber.practika.models;

public enum StatusTransaction {
    IN_PROGRESS(101), // В обработке
    ACCESS(102), // Успешно
    CANCEL(103), // Отменена

    AUTH_FAILED(201), // Не был подтверждён перевод
    NO_MONEY(202), // Недостаточно денег для перевода

    CONFIRMATION_WAIT(301), // Ожидает подтверждения
    CONFIRMATION_FAILED(302), // Подтверждение неудачно
    CONFIRMATION_ACCESS(303), // Подтверждения успешно

    CANNOT_BE_PERFORMED(401), // Просто не может быть выполнена
    CANNOT_BE_PERFORMED_DUE_CARD(402), // Просто не может быть выполнена из-за карты
    CANNOT_BE_PERFORMED_DUE_USER(403); // Просто не может быть выполнена из-за пользователя

    private final int code;
    StatusTransaction(int code) {
        this.code = code;
    }
    public int getCode() { return code; }
}
