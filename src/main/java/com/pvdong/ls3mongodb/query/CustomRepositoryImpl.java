package com.pvdong.ls3mongodb.query;

import com.pvdong.ls3mongodb.entity.Horse;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Horse> getListHorse(String trainerId, Integer year) {
        Aggregation agg = Aggregation.newAggregation(
                new AggregationOperation() {
                    @Override
                    public Document toDocument(AggregationOperationContext context) {
                        return new Document("$lookup",
                                new Document("from", "horse_account")
                                        .append("localField", "_id")
                                        .append("foreignField", "horse_id")
                                        .append("as", "hc"));
                    }
                },
                Aggregation.unwind("$hc", false),
                new AggregationOperation() {
                    @Override
                    public Document toDocument(AggregationOperationContext context) {
                        return new Document("$lookup",
                                new Document("from", "account")
                                        .append("localField", "hc.account_id")
                                        .append("foreignField", "_id")
                                        .append("as", "acc"));
                    }
                },
                Aggregation.unwind("$acc", false),
                new AggregationOperation() {
                    @Override
                    public Document toDocument(AggregationOperationContext context) {
                        return new Document("$lookup",
                                new Document("from", "trainer")
                                        .append("localField", "acc._id")
                                        .append("foreignField", "account_id")
                                        .append("as", "result"));
                    }
                },
                Aggregation.unwind("$result", false),
                new AggregationOperation() {
                    @Override
                    public Document toDocument(AggregationOperationContext context) {
                        return new Document("$addFields",
                                new Document("year",
                                        new Document("$dateToString",
                                                new Document("format", "%Y")
                                                        .append("date", "$foaled"))));
                    }
                },
//                Aggregation.unwind("$result", false),
//                new AggregationOperation() {
//                    @Override
//                    public Document toDocument(AggregationOperationContext context) {
//                        return new Document("$project",
//                                new Document("year", "{ $year : '$foaled' }")
//                                        .append("name", 1));
//                    }
//                },
//                new AggregationOperation() {
//                    @Override
//                    public Document toDocument(AggregationOperationContext context) {
//                        return new Document("$match",
//                                new Document("result._id", trainerId)
//                                        .append("year", year));
//                    }
//                },
//                Aggregation.project("name", "foaled"),
//                        .andExpression("{$dateToString:{ format:'%Y', date: '$foaled'}}").as("year"),

                Aggregation.match(Criteria.where("year").is(year.toString())),
                Aggregation.match(Criteria.where("result._id").is(trainerId))

                // .andOperator(Criteria.where("year").is(year.toString())))
//                Aggregation.facet(Aggregation.project("result._id").and("foaled").extractYear().as("year"),
//                        Aggregation.bucketAuto("year", year).andOutput("result._id").push().as("id"))
//                        .as("byYear")

        );

        AggregationResults<Horse> results = mongoTemplate.aggregate(agg, "horse", Horse.class);
        return results.getMappedResults();
    }

    //db.horse.aggregate([{$project:{year: {$year: "$foaled"}}}, {$match: {year : 2010}}])
    //db.horse_account.aggregate([{$match: {account_id : 1}}, {$project: {account_id: 1, horse_id: 1}}])
    //db.account.aggregate([{$match: {_id: 1}}, {$project: {_id: 1}}])
    //db.trainer.aggregate([{$match: {account_id : 1}}, {$project: {account_id: 1}}])


//                Aggregation.lookup(mongoTemplate.getCollectionName(HorseAccount.class),
//                        "_id", "horse_id", "hc"),
//                Aggregation.unwind("$hc.horse_id", false),
//                Aggregation.lookup(mongoTemplate.getCollectionName(Account.class),
//                        "hc.account_id", "_id", "acc"),
//                Aggregation.unwind("$acc._id", false),
//                Aggregation.lookup(mongoTemplate.getCollectionName(Trainer.class),
//                        "acc._id", "account_id", "result"),
//                Aggregation.unwind("$result.account_id", false),
//                Aggregation.project("foaled", "name", "trainer_id: $result._id"),
//                Aggregation.match(Criteria.where("trainer_id").is(trainerId)),
//                Aggregation.match(Criteria.where("year").is(year.toString()))


//
//    public List<Horse> getListHorse(Integer trainerId, Integer year) {
//        String query = "{\n" +
//                        "  $lookup:\n" +
//                        "  {\n" +
//                        "    from: \"horse_account\",\n" +
//                        "    localField: \"_id\",\n" +
//                        "    foreignField: \"horse_id\",\n" +
//                        "    as: \"hc\"\n" +
//                        "  }\n" +
//                        "},\n" +
//                        "{\n" +
//                        "  $unwind:\n" +
//                        "  {\n" +
//                        "    path: \"$hc\",\n" +
//                        "    preserveNullAndEmptyArrays: false\n" +
//                        "  }\n" +
//                        "},\n" +
//                        "{\n" +
//                        "  $lookup:\n" +
//                        "  {\n" +
//                        "    from: \"account\",\n" +
//                        "    localField: \"hc.account_id\",\n" +
//                        "    foreignField: \"_id\",\n" +
//                        "    as: \"acc\"\n" +
//                        "  }\n" +
//                        "},\n" +
//                        "{\n" +
//                        "  $unwind:\n" +
//                        "  {\n" +
//                        "    path: \"$acc\",\n" +
//                        "    preserveNullAndEmptyArrays: false\n" +
//                        "  }\n" +
//                        "},\n" +
//                        "{\n" +
//                        "  $lookup:\n" +
//                        "  {\n" +
//                        "    from: \"trainer\",\n" +
//                        "    localField: \"acc._id\",\n" +
//                        "    foreignField: \"account_id\",\n" +
//                        "    as: \"result\"\n" +
//                        "  }\n" +
//                        "},\n" +
//                        "{\n" +
//                        "  $unwind:\n" +
//                        "  {\n" +
//                        "    path: \"$result\",\n" +
//                        "    preserveNullAndEmptyArrays: false\n" +
//                        "  }\n" +
//                        "},\n" +
//                        "{\n" +
//                        "  $project:\n" +
//                        "  {\n" +
//                        "    year: { $year : \"$foaled\" },\n" +
//                        "    name: 1,\n" +
//                        "    trainer_id: \"$result._id\"\n" +
//                        "  }\n" +
//                        "},\n" +
//                        "{\n" +
//                        "  $match:\n" +
//                        "  {\n" +
//                        "    trainer_id: " + trainerId.toString() + ",\n" +
//                        "    year : " + year.toString() + "\n" +
//                        "  }\n" +
//                        "}";
////        String query2 = "{ $lookup: { from: 'horse_account', localField: '_id', foreignField: 'horse_id', as: 'hc' } }, { $unwind: { path: '$hc', preserveNullAndEmptyArrays: false } }, { $lookup: { from: 'account', localField: 'hc.account_id', foreignField: '_id', as: 'acc' } }, { $unwind: { path: '$acc', preserveNullAndEmptyArrays: false } }, { $lookup: { from: 'trainer', localField: 'acc._id', foreignField: 'account_id', as: 'result' } }, { $unwind: { path: '$result', preserveNullAndEmptyArrays: false } }, { $project: { year: { $year : '$foaled' }, name: 1, trainer_id: '$result._id' } }, { $match: { year : 2010 } }";
////        TypedAggregation<Horse> aggregation = Aggregation.newAggregation(
////                Horse.class,
////                new CustomAggregationOperation(query)
////        );
//
}
