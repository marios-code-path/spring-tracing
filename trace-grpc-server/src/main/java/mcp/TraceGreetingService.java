package mcp;

import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class TraceGreetingService {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TraceGreetingService.class);


    //public void createBooks(StreamObserver<Greeting> responseObserver) {
//        log.debug("Request " + request);
//
//        BookList.Builder responseBuilder = BookList.newBuilder();
//
//        BookUtil.assignISBN(request.getBookList()).forEach(responseBuilder::addBook);
//
//        BookList bookListResponse = responseBuilder.build();
//
//        log.debug("Response " + bookListResponse);
//
//        responseObserver.onNext(bookListResponse);
//        responseObserver.onCompleted();
    //}

}
