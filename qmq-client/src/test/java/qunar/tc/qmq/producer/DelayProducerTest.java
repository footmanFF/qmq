package qunar.tc.qmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qunar.tc.qmq.Message;
import qunar.tc.qmq.MessageSendStateListener;
import qunar.tc.qmq.common.ClientInfo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DelayProducerTest {

    private static final Logger LOG = LoggerFactory.getLogger(DelayProducerTest.class);

    public static void main(String[] args) throws Exception {
        final MessageProducerProvider provider = new MessageProducerProvider();
        provider.setAppCode("producer_test_delay");
        provider.setClientInfo(ClientInfo.of("consumer_test","ldc1"));
        provider.setMetaServer("http://172.27.64.122:8080/meta/address");
        provider.setSyncSend(true);
        provider.init();

        System.out.println("开始发送");
        
        int msgNum = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(msgNum);
        for (int i = 0; i < msgNum; i++) {
            final Message message = provider.generateMessage("new.qmq.test.delay");
            message.setDelayTime(3600L + 60L, TimeUnit.SECONDS);

            provider.sendMessage(message, new MessageSendStateListener() {
                @Override
                public void onSuccess(Message message) {
                    countDownLatch.countDown();
                    LOG.info("delay message send success id:{}", message.getMessageId());
                }

                @Override
                public void onFailed(Message message) {
                    countDownLatch.countDown();
                    LOG.info("delay message send failed id:{}", message.getMessageId());
                }
            });
        }

        countDownLatch.await();
        provider.destroy();
    }
}
