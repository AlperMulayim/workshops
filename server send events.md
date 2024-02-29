`````java

@RestController
@RequestMapping("api/v1/notifications")
public class NotificationReactiveController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getNotification() throws IOException, InterruptedException {

        Stream<String> lines = Files.lines(Path.of("/Users/alpermulayim/Desktop/github/LeaseSoftPro/leasesoft/pom.xml"));


        return  Flux.fromStream(lines)
                .map(line-> {

                    try {
                        Thread.sleep(200);
                        return  ServerSentEvent.<String>builder()
                                .data(line +" ----  " + LocalDateTime.now().toString())
                                .event("Notification")
                                .build();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

    }
}
`````
