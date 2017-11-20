package st.malike.elasticsearch.kafka.watch.service;

import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.search.SearchHits;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;

/**
 * @author malike_st
 */
public class EventIndexOpsTriggerService {

    private KafkaWatchService kafkaWatchService = new KafkaWatchService();


    public boolean evaluateRuleForEvent(String indexName, Engine.Index index,
                                        Engine.IndexResult indexResult, KafkaWatch kafkaWatch) {

        return evaluateRule(indexName, kafkaWatch);
    }

    public boolean evaluateRuleForEvent(String indexName, Engine.Delete delete,
                                        Engine.DeleteResult deleteResult, KafkaWatch kafkaWatch) {

        return evaluateRule(indexName, kafkaWatch);
    }


    private boolean evaluateRule(String indexName, KafkaWatch kafkaWatch) {
        if (kafkaWatch == null) {
            return false;
        }
        if (!kafkaWatch.getIndexName().equalsIgnoreCase(indexName)) {
            return false;
        }
        if (kafkaWatch.getExpectedHit() == 0) {
            return true;
        }
        SearchHits response = kafkaWatchService.executeWatchQuery(kafkaWatch.getIndexOpsQuery());
        int compared = Long.valueOf(response.getTotalHits()).compareTo(
                kafkaWatch.getExpectedHit()
        );
        switch (kafkaWatch.getQuerySymbol()) {
            case EQUAL_TO:
                return compared == 0;
            case GREATER_THAN_OR_EQUAL_TO:
                return compared >= 0;
            case LESS_THAN_OR_EQUAL_TO:
                return compared <= 0;
        }
        return false;
    }


}
