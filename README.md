**Quant Software Engineer Task**

I did not completely understand the task, so I broke down the
requirements and did what was obvious.

**Program requirements: **

-   -   Java 11

-   MongoDB

-   RabbitMQ

-   Maven

**First:** Make the program run

-   Imported the project and solved some bugs resulting from compilation
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

    i.  ![](.//media/image1.PNG){width="5.885416666666667in"
        height="2.4583333333333335in"}Null 

    ii. ![](.//media/image2.PNG){width="5.927083333333333in"
        height="2.4270833333333335in"}\"templateField\": \"How are
        you?\"

    iii. ![](.//media/image3.PNG){width="5.916666666666667in"
        height="2.5520833333333335in"}42 is returned on the Fifth
        duplicate value( can be sent Non-consecutively, also the sixth
        and more returns the same templateField value)

    iv. ![](.//media/image4.PNG){width="5.9375in"
        height="2.4479166666666665in"}Same templateField is returned if
        not null and not the fifth duplicate

2.  Read templates(Fetch data from the database)

    v.  ![](.//media/image5.PNG){width="5.979166666666667in"
        height="2.3020833333333335in"}Present template

    vi. ![](.//media/image6.PNG){width="5.8125in"
        height="2.1354166666666665in"}Not present template

    vii. ![](.//media/image7.PNG){width="5.854166666666667in"
        height="2.0833333333333335in"}Multiple present duplicated
        templates

<!-- -->

B.  Since the service wasn't tested, I did some unit tests on it.

**Third:** What the program actually does

It wasn't very clear what the purpose of the app was.

But from myI understanding, it's of **chat-bot**.

**Fourth:** What I did

A.  Handled exceptions in Second.A.2.iii by disallowing duplicate
    templateField values

B.  Wrote unit tests.

C.  Added new relevant exceptions

D.  Send relevant HTTP status code in Second.A.2.ii (404 instead of 422)

E.  Containerized the Application
