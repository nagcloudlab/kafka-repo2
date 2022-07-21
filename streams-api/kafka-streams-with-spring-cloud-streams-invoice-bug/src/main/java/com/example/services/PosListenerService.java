package com.example.services;

import com.example.bindings.PosListenerBinding;
import com.example.kafka.HadoopRecord;
import com.example.kafka.Notification;
import com.example.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@EnableBinding(PosListenerBinding.class)
public class PosListenerService {

    @Autowired
    RecordBuilder recordBuilder;

    // Exactly Once feature for every invoice event
    @StreamListener("pos-input-channel")
    public void process(@Input() KStream<String, PosInvoice> input) {
        KStream<String, Notification> notificationKStream = input
                .filter((k, v) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                .mapValues(v -> recordBuilder.getNotification(v));

        KStream<String, HadoopRecord> hadoopRecordKStream = input
                .mapValues( v -> recordBuilder.getMaskedInvoice(v))
                .flatMapValues( v -> recordBuilder.getHadoopRecords(v));
        hadoopRecordKStream.foreach((k, v) -> log.info(String.format("Hadoop Record:- Key: %s, Value: %s", k, v)));
        notificationKStream.foreach((k, v) -> log.info(String.format("Notification:- Key: %s, Value: %s", k, v)));
        notificationKStream.to("loyalty");
        hadoopRecordKStream.to("hadoop-sink");
    }

}
