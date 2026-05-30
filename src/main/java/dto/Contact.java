package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с тестовыми данными Contact.
 * Класс хранит значения контакта, чтобы не держать огромную пачку
 * отдельных переменных в тесте.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {

    @Builder.Default
    private String salutation = "";
    @Builder.Default
    private String firstName = "";
    @Builder.Default
    private String lastName = "test";
    @Builder.Default
    private String officePhone = "";
    @Builder.Default
    private String mobile = "";
    @Builder.Default
    private String jobTitle = "";
    @Builder.Default
    private String department = "";
    @Builder.Default
    private String fax = "";
    @Builder.Default
    private String email = "";
    @Builder.Default
    private String primaryStreet = "";
    @Builder.Default
    private String primaryCity = "";
    @Builder.Default
    private String primaryState = "";
    @Builder.Default
    private String primaryPostalCode = "";
    @Builder.Default
    private String primaryCountry = "";
    @Builder.Default
    private String otherStreet = "";
    @Builder.Default
    private String otherCity = "";
    @Builder.Default
    private String otherState = "";
    @Builder.Default
    private String otherPostalCode = "";
    @Builder.Default
    private String otherCountry = "";
    @Builder.Default
    private String description = "";
    @Builder.Default
    private String leadSource = "";

    public String getFullName() {
        return salutation + " " + firstName + " " + lastName;
    }
}
