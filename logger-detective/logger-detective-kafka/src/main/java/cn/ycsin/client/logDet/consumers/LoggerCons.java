package cn.ycsin.client.logDet.consumers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggerCons {
    private static final String KEY_LOG = "log";
    static JsonParser jsonParser = new JsonParser();

    @KafkaListener(topics = "biz-log")
    public void handleBizLog(String record) throws Exception {
        JsonObject rootJsonObj = (JsonObject) jsonParser.parse(record);
        String message = rootJsonObj.getAsJsonPrimitive("message").getAsString();
        log.info("biz-log: {}", message);
    }

    @KafkaListener(topics = "access-log")
    public void handleAccessLog(String record) throws Exception {
        JsonObject rootJsonObj = (JsonObject) jsonParser.parse(record);
//        JsonObject logJsonObj = rootJsonObj.getAsJsonObject(KEY_LOG);
        String message = rootJsonObj.getAsJsonPrimitive("message").getAsString();
        log.info("access-log: {}", message);
    }

    static String str = "{\"@timestamp\":\"2019-10-06T08:00:27.091Z\",\"@metadata\":{\"beat\":\"filebeat\",\"type\":\"_doc\",\"version\":\"7.4.0\",\"topic\":\"log\"},\"log\":{\"offset\":426,\"file\":{\"path\":\"/logs/access_log.2019-10-06.log\"}},\"message\":\"[access] [06/Oct/2019:16:00:22 +0800] [http-nio-80-exec-5] - 192.168.1.4 GET /log HTTP/1.1 500 (7 ms)\",\"input\":{\"type\":\"log\"},\"agent\":{\"version\":\"7.4.0\",\"type\":\"filebeat\",\"ephemeral_id\":\"ca08f2e6-912a-41ca-9e65-8a4df1ecc608\",\"hostname\":\"localhost.localdomain\",\"id\":\"7489a7ab-8bec-412e-8056-9d35d27d3c5d\"},\"ecs\":{\"version\":\"1.1.0\"},\"host\":{\"architecture\":\"x86_64\",\"os\":{\"name\":\"CentOS Linux\",\"kernel\":\"3.10.0-693.el7.x86_64\",\"codename\":\"Core\",\"platform\":\"centos\",\"version\":\"7 (Core)\",\"family\":\"redhat\"},\"id\":\"e8f70b788b464d798b47f2215253146c\",\"name\":\"localhost.localdomain\",\"containerized\":false,\"hostname\":\"localhost.localdomain\"}}";

    public static void main(String[] args) {

    }
}
