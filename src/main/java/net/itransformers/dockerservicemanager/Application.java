package net.itransformers.dockerservicemanager;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import net.itransformers.dockerservicemanager.config.DockerConfigBuilderFactory;
import net.itransformers.dockerservicemanager.controller.DockerImageController;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Created by cpt2nmi on 18.10.2017 г..
 */
@SpringBootApplication

public class Application {
    public static void main(String[] args) {
        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
                .withReadTimeout(10000)
                .withConnectTimeout(10000)
                .withMaxTotalConnections(100)
                .withMaxPerRouteConnections(10);



        DockerClientConfig dockerClientConfig = DockerConfigBuilderFactory.createDockerClientConfig();

        DockerClient dockerClient = DockerClientBuilder.getInstance(dockerClientConfig)
                .withDockerCmdExecFactory(dockerCmdExecFactory).build();
        DockerImageController dockerImageController = new DockerImageController(dockerClient);
        Set<String> tags = new HashSet<String>();
        tags.add("DockerServiceManager");


        for (Image image : dockerImageController.getImages()) {

            System.out.println("Image id: " + image.getId() + "id: " + image.getSize() + " B");

        }
        String imageId = dockerImageController.build(".", "Dockerfile");
        System.out.println("Imange with id" + imageId+" has been built!!!");
        dockerImageController.pull("java:8");

        Info info = dockerClient.infoCmd().exec();


        dockerImageController.tag(imageId, "itransformers/docker-service-manager", "latest");
        dockerImageController.push(imageId, "itransformers/docker-service-manager", "latest");

    }
}
