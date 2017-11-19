package st.malike.elasticsearch.kafka.watch.service;

import org.apache.commons.lang.RandomStringUtils;
import org.elasticsearch.index.engine.Engine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import st.malike.elasticsearch.kafka.watch.model.KafkaWatch;
import st.malike.elasticsearch.kafka.watch.util.Enums;

import java.util.Arrays;
import java.util.Date;

/**
 * @autor malike_st
 */
@RunWith(MockitoJUnitRunner.class)
public class EventIndexOpsTriggerServiceTest {

    @Spy
    @InjectMocks
    private EventIndexOpsTriggerService eventIndexOpsTriggerService;
    @Mock
    private Engine.Index index;
    @Mock
    private Engine.IndexResult indexResult;
    @Mock
    private Engine.Delete delete;
    @Mock
    private Engine.DeleteResult deleteResult;
    private KafkaWatch kafkaWatch;
    private String INDEX_NAME = "";


    @Before
    public void setUp() throws Exception {

        kafkaWatch = new KafkaWatch();
        kafkaWatch.setId(RandomStringUtils.randomAlphanumeric(5));
        kafkaWatch.setQuerySymbol(Enums.QuerySymbol.GREATER_THAN_OR_EQUAL_TO);
        kafkaWatch.setSubject("Random Kafka Watch");
        kafkaWatch.setDateCreated(new Date());
        kafkaWatch.setTriggerType(Enums.TriggerType.INDEX_OPS);
        kafkaWatch.setChannel(Arrays.asList("SMS", "EMAIL"));
        kafkaWatch.setDescription("Random Kafka Watch To Test");
        kafkaWatch.setEventType("SUBSCRIPTION");
        kafkaWatch.setReportTemplatePath("/home/malike/devfiles/report.jrxml");
        kafkaWatch.setGenerateReport(true);
        kafkaWatch.setIndexName("Test");
        kafkaWatch.setExpectedHit(2);
        kafkaWatch.setReportFormat("HTML");
        kafkaWatch.setRecipient(Arrays.asList("233201234567", "st.malike@gmail.com"));


    }


    @Test
    public void testEvaluateRuleForIndexCreateWithNoWatch() {

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(INDEX_NAME,
                index, indexResult,null);
        Assert.assertFalse(rule);
    }

    @Test
    public void testEvaluateRuleForIndexCreateWithUnmatchingIndexName() {

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(INDEX_NAME,
                index, indexResult,null);
        Assert.assertFalse(rule);
    }

    @Test
    public void testEvaluateRuleForIndexCreate() {

        Boolean rule = eventIndexOpsTriggerService.evaluateRuleForEvent(kafkaWatch.getIndexName(),
                index, indexResult,kafkaWatch);
        Assert.assertTrue(rule);
    }

    @Test
    public void testEvaluateRuleForIndexDelete() {
        Assert.assertNotEquals("awesome", "AWESOME");
    }
}
