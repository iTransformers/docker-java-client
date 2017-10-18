package net.itransformers.dockerservicemanager.api;

import com.github.dockerjava.api.model.Image;

import java.util.List;

/**
 * Created by cpt2nmi on 18.10.2017 г..
 */
public interface DockerImangeManager {

    public String build(String dockerBaseDirPath, String dockerFilePath);
    public void pull(String repo,String tag);
    public void tag(String image, String repo, String tag);
    public void push(String image, String repo, String tag);
    public List<Image> getImages();

}
