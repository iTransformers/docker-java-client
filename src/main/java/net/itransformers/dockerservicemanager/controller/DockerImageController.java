package net.itransformers.dockerservicemanager.controller;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import net.itransformers.dockerservicemanager.api.DockerImangeManager;
import net.itransformers.dockerservicemanager.config.DockerConfigBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cpt2nmi on 16.10.2017 г..
 */
public class DockerImageController implements DockerImangeManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    DockerClient dockerClient;

    public DockerImageController(DockerClient dockerClient ) {
        this.dockerClient = dockerClient;

    }


    public String build(String dockerBaseDirPath, String dockerFilePath){

        File baseDirectory = new File(dockerBaseDirPath);
        File dockerFile = new File(baseDirectory,dockerFilePath);
        logger.debug("Building an image in + "+dockerBaseDirPath+ " with Dockerfile "+dockerFilePath+ "!");
        BuildImageResultCallback callback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                logger.debug("id:" + item.getId()  +" status: "+item.getStatus());
                super.onNext(item);
            }
            @Override
            public void onComplete() {
                logger.debug("completed!");
                super.onComplete();
            }
        };

        return  dockerClient.buildImageCmd(baseDirectory).withDockerfile(dockerFile).exec(callback).awaitImageId();
    }


    public void pull(String name){
        logger.debug("Pulling an image from a repo: "+ name+"with tag " +name +"!");

        PullImageResultCallback callback = new PullImageResultCallback() {

            @Override
            public void onNext(PullResponseItem item) {
                logger.debug("id:" + item.getId()  +" status: "+item.getStatus());
                super.onNext(item);
            }
            @Override
            public void onComplete() {
                logger.debug("completed!");
                super.onComplete();
            }

        };

        dockerClient.pullImageCmd(name).exec(callback).awaitSuccess();


    }

    public void pull(String repo, String tag){
        logger.debug("Pulling an image from a repo: "+ repo+"with tag " +tag +"!");

        PullImageResultCallback callback = new PullImageResultCallback() {

            @Override
            public void onNext(PullResponseItem item) {
                logger.debug("id:" + item.getId()  +" status: "+item.getStatus());
                super.onNext(item);
            }
            @Override
            public void onComplete() {
                logger.debug("completed!");
                super.onComplete();
            }

        };
        if (tag==null) {
            tag = "latest";
        }
        dockerClient.pullImageCmd(repo).exec(callback).awaitSuccess();


    }
    public void tag(String image, String repo, String tag){
        dockerClient.tagImageCmd(image,repo,tag).exec();
    }

    public void push(String image, String repo, String tag){

        PushImageResultCallback callback = new PushImageResultCallback() {


            @Override
            public void onNext(PushResponseItem item) {
                logger.debug("id:" + item.getId()  +" status: "+item.getStatus());
                super.onNext(item);
            }
            @Override
            public void onComplete() {
                logger.debug("completed!");
                super.onComplete();
            }
        };
        dockerClient.pushImageCmd(image).withName(repo).withTag(tag).exec(callback).awaitSuccess();
    }

    public List<Image> getImages(){
        List<Image> images = dockerClient.listImagesCmd().exec();
        return images;
    }


}
