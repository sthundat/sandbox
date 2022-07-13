package com.redhat.service.smartevents.integration.tests.resources;

import java.util.List;

import org.junit.jupiter.api.extension.RegisterExtension;

import com.redhat.service.smartevents.integration.tests.common.Utils;

import io.cucumber.java.BeforeAll;

import software.tnb.common.service.ServiceFactory;
import software.tnb.slack.service.Slack;
import software.tnb.slack.validation.MessageRequestConfig;

public class SlackResource {

    private final static String SLACK_CHANNEL_NAME = Utils.getSystemProperty("slack.channel.name");

    @RegisterExtension
    public static Slack slack = ServiceFactory.create(Slack.class);

    @BeforeAll()
    public static void loadSlackProperties() throws Exception {
        slack.beforeAll(null);
    }

    public static List<String> getListOfSlackMessages() {
        return slack.validation().getMessages(new MessageRequestConfig().setChannelName(SLACK_CHANNEL_NAME).setLimit(5));
    }

    public static void postToSlackWebhookUrl(final String message) {
        slack.validation().sendMessageToChannelName(message, slack.account().channel(SLACK_CHANNEL_NAME));
    }
}
