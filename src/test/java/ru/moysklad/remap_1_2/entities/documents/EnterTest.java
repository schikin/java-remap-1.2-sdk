package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.positions.EnterDocumentPosition;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.params.ExpandParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class EnterTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Enter enter = new Enter();
        enter.setName("enter_" + randomString(3) + "_" + new Date().getTime());
        enter.setDescription(randomString());
        enter.setMoment(LocalDateTime.now());
        enter.setOrganization(simpleEntityManager.getOwnOrganization());
        enter.setStore(simpleEntityManager.getMainStore());

        EnterDocumentPosition position = new EnterDocumentPosition();
        position.setAssortment(simpleEntityManager.createSimpleProduct());
        position.setQuantity(1.);
        position.setCountry(simpleEntityManager.createSimpleCountry());
        position.setGtd(new DocumentEntity.Gtd(randomString()));
        position.setReason(randomString());
        enter.setPositions(new ListEntity<>(position));

        api.entity().enter().create(enter);

        ListEntity<Enter> updatedEntitiesList = api.entity().enter().get(filterEq("name", enter.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Enter retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(enter.getName(), retrievedEntity.getName());
        assertEquals(enter.getDescription(), retrievedEntity.getDescription());
        assertEquals(enter.getMoment(), retrievedEntity.getMoment());
        assertEquals(enter.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(enter.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());

        ListEntity<EnterDocumentPosition> retrievedPositions = api.entity().enter().getPositions(retrievedEntity.getId(), ExpandParam.expand("country"));
        assertEquals(1, retrievedPositions.getRows().size());
        EnterDocumentPosition retrievedPosition = retrievedPositions.getRows().get(0);
        assertEquals(position.getReason(), retrievedPosition.getReason());
        assertEquals(position.getGtd().getName(), retrievedPosition.getGtd().getName());
        assertEquals(position.getCountry().getId(), retrievedPosition.getCountry().getId());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse response = api.entity().enter().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().enter().metadataAttributes();
        assertNotNull(attributes);
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Enter originalEnter = (Enter) originalEntity;
        Enter retrievedEnter = (Enter) retrievedEntity;

        assertEquals(originalEnter.getName(), retrievedEnter.getName());
        assertEquals(originalEnter.getDescription(), retrievedEnter.getDescription());
        assertEquals(originalEnter.getOrganization().getMeta().getHref(), retrievedEnter.getOrganization().getMeta().getHref());
        assertEquals(originalEnter.getStore().getMeta().getHref(), retrievedEnter.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Enter originalEnter = (Enter) originalEntity;
        Enter updatedEnter = (Enter) updatedEntity;

        assertNotEquals(originalEnter.getName(), updatedEnter.getName());
        assertEquals(changedField, updatedEnter.getName());
        assertEquals(originalEnter.getDescription(), updatedEnter.getDescription());
        assertEquals(originalEnter.getOrganization().getMeta().getHref(), updatedEnter.getOrganization().getMeta().getHref());
        assertEquals(originalEnter.getStore().getMeta().getHref(), updatedEnter.getStore().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().enter();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Enter.class;
    }
}
