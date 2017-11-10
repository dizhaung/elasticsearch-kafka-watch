package st.malike.elasticsearch.kafka.watch;

import org.apache.log4j.Logger;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.*;
import st.malike.elasticsearch.kafka.watch.util.Enums;
import st.malike.elasticsearch.kafka.watch.util.JSONResponse;

import java.io.IOException;

import static org.elasticsearch.rest.RestRequest.Method.POST;

/**
 * @author malike_st
 */
public class RemoveWatcherRestAction extends BaseRestHandler {

    private static Logger log = Logger.getLogger(RemoveWatcherRestAction.class);

    @Inject
    public RemoveWatcherRestAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(POST, "/_removekafkawatch", this);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        JSONResponse message = new JSONResponse();
        IndexRequestBuilder prepareIndex = client.prepareIndex("","");
//        prepareIndex.setOpType(DocWriteRequest.OpType.valueOf())
        return channel -> {
            message.setStatus(true);
            message.setCount(2L);
            message.setMessage(Enums.JSONResponseMessage.SUCCESS.toString());
            XContentBuilder builder = channel.newBuilder();
            builder.startObject();
            message.toXContent(builder, restRequest);
            builder.endObject();
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
        };
    }


}
