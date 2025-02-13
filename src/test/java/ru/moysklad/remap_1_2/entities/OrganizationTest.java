package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class OrganizationTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Organization organization = new Organization();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());
        organization.setArchived(false);
        organization.setCompanyType(CompanyType.legal);
        organization.setInn(randomString());
        organization.setOgrn(randomString());
        Address actualAddressFull = randomAddress(api);
        Address legalAddressFull = randomAddress(api);
        organization.setActualAddressFull(actualAddressFull);
        organization.setLegalAddressFull(legalAddressFull);
        organization.setSyncId(UUID.randomUUID().toString());
        organization.setDescription(randomString());
        organization.setCode(randomString());
        organization.setTrackingContractNumber(randomString());
        organization.setTrackingContractDate(LocalDateTime.now());

        api.entity().organization().create(organization);

        ListEntity<Organization> updatedEntitiesList = api.entity().organization().get(filterEq("name", organization.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Organization retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(organization.getName(), retrievedEntity.getName());
        assertEquals(organization.getArchived(), retrievedEntity.getArchived());
        assertEquals(organization.getAccounts(), retrievedEntity.getAccounts());
        assertEquals(organization.getCompanyType(), retrievedEntity.getCompanyType());
        assertEquals(organization.getInn(), retrievedEntity.getInn());
        assertEquals(organization.getOgrn(), retrievedEntity.getOgrn());
        assertAddressFull(actualAddressFull, organization.getActualAddressFull());
        assertAddressFull(legalAddressFull, organization.getLegalAddressFull());
        assertEquals(organization.getSyncId(), retrievedEntity.getSyncId());
        assertEquals(organization.getDescription(), retrievedEntity.getDescription());
        assertEquals(organization.getCode(), retrievedEntity.getCode());
        assertEquals(organization.getTrackingContractNumber(), retrievedEntity.getTrackingContractNumber());
        assertEquals(organization.getTrackingContractDate(), retrievedEntity.getTrackingContractDate());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse metadata = api.entity().organization().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Test
    public void getAccountsTest() throws IOException, ApiClientException {
        Organization organization = new Organization();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AgentAccount> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AgentAccount account = new AgentAccount();
        account.setIsDefault(true);
        account.setAccountNumber(randomString());
        accounts.getRows().add(account);
        account.setIsDefault(false);
        accounts.getRows().add(account);
        organization.setAccounts(accounts);

        api.entity().organization().create(organization);

        ListEntity<AgentAccount> accountList = api.entity().organization().getAccounts(organization.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());
    }

    @Test
    public void postAccountsTest() throws IOException, ApiClientException {
        Organization organization = new Organization();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AgentAccount> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AgentAccount account = new AgentAccount();
        account.setIsDefault(true);
        account.setAccountNumber(randomString());
        accounts.getRows().add(account);
        account.setIsDefault(false);
        accounts.getRows().add(account);

        api.entity().organization().create(organization);

        api.entity().organization().createAccounts(organization.getId(), accounts.getRows());

        ListEntity<AgentAccount> accountList = api.entity().organization().getAccounts(organization.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Organization originalOrganization = (Organization) originalEntity;
        Organization retrievedOrganization = (Organization) retrievedEntity;

        assertEquals(originalOrganization.getName(), retrievedOrganization.getName());
        assertEquals(originalOrganization.getCompanyType(), retrievedOrganization.getCompanyType());
        assertEquals(originalOrganization.getAccounts(), retrievedOrganization.getAccounts());
        assertEquals(originalOrganization.getInn(), retrievedOrganization.getInn());
        assertEquals(originalOrganization.getOgrn(), retrievedOrganization.getOgrn());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Organization originalOrganization = (Organization) originalEntity;
        Organization updatedOrganization = (Organization) updatedEntity;

        assertNotEquals(originalOrganization.getName(), updatedOrganization.getName());
        assertEquals(changedField, updatedOrganization.getName());
        assertEquals(originalOrganization.getCompanyType(), updatedOrganization.getCompanyType());
        assertEquals(originalOrganization.getAccounts(), updatedOrganization.getAccounts());
        assertEquals(originalOrganization.getInn(), updatedOrganization.getInn());
        assertEquals(originalOrganization.getOgrn(), updatedOrganization.getOgrn());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().organization();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Organization.class;
    }
}
