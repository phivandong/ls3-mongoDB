package com.pvdong.ls3mongodb.query;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

public class CustomAggregationOperation implements AggregationOperation {

    private final String jsonOperation;

    public CustomAggregationOperation(String jsonOperation) {
        this.jsonOperation = jsonOperation;
    }

    @Override
    public Document toDocument(AggregationOperationContext context) {
        return context.getMappedObject(org.bson.Document.parse(jsonOperation));
    }
}
