package com.my.springbootkafkatopologytestsample;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.time.Duration;
import java.util.List;

@SpringBootTest
class SpringBootKafkaTopologyTestSampleApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(SpringBootKafkaTopologyTestSampleApplicationTests.class);

    private TopologyTestDriver topologyTestDriver;

    @Value("${input-topic.name}")
    private String inputTopicName;

    @Value("${output-topic.name}")
    private String outputTopicName;

    private TestInputTopic<Integer, String> inputTopic;
    private TestOutputTopic<Integer, Long> outputTopic;

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @BeforeEach
    public void setup() {
        this.topologyTestDriver = new TopologyTestDriver(streamsBuilderFactoryBean.getTopology(), streamsBuilderFactoryBean.getStreamsConfiguration());
        logger.info(streamsBuilderFactoryBean.getTopology().describe().toString());
        this.inputTopic = topologyTestDriver.createInputTopic(inputTopicName, Serdes.Integer().serializer(), Serdes.String().serializer());
        this.outputTopic = topologyTestDriver.createOutputTopic(outputTopicName, Serdes.Integer().deserializer(), Serdes.Long().deserializer());
    }

    @AfterEach
    public void after() {
        if(topologyTestDriver != null) {
            topologyTestDriver.close();
        }
    }

    @Test
    public void testTopologyLogic() {
        inputTopic.pipeInput(1, "test", 1L);
        inputTopic.pipeInput(1, "test", 10L);
        inputTopic.pipeInput(2, "test", 2L);


        Awaitility.waitAtMost(Duration.ofSeconds(1)).until(() -> outputTopic.getQueueSize() == 2L);
        Assertions.assertThat(outputTopic.readValuesToList()).isEqualTo(List.of(2L, 1L));
    }

}
