package gray.bingo.tracker.adapter.feign;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import gray.bingo.common.constants.BingoCst;
import gray.bingo.tracker.common.TrackerCst;
import gray.bingo.tracker.common.TrackerUtil;

public class TrackerFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(TrackerCst.HTTP_HEADERS, TrackerUtil.buildHeader(BingoCst.SPAN_TYPE_FEIGN_INVOKE));
    }
}