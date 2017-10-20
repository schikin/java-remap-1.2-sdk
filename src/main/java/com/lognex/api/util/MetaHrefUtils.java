package com.lognex.api.util;

import com.lognex.api.model.base.Entity;
import com.lognex.api.model.entity.CustomEntity;
import lombok.NoArgsConstructor;

import static com.lognex.api.util.Constants.ENTITY_PATH;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class MetaHrefUtils {

    public static ID getId(String href) {
        String[] split = href.split("/");
        return new ID(split[split.length-1]);
    }

    public static <T> String makeHref(Type type, T value) {
        StringBuilder sb = new StringBuilder(Constants.DEFAULT_HOST_URL);
        if (value instanceof Entity){
            sb.append("/").append(ENTITY_PATH);
        }
        sb.append("/").append(type.getApiName());
        if (value instanceof Entity){
            if (value instanceof CustomEntity) {
                sb.append("/").append(((CustomEntity)value).getCustomDictionaryId())
                        .append("/").append(((Entity) value).getId().getValue());
            } else {
                sb.append("/").append(((Entity) value).getId().getValue());
            }
        }
        return sb.toString();
    }
}
