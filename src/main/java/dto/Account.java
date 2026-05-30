package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с тестовыми данными Account.
 * Класс хранит значения, которые тест передаёт на страницу создания
 * и потом использует в проверках на detail-странице.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Builder.Default
    private String name = "test";
    @Builder.Default
    private String officePhone = "";
    @Builder.Default
    private String website = "";
    @Builder.Default
    private String fax = "";
    @Builder.Default
    private String email = "";
    @Builder.Default
    private String billingStreet = "";
    @Builder.Default
    private String billingCity = "";
    @Builder.Default
    private String billingState = "";
    @Builder.Default
    private String billingPostalCode = "";
    @Builder.Default
    private String billingCountry = "";
    @Builder.Default
    private String shippingStreet = "";
    @Builder.Default
    private String shippingCity = "";
    @Builder.Default
    private String shippingState = "";
    @Builder.Default
    private String shippingPostalCode = "";
    @Builder.Default
    private String shippingCountry = "";
    @Builder.Default
    private String description = "";
    @Builder.Default
    private String type = "";
    @Builder.Default
    private String industry = "";
    @Builder.Default
    private String annualRevenue = "";
    @Builder.Default
    private String employees = "";
}
