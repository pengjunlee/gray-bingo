package gray.bingo.tracker.adapter.feign;


import feign.RequestInterceptor;
import feign.RequestTemplate;

public class TrackerFeignInterceptor implements RequestInterceptor {
 
    private final String headerName = "Authorization";
    private final String headerValue = "Bearer xxxxxxxxxxxxxxxx";
 
    @Override
    public void apply(RequestTemplate template) {
        template.header(headerName, headerValue);
    }
}