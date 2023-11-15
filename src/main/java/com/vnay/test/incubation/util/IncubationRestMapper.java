package com.vnay.test.incubation.util;

import java.util.Map;

import org.kie.kogito.incubation.common.DataContext;
import org.kie.kogito.incubation.common.ExtendedDataContext;
import org.kie.kogito.incubation.common.MapDataContext;
import org.kie.kogito.incubation.common.MapLikeDataContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class IncubationRestMapper {
	private static final ObjectMapper mapper = new ObjectMapper();
	static{
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.setDefaultPropertyInclusion(Include.NON_DEFAULT);
		mapper.setDefaultPropertyInclusion(Include.NON_ABSENT);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T convertValue(Object self, Class<T> type) {
        if (type.isInstance(self)) {
            return type.cast(self);
        }

        if (MapLikeDataContext.class == type || MapDataContext.class == type) {
            return (T) MapDataContext.of(mapper.convertValue(self, Map.class));
        }

        if (ExtendedDataContext.class == type) {
            if (self instanceof DataContext) {
                return (T) ExtendedDataContext.ofData((DataContext) self);
            } else {
                return (T) ExtendedDataContext.ofData(convertValue(self, MapDataContext.class));
            }
        }

        return mapper.convertValue(self, type);
    }

}
