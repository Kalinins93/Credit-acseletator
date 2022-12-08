package ru.neoflex.models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum ApplicationStatus {
    PREAPPROVAL,
    APROVED,
    CC_DENIED,
    CC_APPROVED,
    PREPARE_DOCUMENTS,
    DOCUMENT_CREATED,
    CLIENT_DENIED,
    DOCUMENT_SIGNED,
    CREDIT_ISSUED;
}
