# True North Sr Fullstack Challenge - Restonode 

## Order messaging service component

This repo contains the order messaging service of the *Restonode* restaurants management system which is made up of two other components/repos:

1. [REST API](https://github.com/abekerman-dev/restonode-challenge-api) 
2. [Front end](https://github.com/abekerman-dev/restonode-challenge-frontend)
    
The former handles the creation of delivery orders (which produces RabbitMQ messages consumed by this service) among other tasks, whereas the latter is a website where customers can make their orders and restaurant owners can manage their businesses by adding meals to be delivered, all of these interactions being handled by the API above.

## Running the whole *Restonode* system

As the *Restonode* restaurants management is divided up into three components/repos, in order for the system to run as a whole each component must be launched individually, explained right below.

So in order to run the whole *Restonode* system there are two alternatives:

1. "Manual" execution
2. With docker
    
> Of course, you'll first have to clone/download this repo and then follow either of the execution alternatives below.

> In either case, you'll have to set up [SendGrid](https://sendgrid.com/docs/api-reference/) API key so that the application can work properly. See below for how to tackle it in both cases.

So let's explore these alternatives further:

### Running manually

First, clone or download this repo.

The first step is to export the SendGrid API key as an environment variable so the application can look it up and use it.

> For security reasons the API key is not (and should not) be hardcoded as it would become part of a public repo, so the API key must be obtained somehow (e.g by email).

In order to export it, you can use the `.env.sample` file in the root of this repo like this: copy it to a new .env file (`cp .env_sample .env`) and replace the right side of the equal sign with the actual API key. Then, run `source .env`. That's it.

**Important note:** if the REST API component was already started then we can safely assume a RabbitMQ server was already started alongside it. If the API was executed manually, the RabbitMQ host will run at `localhost`, whereas if it was executed with docker it will live in `rabbitmq` host.

If the RabbitMQ server was not started up to this point for whatever reason, you can do it now by running `docker-compose -f docker-compose-rabbitMQ-only.yml up` and it will launch a RabbitMQ instance in your `localhost`. Now this service is ready to start listening for RabbitMQ messages.

Lastly, let's run the application by executing `mvn spring-boot:run`. Of course, [maven](https://maven.apache.org/) must be already installed.

In order to get the other two components up and running as well, please refer to each repo's *README* file where you'll find instructions on how to do that just like here.

### Running *with docker*

The three repos comprising *Restonode* are *docker-ready*, so it's highly advised getting the whole system to run through this method. How? Simple!

As with the manual execution, here we also need to set up the API key in the environment variable, except that we do it differently with docker: find the `SENDGRID_API_KEY` entry under `environment` in the `docker-compose.yml` file in the root of this repo and replace the right side of the equal sign with the actual API key.

Finally, run `docker-compose up` and it'll trigger the build of a maven image with this service code as a SpringBoot application to run on top of it, and it will rely on an existing RabbitMQ service running in an external docker network against which it'll bind.

> Note: The first time it gets executed it'll take quite some time to download maven/jdk images as well as the `pom.xml` dependencies, so be patient while it's doing its job!

#### The service is now up and running and can start listening for RabbitMQ messages flowing in from the REST API.

## High-level architecture

### Code

This API is just a [Spring Boot](http://spring.io/projects/spring-boot) application set up using [Spring Initializr](https://start.spring.io/), hence most of the application's design/architecture was already decided (and simplified!) for us.

The API consists of the following core components:

1. A set of classes dealing with consuming RabbitMQ messages produced by the REST API.

2. An `EmailService` interface implementation which uses SendGrid API to send an email to the restaurant with the order details every time a delivery order has been made. There is also a mock implementation for local/integration tests whose idea is to avoid making requests to the actual SendGrid API.

## Author

* **Andrés Bekerman** - [GitHub](https://github.com/abekerman-dev)
