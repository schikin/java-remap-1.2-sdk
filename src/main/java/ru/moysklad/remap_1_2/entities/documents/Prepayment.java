package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.positions.PrepaymentDocumentPosition;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Prepayment extends DocumentEntity implements IEntityWithAttributes {
    private String syncId;
    private LocalDateTime deleted;
    private String description;
    private String externalCode;
    private Rate rate;
    private Agent agent;
    private Organization organization;
    private State state;
    private List<Attribute> attributes;
    private LocalDateTime created;
    private Boolean vatEnabled;
    private Boolean vatIncluded;
    private Long vatSum;
    private RetailStore retailStore;
    private CustomerOrder customerOrder;
    private RetailShift retailShift;
    private List<PrepaymentReturn> returns;
    private Long cashSum;
    private Long noCashSum;
    private TaxSystem taxSystem;
    private ListEntity<PrepaymentDocumentPosition> positions;
    private Long qrSum;

    public Prepayment(String id) {
        super(id);
    }
}
