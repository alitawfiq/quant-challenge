**Quant Software Engineer Task**

I did not completely understand the task, so I broke down the
requirements and did what was obvious.

**Program requirements:**

-   Java 11
-   MongoDB
-   RabbitMQ
-   Maven

**First:** Make the program run

    Imported the project and solved some bugs resulting from compilation
    errors and version incompatibility.

    **Note:**

    I remember you said that you run on a K8s cluster so I containerized
    the application with docker.

    To test the app, Just run docker-compose up --d. (Docker must be
    installed on your machine)

**Second:** Functionality test

A.  There were two APIs;

<!-- -->

1.  Create template (publish to the queue, save to the database)

    i.  Null ![](.//media/image1.PNG) 

    ii. "templateField": "How are you?"![](.//media/image2.PNG)

    iii. 42 is returned on the Fifth duplicate value( can be sent Non-consecutively, also the sixth and more returns the same templateField value) ![](.//media/image3.PNG)

    iv. Same templateField is returned if not null and not the fifth duplicate ![](.//media/image4.PNG)

2.  Read templates(Fetch data from the database)

    v. Present template  ![](.//media/image5.PNG)
    

    vi. Not present template ![](.//media/image6.PNG)

    vii. Multiple present duplicated templates ![](.//media/image7.PNG)

<!-- -->

B.  Since the service wasn't tested, I did some unit tests on it.

**Third:** What the program actually does

    It wasn't very clear what the purpose of the app was.

    But from my understanding, it's a **chat-bot**.

**Fourth:** What I did

    A.  Handled exceptions in Second.A.2.iii by disallowing duplicate
        templateField values

    B.  Wrote unit tests.

    C.  Added new relevant exceptions

    D.  Send relevant HTTP status code in Second.A.2.ii (404 instead of 422)

    E.  Containerized the Application
