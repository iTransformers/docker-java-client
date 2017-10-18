package net.itransformers.dockerservicemanager.controller;

import com.github.dockerjava.api.DockerClient;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cpt2nmi on 16.10.2017 г..
 */
public class DockerServiceController {

    DockerClient dockerClient;

    public DockerServiceController(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }
}
