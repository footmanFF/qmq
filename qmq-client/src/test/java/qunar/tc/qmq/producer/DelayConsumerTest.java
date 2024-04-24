package qunar.tc.qmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qunar.tc.qmq.ListenerHolder;
import qunar.tc.qmq.Message;
import qunar.tc.qmq.MessageListener;
import qunar.tc.qmq.common.ClientInfo;
import qunar.tc.qmq.consumer.MessageConsumerProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xufeng.deng dennisdxf@gmail.com
 * @since 2019/6/3
 */
public class DelayConsumerTest {
    private static final Logger LOG = LoggerFactory.getLogger(DelayConsumerTest.class);

    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws Exception {
        final MessageConsumerProvider provider = new MessageConsumerProvider();
        provider.setMetaServer("http://172.27.64.122:8080/meta/address");
        provider.setAppCode("consumer_test");
        provider.setClientInfo(ClientInfo.of("consumer_test","ldc1"));
        provider.init();
        provider.online();

        // 第二个参数类似消费组的概念，consumerGroup不同，就是一个topic不同的消费方
        final ListenerHolder listener = provider.addListener("new.qmq.test.delay", "group-delay", new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                LOG.info("msgId:{}", msg.getMessageId());
            }
        }, executor);

        System.in.read();
        listener.stopListen();
        provider.destroy();
    }
}
