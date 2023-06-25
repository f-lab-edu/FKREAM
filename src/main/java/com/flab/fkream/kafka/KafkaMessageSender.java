package com.flab.fkream.kafka;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@RequiredArgsConstructor
@Log4j2
public class KafkaMessageSender {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Object data) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, data);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(
            record);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("메세지 전송 실패");
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("메세지 전송 성공");
            }
        });
    }
}
