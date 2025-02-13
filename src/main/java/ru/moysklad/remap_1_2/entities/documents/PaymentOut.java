package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceOutDocumentMarker;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentOut extends DocumentEntity implements FinanceOutDocumentMarker, IEntityWithAttributes {
    private Agent agent;
    private LocalDateTime created;
    private ExpenseItem expenseItem;
    private String externalCode;
    private Organization organization;
    private Project project;
    private Rate rate;
    private State state;
    private Long vatSum;
    private Contract contract;
    private String paymentPurpose;
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private AgentAccount organizationAccount;
    private AgentAccount agentAccount;
    private List<Attribute> attributes;
    private FactureIn factureIn;
    private List<MetaEntity> operations;

    public PaymentOut(String id) {
        super(id);
    }
}
