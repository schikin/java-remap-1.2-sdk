package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class StoreTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Store store = new Store();
        store.setName("store_" + randomString(3) + "_" + new Date().getTime());
        store.setArchived(false);
        store.setDescription(randomString());
        store.setPathName(randomString());
        Address addressFull = randomAddress(api);
        store.setAddressFull(addressFull);

        api.entity().store().create(store);

        ListEntity<Store> updatedEntitiesList = api.entity().store().get(filterEq("name", store.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Store retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(store.getName(), retrievedEntity.getName());
        assertEquals(store.getArchived(), retrievedEntity.getArchived());
        assertEquals(store.getDescription(), retrievedEntity.getDescription());
        assertEquals(store.getPathName(), retrievedEntity.getPathName());
        assertAddressFull(addressFull, store.getAddressFull());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse metadata = api.entity().store().metadata();
        assertFalse(metadata.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Store originalStore = (Store) originalEntity;
        Store retrievedStore = (Store) retrievedEntity;

        assertEquals(originalStore.getName(), retrievedStore.getName());
        assertEquals(originalStore.getDescription(), retrievedStore.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Store originalStore = (Store) originalEntity;
        Store updatedStore = (Store) updatedEntity;

        assertNotEquals(originalStore.getName(), updatedStore.getName());
        assertEquals(changedField, updatedStore.getName());
        assertEquals(originalStore.getDescription(), updatedStore.getDescription());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().store();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Store.class;
    }
}
