package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Pricelist;
import com.lognex.api.entities.documents.Pricelist.PricelistRow;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DocumentPricelistClient
        extends com.lognex.api.clients.ApiClient
        implements
        GetListEndpoint<Pricelist>,
        PostEndpoint<Pricelist>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Pricelist>,
        PutByIdEndpoint<Pricelist>,
        ExportEndpoint {

    public DocumentPricelistClient(ApiClient api) {
        super(api, "/entity/pricelist/");
    }

    @ApiEndpoint
    public List<PricelistRow> postPositions(String documentId, List<PricelistRow> updatedEntities) throws IOException, LognexApiException {
        List<PricelistRow> responseEntity = HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/").
                body(updatedEntities).
                postList(PricelistRow.class);

        for (int i = 0; i < responseEntity.size(); i++) {
            updatedEntities.set(i, responseEntity.get(i));
        }
        return updatedEntities;
    }

    @ApiEndpoint
    public List<PricelistRow> postPositions(Pricelist document, List<PricelistRow> updatedEntities) throws IOException, LognexApiException {
        return postPositions(document.getId(), updatedEntities);
    }

    @ApiEndpoint
    public PricelistRow postPosition(String documentId, PricelistRow updatedEntity) throws IOException, LognexApiException {
        List<PricelistRow> positionList = new ArrayList<>(Collections.singletonList(updatedEntity));
        List<PricelistRow> newPosition = postPositions(documentId, positionList);

        updatedEntity.set(newPosition.get(0));
        return updatedEntity;
    }

    @ApiEndpoint
    public PricelistRow postPosition(Pricelist document, PricelistRow updatedEntity) throws IOException, LognexApiException {
        return postPosition(document.getId(), updatedEntity);
    }

    @ApiEndpoint
    public ListEntity<PricelistRow> getPositions(String documentId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/positions").
                apiParams(params).
                list(PricelistRow.class);
    }

    @ApiEndpoint
    public ListEntity<PricelistRow> getPositions(Pricelist document, ApiParam... params) throws IOException, LognexApiException {
        return getPositions(document.getId(), params);
    }

    @ApiEndpoint
    public PricelistRow getPosition(String documentId, String positionId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                apiParams(params).
                get(PricelistRow.class);
    }

    @ApiEndpoint
    public PricelistRow getPosition(Pricelist document, String positionId, ApiParam... params) throws IOException, LognexApiException {
        return getPosition(document.getId(), positionId, params);
    }

    @ApiEndpoint
    public void putPosition(String documentId, String positionId, PricelistRow updatedEntity) throws IOException, LognexApiException {
        PricelistRow responseEntity = HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                body(updatedEntity).
                put(PricelistRow.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void putPosition(Pricelist document, String positionId, PricelistRow updatedEntity) throws IOException, LognexApiException {
        putPosition(document.getId(), positionId, updatedEntity);
    }

    @ApiEndpoint
    public void putPosition(Pricelist document, PricelistRow position, PricelistRow updatedEntity) throws IOException, LognexApiException {
        putPosition(document, position.getId(), updatedEntity);
    }

    @ApiEndpoint
    public void putPosition(Pricelist document, PricelistRow position) throws IOException, LognexApiException {
        putPosition(document, position, position);
    }

    @ApiEndpoint
    public void delete(String documentId, String positionId) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                delete();
    }

    @ApiEndpoint
    public void delete(Pricelist document, String positionId) throws IOException, LognexApiException {
        delete(document.getId(), positionId);
    }

    @ApiEndpoint
    public void delete(Pricelist document, PricelistRow position) throws IOException, LognexApiException {
        delete(document, position.getId());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Pricelist.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
