package project.moneymanaer_api.constants;

import lombok.Getter;

@Getter
public enum CategoryType {
    INCOME("income"),
    EXPENSE("expense");

    private final String name;

    CategoryType(String name){
        this.name = name;
    }

    public static CategoryType fromString(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (text.equalsIgnoreCase("income")) return INCOME;
        else return EXPENSE;
    }

}
